package com.ai.cloud.service.demonstrator;

import com.ai.cloud.base.lang.Rmap;
import com.ai.cloud.bean.demonstrator.CartCountBean;
import com.ai.cloud.constant.OrderConstant;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class CartCountService {
    private static final String DEL_FLAG = "0";
    private static final int LONG_CACHE_TIME = 365;

    @Autowired
    private StringRedisTemplate redisTemplate;

    public void changeCount(CartCountBean param) {
        String key = OrderConstant.CACHE_GOODS_KEY + param.getChannelId();
        Set<String> cartOrders = redisTemplate.opsForSet().members(key);
        log.info("演示机-查询购物数据-信息:{},登录号码:{}", JSON.toJSONString(cartOrders), param.getPhoneNo());
        //处理购物车数据
        dealCartData(cartOrders, param, key);
    }

    public void dealCartData(Set<String> cartOrders, CartCountBean param, String key) {
        for (String redisData : cartOrders) {
            Map data = JSONObject.parseObject(redisData, Map.class);
            if (!StringUtils.equals(param.getGoodsId(), Rmap.getStr(data, "goodsId"))) {
                continue;
            }
            log.info("演示机-购物车操作-所要变更商品:{},登录号码:{}", JSON.toJSONString(data), param.getPhoneNo());
            redisTemplate.opsForSet().remove(key, redisData);
            if (DEL_FLAG.equals(param.getOperateFlag())) {
                log.info("演示机-购物车删除操作-所删除的商品:{},登录号码:{}", param.getGoodsId(), param.getPhoneNo());
            } else {
                data.replace("count", param.getGoodsCount());
                log.info("演示机-数量" + "[" + param.getOperateFlag() + "]操作,操作数量:{},商品Id:{},登录手机号:{}", param.getGoodsCount(), param.getGoodsId(), param.getPhoneNo());
                redisTemplate.opsForSet().add(key, JSON.toJSONString(data));
                redisTemplate.expire(key,LONG_CACHE_TIME, TimeUnit.DAYS);
            }
            break;
        }
    }
}
