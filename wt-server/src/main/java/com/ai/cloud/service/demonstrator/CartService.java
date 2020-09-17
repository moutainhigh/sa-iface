package com.ai.cloud.service.demonstrator;

import com.ai.cloud.base.exception.BusinessException;
import com.ai.cloud.base.lang.Rmap;
import com.ai.cloud.bean.demonstrator.CartGoodsInfoBean;
import com.ai.cloud.bean.demonstrator.CartItemBean;
import com.ai.cloud.bean.demonstrator.GoodsInfoBean;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.n3r.eql.Eql;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static com.ai.cloud.constant.DemoMachineConstant.*;
import static com.ai.cloud.constant.DSName.*;

/**
 * 演示机购物车 Service
 *
 * @author zhaanping
 * @date 2020-06-11
 */
@Service
@Slf4j
public class CartService {

    @Autowired
    StringRedisTemplate redisTemplate;

    /**
     * 查询购物车内商品列表
     *
     * @param channelId
     * @return
     */
    public List<CartGoodsInfoBean> qryCartGoodsList(String channelId) {
        // 获取购物车中所有数据
        List<CartItemBean> list = getAllItems(channelId);
        if (CollectionUtils.isEmpty(list)) {
            return Lists.newArrayList();
        }

        List<String> goodsIdList = Lists.newArrayList();
        for (CartItemBean c : list) {
            goodsIdList.add(c.getGoodsId());
        }
        // 查询商品列表
        List<GoodsInfoBean> goodsList = qryGoodsInfoList(channelId, goodsIdList);

        List<CartGoodsInfoBean> cartGoodsList = Lists.newArrayList();
        for (CartItemBean c : list) {
            CartGoodsInfoBean cg = new CartGoodsInfoBean();
            BeanUtils.copyProperties(c, cg);
            cg.setValidFlag(ValidFlag.YES);
            cg.setAppLimitTime("");
            // 组装商品数据
            for (GoodsInfoBean g : goodsList) {
                if (c.getGoodsId().equals(g.getGoodsId())) {
                    cg.setGoodsName(g.getGoodsName());
                    cg.setBrandCode(g.getBrandCode());
                    cg.setBrandName(g.getBrandName());
                    cg.setModelCode(g.getModelCode());
                    cg.setModelName(g.getModelName());
                    cg.setAppLimitTime(g.getAppLimitTime());
                    cg.setStockNum(g.getStockNum());
                    continue;
                }
            }
            // 校验有效性
            String checkValidStr = checkValid(channelId, cg);
            if (StringUtils.isNotEmpty(checkValidStr)) {
                cg.setValidFlag(ValidFlag.NO);
                cg.setInvalidReason(checkValidStr);
            }
            cartGoodsList.add(cg);
        }
        Collections.sort(cartGoodsList);
        return cartGoodsList;
    }

    /**
     * 查询商品列表
     *
     * @param channelId
     * @param goodsIdList
     * @return
     */
    public List<GoodsInfoBean> qryGoodsInfoList(String channelId, List<String> goodsIdList) {
        return new Eql(MSQ).select("qryGoodsInfo")
                .params(Rmap.asMap("channelId", channelId, "goodsIdList", goodsIdList))
                .returnType(GoodsInfoBean.class).execute();
    }

    /**
     * 校验购物车内商品有效性
     *
     * @param channelId
     * @param cg
     * @return
     */
    public String checkValid(String channelId, CartGoodsInfoBean cg) {
        // 1、校验商品状态
        if (StringUtils.isEmpty(cg.getAppLimitTime())) {
            return "该商品已删除，无法申请";
        }

        // 2、校验最后申请截止时间
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime appLimit = LocalDateTime.parse(cg.getAppLimitTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        if (now.isAfter(appLimit)) {
            return "已超过最后申请截止时间，无法申请";
        }

        // 3、校验是否存在 有效的（非省份驳回、非地市驳回）A类（厂商配置）申请单
        int validAOrderCount = qryValidAOrderCount(channelId, cg.getGoodsId());
        if (validAOrderCount > 0) {
            return "已存在申请单，无法申请";
        }

        // 4、校验数量
        String count = cg.getCount();
        if (ApplyMode.FIRM_CONFIG.equals(cg.getApplyMode())) {
            if (!count.equals(cg.getStockNum())) {
                return "该商品库存有变动，请删除后重新选择";
            }
        } else {
            boolean countRightFlag = StringUtils.isNumeric(count) && Integer.parseInt(count) >= CART_MIN_NUM && Integer.parseInt(count) <= CAT_MAX_NUM;
            if (!countRightFlag) {
                return "当前申请模式，申请数量最少" + CART_MIN_NUM + "台，最多" + CAT_MAX_NUM + "台";
            }
        }

        return "";
    }

    /**
     * 查询购物车 示例 是否展示标记
     *
     * @param phoneNo
     * @return
     */
    public String qryShowIllustrateFlag(String phoneNo) {
        String key = DEMO_MACHINE_SHOW_ILLUSTRATE + phoneNo;
        String redisValue = redisTemplate.opsForValue().get(key);
        if (StringUtils.isNotEmpty(redisValue)) {
            return ShowFlag.NO;
        }
        redisTemplate.opsForValue().set(key, phoneNo, LONG_CACHE_TIME, TimeUnit.DAYS);
        return ShowFlag.YES;
    }


    /**
     * 校验购物车数据
     *
     * @param channelId
     * @param cartItem
     * @param cartOperType
     */
    public void checkCartItemBean(String channelId, CartItemBean cartItem, String cartOperType) {

        // 1、校验非空
        if (cartItem == null || StringUtils.isBlank(cartItem.getGoodsId())
                || StringUtils.isBlank(cartItem.getApplyMode())
                || StringUtils.isBlank(cartItem.getCount())) {
            throw new BusinessException(RspCode.REQ_PARAM_ERR, "商品编码、申请模式、申请数量 均不能为空");
        }
        String goodsId = cartItem.getGoodsId();
        String applyMode = cartItem.getApplyMode();
        String count = cartItem.getCount();

        // 2、校验商品有效性
        GoodsInfoBean goodsInfoBean = qryGoodsInfo(channelId, goodsId);
        // 2.1、校验商品状态
        if (goodsInfoBean == null) {
            throw new BusinessException(RspCode.GOODS_INVALID_ERR, "该商品已删除，无法申请");
        }
        // 2.2、校验最后申请截止时间
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime appLimit = LocalDateTime.parse(goodsInfoBean.getAppLimitTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        if (now.isAfter(appLimit)) {
            throw new BusinessException(RspCode.GOODS_INVALID_ERR, "已超过最后申请截止时间，无法申请");
        }

        if (CartOperType.ADD.equals(cartOperType)) {
            // 3、校验是否存在 有效的（非省份驳回、非地市驳回）A类（厂商配置）申请单
            int validAOrderCount = qryValidAOrderCount(channelId, goodsId);
            if (validAOrderCount > 0) {
                throw new BusinessException(RspCode.HAVE_VALID_A_ORDER_ERR, "已存在申请单，无法申请");
            }

            // 4、校验购物车中是否已存在该商品
            List<CartItemBean> list = getAllItems(channelId);

            for (CartItemBean c : list) {
                if (cartItem.getGoodsId().equals(c.getGoodsId())) {
                    throw new BusinessException(RspCode.CART_REPEAT_ERR, "购物车中已存在该商品，如需再次申请，需先从购物车删除该商品");
                }
            }

            // 5、校验 申请模式 与 购物车中已存在的 商品的申请模式 的 一致性
            if (CollectionUtils.isNotEmpty(list) && !applyMode.equals(list.get(0).getApplyMode())) {
                throw new BusinessException(RspCode.CART_APPLY_MODE_ERR, "当前购物车内只能有一种申请模式");
            }
        }

        // 6、校验申请数量。申请模式为 厂商配置 时 申请数量 需等于 该商品库存，为其他两种模式时 需满足 有效数字，最小1，最大50
        if (ApplyMode.FIRM_CONFIG.equals(applyMode)) {
            if (!count.equals(goodsInfoBean.getStockNum())) {
                throw new BusinessException(RspCode.CART_COUNT_ERR, "该商品库存有变动，请稍后重新申请");
            }
        } else {
            boolean countRightFlag = StringUtils.isNumeric(count) && Integer.parseInt(count) >= CART_MIN_NUM && Integer.parseInt(count) <= CAT_MAX_NUM;
            if (!countRightFlag) {
                throw new BusinessException(RspCode.CART_COUNT_ERR, "当前申请模式，申请数量最少" + CART_MIN_NUM + "台，最多" + CAT_MAX_NUM + "台");
            }
        }

        // 7、处理购物车商品名称
        cartItem.setGoodsName(goodsInfoBean.getGoodsName());
    }

    /**
     * 查询商品信息
     *
     * @param channelId
     * @param goodsId
     * @return
     */
    public GoodsInfoBean qryGoodsInfo(String channelId, String goodsId) {
        return new Eql(MSQ).selectFirst("qryGoodsInfo")
                .params(Rmap.asMap("channelId", channelId, "goodsId", goodsId))
                .returnType(GoodsInfoBean.class).execute();
    }

    /**
     * 查询 有效的（非省份驳回、非地市驳回）A类（厂商配置）申请单 数量
     *
     * @param channelId
     * @param goodsId
     * @return
     */
    public int qryValidAOrderCount(String channelId, String goodsId) {
        return new Eql(MSQ).selectFirst("qryValidAOrder").params(Rmap.asMap("channelId", channelId, "goodsId", goodsId)).returnType(Integer.class).execute();
    }

    /**
     * 获取购物车中所有数据
     *
     * @param channelId
     * @return
     */
    public List<CartItemBean> getAllItems(String channelId) {
        List<CartItemBean> list = Lists.newLinkedList();
        Set<String> set = redisTemplate.opsForSet().members(buildKey(channelId));
        if (CollectionUtils.isEmpty(set)) {
            return list;
        }
        for (String c : set) {
            CartItemBean cartItemBean = JSONObject.parseObject(c, CartItemBean.class);
            list.add(cartItemBean);
        }
        return list;
    }

    /**
     * 组装 redis key
     *
     * @param channelId
     * @return
     */
    private String buildKey(String channelId) {
        return MessageFormat.format(CART_REDIS_KEY, channelId);
    }

    /**
     * 加入购物车
     *
     * @param channelId
     * @param cartItemBean
     * @return
     */
    public void pushItem(String channelId, CartItemBean cartItemBean) {
        redisTemplate.opsForSet().add(buildKey(channelId), JSONObject.toJSONString(cartItemBean));
        redisTemplate.expire(buildKey(channelId), LONG_CACHE_TIME, TimeUnit.DAYS);
    }

    /**
     * 删除购物车
     *
     * @param channleId
     * @param cartItemBean
     * @return
     */
    public long removeItem(String channleId, CartItemBean cartItemBean) {
        return redisTemplate.opsForSet().remove(buildKey(channleId),
                JSONObject.toJSONString(cartItemBean));
    }
}
