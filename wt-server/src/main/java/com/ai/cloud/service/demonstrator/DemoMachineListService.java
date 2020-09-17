package com.ai.cloud.service.demonstrator;

import com.ai.cloud.auth.bean.AuthBean;
import com.ai.cloud.base.exception.BusinessException;
import com.ai.cloud.base.lang.Rmap;
import com.ai.cloud.bean.demonstrator.*;
import com.ai.cloud.constant.RspCodeEnum;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.n3r.eql.Eql;
import org.n3r.eql.EqlTran;
import org.n3r.eql.util.Closes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.ai.cloud.constant.DSName.MSQ;
import static com.ai.cloud.constant.DemoMachineConstant.*;

/**
 * 演示机列表 Service
 *
 * @author zhaanping
 * @date 2020-06-12
 */
@Service
@Slf4j
public class DemoMachineListService {

    @Autowired
    DemoMachineCommonService commonService;

    @Autowired
    StringRedisTemplate redisTemplate;

    /**
     * 查询商品品牌列表
     *
     * @param authBean
     * @return
     */
    public List<GoodsBrandBean> qryGoodsBrandList(AuthBean authBean) {
        String key = GOODS_BRAND_REDIS_KEY + authBean.getChannelId();
        String redisValue = redisTemplate.opsForValue().get(key);
        if (StringUtils.isNotEmpty(redisValue)) {
            return JSON.parseArray(redisValue, GoodsBrandBean.class);
        }

        List<GoodsBrandBean> list = new Eql(MSQ).select("qryGoodsBrandList").params(authBean).returnType(GoodsBrandBean.class).execute();

        redisTemplate.opsForValue().set(key, JSON.toJSONString(list), CACHE_TIME, TimeUnit.SECONDS);
        return list;
    }

    /**
     * 查询商品型号列表
     *
     * @param authBean
     * @return
     */
    public List<GoodsModelBean> qryGoodsModelList(AuthBean authBean) {
        String key = GOODS_MODEL_REDIS_KEY + authBean.getChannelId();
        String redisValue = redisTemplate.opsForValue().get(key);
        if (StringUtils.isNotEmpty(redisValue)) {
            return JSON.parseArray(redisValue, GoodsModelBean.class);
        }

        List<GoodsModelBean> list = new Eql(MSQ).select("qryGoodsModelList").params(authBean).returnType(GoodsModelBean.class).execute();

        redisTemplate.opsForValue().set(key, JSON.toJSONString(list), CACHE_TIME, TimeUnit.SECONDS);
        return list;
    }

    /**
     * 查询可申请商品列表页
     * <p>
     * 展示条件：
     * 1、截止申请时间 有效期内
     * 2、不存在有效的（非省份驳回、非地市驳回）A类（厂商配置）申请单
     * （即 若该商品存在模式为 厂商配置 的订单 且 订单的状态为 待地市审核、地市审核通过、省份审核通过、已完成 之一 时 ，不展示该商品）
     *
     * @param queryBean
     * @return
     */
    public List<GoodsInfoBean> qryCanApplyGoodsList(GoodsQueryBean queryBean) {

        List<GoodsInfoBean> goodsList = new Eql(MSQ).select("qryCanApplyGoodsList").params(queryBean).returnType(GoodsInfoBean.class).execute();

        return goodsList;
    }

    /**
     * 查询订单列表
     *
     * @param queryBean
     * @return
     */
    public List<OrderInfoBean> qryOrderList(OrderQueryBean queryBean) {

        // 查询订单列表
        List<OrderInfoBean> orderList = new Eql(MSQ).select("qryOrderList").params(queryBean).returnType(OrderInfoBean.class).execute();
        if (CollectionUtils.isEmpty(orderList)) {
            return orderList;
        }

        // 查询订单商品信息
        for (OrderInfoBean order : orderList) {

            List<OrderGoodsInfoBean> goodsInfoList = new Eql(MSQ).select("qryGoodsInfoList").params(order.getOrderId()).returnType(OrderGoodsInfoBean.class).execute();
            order.setGoodsInfoList(goodsInfoList);

            // 组装 是否可以确认完成的标记：订单状态省份审核完成，且订单商品的状态 存在 已发货 或 已签收
            order.setCanConfirmFlag(CanConfirm.NO);
            if (OrderState.PROVINCE_AUDIT_PASS == order.getOrderState()) {
                for (OrderGoodsInfoBean g : goodsInfoList) {
                    if (GoodsState.DELIVERED.equals(g.getGoodsState()) || GoodsState.SIGNED_IN.equals(g.getGoodsState())) {
                        order.setCanConfirmFlag(CanConfirm.YES);
                        break;
                    }
                }
            }
        }
        return orderList;
    }

    /**
     * 订单确认完成
     *
     * @param authBean
     * @param orderId
     * @return
     */
    public int confirmOrder(AuthBean authBean, String orderId) {
        Eql eql = new Eql(MSQ);
        EqlTran eqlTran = eql.newTran();
        try {
            eqlTran.start();
            eql.useTran(eqlTran);

            // 订单确认完成
            int row = eql.update("confirmOrder").params(Rmap.asMap("channelId", authBean.getChannelId(), "orderId", orderId)).returnType(Integer.class).execute();
            if (row != 1) {
                throw new BusinessException(RspCode.ORDER_STATE_ERR, "订单状态已改变，请确认该订单信息!");
            }
            // 记录日志
            OrderLogInfoBean logBean = new OrderLogInfoBean();
            logBean.setOrderId(orderId);
            logBean.setProvinceCode(authBean.getProvinceCode());
            logBean.setOperatorId(authBean.getPhoneNo());
            logBean.setOperateName(authBean.getUserName());
            logBean.setOperatePhone(authBean.getPhoneNo());
            logBean.setCurrentState(OrderState.COMPLETE + "");
            logBean.setOriginalState(OrderState.PROVINCE_AUDIT_PASS + "");
            logBean.setDealType(LogDealType.CONFIRM);
            logBean.setDealContent("订单确认完成");
            logBean.setResultCode("1");
            logBean.setResultInfo("成功");
            logBean.setOperateType(LogOperateType.CUSTOM);
            logBean.setShowFlag(LogShowFlag.ALL_SHOW);
            int logRow = commonService.saveLogInfo(eql, logBean);

            eqlTran.commit();
            return row + logRow;
        } catch (BusinessException be) {
            log.info("码上购-演示机-订单确认完成，订单号：{}，入库业务异常:{}|{}", orderId, be.getMessageCode(), be.getMessage());
            eqlTran.rollback();
            throw new BusinessException(be.getMessageCode(), be.getMessage());
        } catch (Exception e) {
            log.info("码上购-演示机-订单确认完成，订单号：{}，入库系统异常", orderId, e);
            eqlTran.rollback();
            throw new BusinessException(RspCodeEnum.ERROR.getCode(), "操作失败，请重试");
        } finally {
            Closes.closeQuietly(eqlTran);
        }
    }
}
