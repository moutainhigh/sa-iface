package com.ai.cloud.service.demonstrator;

import com.ai.cloud.auth.bean.AuthBean;
import com.ai.cloud.base.exception.BusinessException;
import com.ai.cloud.base.lang.Rmap;
import com.ai.cloud.bean.demonstrator.OrderGoodsInfoBean;
import com.ai.cloud.bean.demonstrator.OrderInfoBean;
import com.ai.cloud.bean.demonstrator.OrderLogInfoBean;
import com.ai.cloud.client.IdServerClient;
import com.ai.cloud.client.pojo.OrderSeqRsp;
import com.ai.cloud.constant.DSName;
import com.ai.cloud.constant.DemoMachineConstant;
import com.ai.cloud.constant.OrderConstant;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.n3r.eql.Eql;
import org.n3r.eql.EqlTran;
import org.n3r.eql.util.Closes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.ai.cloud.constant.DemoMachineConstant.GOODS_UP_MAX_DAY;


@Slf4j
@Service
public class ApplySubmitService {

    /**
     * 直辖市：北京市、天津市、上海市、重庆市
     */
    private static final List<String> DIRECTLY_CITY_LIST = Lists.newArrayList("11", "13", "31", "83");

    @Autowired
    IdServerClient idServerClient;

    @Autowired
    StringRedisTemplate redisTemplate;

    public void submitOrder(AuthBean authBean) {
        OrderInfoBean orderInfoBean = new OrderInfoBean();
        //准备一些默认值
        String orderId = qryOrderSeqNo(true);
        orderInfoBean.setOrderId(orderId);
        orderInfoBean.setStaffId(authBean.getPhoneNo());
        orderInfoBean.setChannelId(authBean.getChannelId());
        orderInfoBean.setChannelName(authBean.getChannelName());
        orderInfoBean.setProvinceCode(authBean.getProvinceCode());
        orderInfoBean.setCityCode(authBean.getEparchyCode());
        orderInfoBean.setStaffName(authBean.getUserName());
        orderInfoBean.setOrderState(DIRECTLY_CITY_LIST.contains(authBean.getProvinceCode()) ? 2 : 1);
        //从redis中取购物车信息
        String goodsKey = OrderConstant.CACHE_GOODS_KEY + authBean.getChannelId();
        Set<String> cacheGoodsInfo = redisTemplate.opsForSet().members(goodsKey);
        log.info("演示机-购物车redis里面数据为-{}-登陆号码-{}", JSON.toJSONString(cacheGoodsInfo),  authBean.getPhoneNo());
        if (CollectionUtils.isEmpty(cacheGoodsInfo)) {
            log.error("演示机-购物车redis里面数据为空-登陆号码{}", authBean.getPhoneNo());
            throw new BusinessException("2000", "没有申请的演示机");
        }
        //拼接数据
        Map goodsMap = null;
        OrderGoodsInfoBean orderGoodsInfoBean = null;
        String applyMode = null;
        Set<String> applyModeSet = new HashSet<>();
        List<OrderGoodsInfoBean> goodsInfoBeans = Lists.newArrayList();
        for (String goodsInfoStr : cacheGoodsInfo) {
            //goodsMap {"goodsId": "111111111","applyMode": "01", "count": "12"}
            goodsMap = JSON.parseObject(goodsInfoStr, Map.class);
            orderGoodsInfoBean = new OrderGoodsInfoBean();
            orderGoodsInfoBean.setOrderId(orderId);
            orderGoodsInfoBean.setGoodsId(MapUtils.getString(goodsMap, "goodsId"));
            orderGoodsInfoBean.setApplyCount(Integer.valueOf(MapUtils.getString(goodsMap, "count", "0")));
            orderGoodsInfoBean.setGoodsState("00");
            applyMode = MapUtils.getString(goodsMap, "applyMode");
            if (StringUtils.isEmpty(applyMode)) {
                log.error("演示机-购物车redis里面applyMode为空-登陆号码{}", authBean.getPhoneNo());
                throw new BusinessException("2001", "购物车数据有误，请确认");
            }
            applyModeSet.add(applyMode);
            goodsInfoBeans.add(orderGoodsInfoBean);
        }
        log.info("演示机-applyMode集合{}-登陆号码{}", JSON.toJSONString(applyModeSet),  authBean.getPhoneNo());
        //同一申请单只能有一种申请模式
        if (applyModeSet.size() != 1) {
            log.error("演示机-applyMode申请单不是同一种模式-登陆号码{}", JSON.toJSONString(applyModeSet),  authBean.getPhoneNo());
            throw new BusinessException("2002", "同一申请单只能有一种申请模式，请重新选择");
        }
        orderInfoBean.setApplyMode(applyMode);
        log.info("演示机-校验商品数据-登陆号码{}", authBean.getPhoneNo());
        checkEffectGoodsId(authBean.getChannelId(), goodsInfoBeans);
        prePareGoodsInfo(goodsInfoBeans, orderInfoBean);
        orderInfoBean.setGoodsInfoList(goodsInfoBeans);
        saveOrderInfo(orderInfoBean);
        redisTemplate.delete(goodsKey);
    }

    /**
     * 订单入库
     */
    private void saveOrderInfo(OrderInfoBean orderBean) {
        log.info("演示机-申请入库前数据为:{}", JSON.toJSONString(orderBean));
        if (orderBean == null) {
            log.error("演示机-数据为空");
            throw new BusinessException("9998", "数据为空");
        }
        //日志表
        OrderLogInfoBean orderLogInfoBean = new OrderLogInfoBean();
        orderLogInfoBean.setOrderId(orderBean.getOrderId());
        orderLogInfoBean.setProvinceCode(orderBean.getProvinceCode());
        orderLogInfoBean.setOperatorId(orderBean.getStaffId());
        orderLogInfoBean.setOperateName(orderBean.getStaffName());
        orderLogInfoBean.setCurrentState("1");
        orderLogInfoBean.setOriginalState("1");
        orderLogInfoBean.setDealType(DemoMachineConstant.LogDealType.APPLY_SUBMIT);
        orderLogInfoBean.setDealContent("您已提交订单，请等待系统确认");
        orderLogInfoBean.setResultCode("1");
        orderLogInfoBean.setResultInfo("成功");
        orderLogInfoBean.setShowFlag("0");
        Eql eql = new Eql(DSName.MSQ);
        EqlTran eqlTran = eql.newTran();
        try{
            eqlTran.start();
            eql.useTran(eqlTran);
            eql.insert("saveOrderInfo").params(orderBean).execute();
            if (CollectionUtils.isNotEmpty(orderBean.getGoodsInfoList())) {
                eql.insert("saveGoodsInfo").params(Rmap.asMap("goodsList", orderBean.getGoodsInfoList())).execute();
            }
            eql.insert("saveLogInfo").params(orderLogInfoBean).execute();
            eqlTran.commit();
            log.info("演示机-申请入库成功:订单id{}, 号码-{}", orderBean.getOrderId(), orderBean.getStaffId());
        } catch (Exception e) {
            log.error("演示机-申请入库异常:{},号码-{}", orderBean.getOrderId(), orderBean.getStaffId(), e);
            eqlTran.rollback();
            throw new BusinessException("9999", "保存失败，请重试");
        } finally {
            Closes.closeQuietly(eqlTran);
        }
    }

    /**
     * 查询当前员工有效的A类订单的商品id并校验所传的数据是否可以。如果任何一个商品存在有效订单反回false报错。
     * @param channelId
     * @return
     */
    private void checkEffectGoodsId(String channelId, List<OrderGoodsInfoBean> goodsInfoBeans) {
        if (CollectionUtils.isEmpty(goodsInfoBeans)) {
            return;
        }
        List<String> result = new Eql(DSName.MSQ).select("qryOrderGoodsInfo").params(channelId).returnType(String.class).execute();
        log.error("演示机-{}查询有效的厂家配置订单结果{}", channelId, JSON.toJSONString(result));
        if (CollectionUtils.isEmpty(result)) {
            return;
        }
        for(OrderGoodsInfoBean goodsInfoBean : goodsInfoBeans) {
            if (StringUtils.isEmpty(goodsInfoBean.getGoodsId())) {
                continue;
            }
            if (result.contains(goodsInfoBean.getGoodsId())) {
                log.error("演示机-商品-{}-存在有效的厂家配置订单", goodsInfoBean.getGoodsId());
                throw new BusinessException("2003", "商品存在有效的厂家配置订单。");
            }
        }
    }

    /**
     * 校验商品信息，查询商品信息
     * @param goodsInfoBeans
     */
    private void prePareGoodsInfo(List<OrderGoodsInfoBean> goodsInfoBeans, OrderInfoBean orderInfoBean) {
        String staffId = orderInfoBean.getStaffId();
        if (CollectionUtils.isEmpty(goodsInfoBeans)) {
            log.error("演示机-没有选择商品{}", staffId);
            throw new BusinessException("3001", "您没有选择商品，请确认");
        }
        List<String> goodsIdList = Lists.newArrayList();
        for (OrderGoodsInfoBean bean : goodsInfoBeans) {
            if (StringUtils.isNotEmpty(bean.getGoodsId())) {
                goodsIdList.add(bean.getGoodsId());
            }
        }
        log.error("演示机-{}选择的商品id为：{}", staffId, JSON.toJSONString(goodsIdList));
        if (CollectionUtils.isEmpty(goodsIdList)) {
            log.error("演示机-没有选择商品{}", staffId);
            throw new BusinessException("3001", "您没有选择商品，请确认");
        }
        //校验商品数据
        List<Map> goodsInfoList = new Eql(DSName.MSQ).select("qryGoodsInfo").params(Rmap.asMap("goodsIdList", goodsIdList)).returnType(Map.class).execute();
        log.info("演示机-{}-查询的商品数据为{}", staffId, JSON.toJSONString(goodsInfoList));
        if (CollectionUtils.isEmpty(goodsInfoList) || goodsInfoList.size() != goodsIdList.size()) {
            log.error("演示机-{}-商品数据有误，{}数量不一致{}", staffId, JSON.toJSONString(goodsIdList), JSON.toJSONString(goodsInfoList));
            throw new BusinessException("4003", "部分商品已下架，请重新选择商品");
        }
        //校验分配库存
        List<Map> effectCount = new Eql(DSName.MSQ).select("qryEffectInfo").params(Rmap.asMap("goodsIdList", goodsIdList, "channelId", orderInfoBean.getChannelId())).returnType(Map.class).execute();
        log.error("演示机-{}查询库存数据{}", staffId, JSON.toJSONString(effectCount));
        if (CollectionUtils.isEmpty(effectCount) || effectCount.size() != goodsIdList.size()) {
            log.error("演示机-{}-库存数据有误，{}数量不一致{}", staffId, JSON.toJSONString(goodsIdList), JSON.toJSONString(effectCount));
            throw new BusinessException("4003", "部分商品已停止申请，请重新选择商品");
        }
        String reqGoodsId = "";
        String qryGoodsId = "";
        int qryAmount = 0;
        for (OrderGoodsInfoBean bean : goodsInfoBeans) {
            reqGoodsId = bean.getGoodsId();
            for (Map info : goodsInfoList) {
                qryGoodsId = MapUtils.getString(info, "goodsId", "");
                if (reqGoodsId.equals(qryGoodsId)) {
                    bean.setGoodsBrand(MapUtils.getString(info, "brandCode"));
                    bean.setGoodsBrandDesc(MapUtils.getString(info, "brandDesc"));
                    bean.setGoodsName(MapUtils.getString(info, "goodsDesc"));
                    bean.setGoodsType(MapUtils.getString(info, "modelCode"));
                    bean.setGoodsTypeDesc(MapUtils.getString(info, "modelDesc"));
                    break;
                }
            }
            //如果是厂家配置模式，要校验库存和购物车数量一致
            if (OrderConstant.APPLY_MODE_FACTORY.equals(orderInfoBean.getApplyMode())) {
                for (Map amount : effectCount) {
                    qryGoodsId = MapUtils.getString(amount, "goodsId", "");
                    qryAmount = MapUtils.getInteger(amount, "amount", 0);
                    if (reqGoodsId.equals(qryGoodsId) && bean.getApplyCount() != qryAmount){
                        log.error("演示机-{}-商品{}库存{}，购买{},数量不一致", staffId, reqGoodsId, qryAmount, bean.getApplyCount());
                        throw new BusinessException("4003", "商品库存不足，请确认商品数是否正确");
                    }
                }
            }
        }
    }


    /**
     * 生成订单号方法
     * @param flag true 查询订单id, false 生成订单no
     * @return true 查询订单id, false 生成订单no
     */
    private String qryOrderSeqNo(boolean flag) {
        String traceId = UUID.randomUUID().toString().toLowerCase();
        OrderSeqRsp rsp;
        if (flag) {
            rsp = idServerClient.getOrderId(traceId);
        } else {
            rsp = idServerClient.getOrderNo(traceId);
        }
        if (!"0000".equals(rsp.getRspCode())) {
            log.error("id生成失败");
            throw new BusinessException("7009", "序列生成失败，请重新尝试生成序列！");
        }
        return  flag ? rsp.getOrderId() : rsp.getOrderNo();
    }

}
