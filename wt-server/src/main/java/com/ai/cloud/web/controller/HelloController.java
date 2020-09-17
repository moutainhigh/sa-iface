package com.ai.cloud.web.controller;

import com.ai.cloud.auth.bean.AuthBean;
import com.ai.cloud.base.exception.AppException;
import com.ai.cloud.bean.base.BaseRsp;
import com.ai.cloud.bean.config.HelloConfig;
import com.ai.cloud.client.pojo.MerchBean;
import com.ai.cloud.client.pojo.NumSelectionParam;
import com.ai.cloud.service.HelloService;
import com.ai.cloud.tool.CookieUtil;
import com.ai.cloud.tool.RspHelp;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author xiaoniu 2018/1/18.
 */
@RestController
public class HelloController {

    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);

    @Autowired
    HelloService service;

    @Autowired
    RspHelp rspHelp;

    @Autowired
    HelloConfig config;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    CookieUtil cookieUtil;


    /**
     * 缓存微服务调用
     *
     * @return
     */
    @RequestMapping(value = "/hello/ess/staff/v1")
    public BaseRsp helloCache() {
        try {
            logger.info("starting ... ");
            String proCityCode = "11_110";
            MerchBean bean = service.helloCache(proCityCode);
            return rspHelp.success(bean);
        } catch (Exception e) {
            return rspHelp.error();
        }
    }

    @RequestMapping(value = "/error1")
    public void error(HttpServletRequest req, HttpServletResponse rsp) {
        throw new AppException("1002", "其他错误");
    }

    /**
     * redis 读取
     * http://localhost:7006/hello/redis/delete/v1
     */
    @RequestMapping(value = "/hello/redis/delete/v1")
    public String hellRedisDelete(String key) {
        redisTemplate.delete(key);
        return "delete success:" + key;
    }

    /**
     * 号码微服务调用
     *
     * @return
     */
    @RequestMapping(value = "/hello/num/query/v1")
    public String helloNum() {
        NumSelectionParam param = new NumSelectionParam();
        param.setProvinceCode("11");
        param.setCityCode("110");
        param.setQryType("01");
        return service.helloNum(param);
    }

    /**
     * 参数测试
     * http://localhost:11111/hello/test/bean/v1?provinceCode=11&cityCode=110
     *
     * @param merchBean
     * @return
     */
    @RequestMapping(value = "/hello/test/bean/v1")
    public String helloBean1(@ModelAttribute("merchBean") MerchBean merchBean) {
        return JSON.toJSONString(merchBean);
    }

    /**
     * 从配置中心 读取 配置
     */
    @RequestMapping(value = "/hello/config/num/ref/v1")
    public String getNumContext() {
        return config.getNumContext();
    }

    /**
     * redis 写入
     * http://localhost:11111/hello/redis/record/v1?value=world
     */
    @RequestMapping(value = "/hello/redis/record/v1")
    public String hellRedisRecord(@RequestParam("value") String value) {
        redisTemplate.opsForValue().set("cloudHello", value);
        return "record success";
    }

    @RequestMapping(value = "/hello/redis/record/v3")
    public String setRedisRecord(@RequestParam("key") String key) {
        redisTemplate.opsForValue().set(key, "1",10,TimeUnit.MINUTES);
        return "record success";
    }

    /**
     * redis 读取
     * http://localhost:11111/hello/redis/read/v1
     */
    @RequestMapping(value = "/hello/redis/read/v1")
    public String hellRedisRead() {
        String value = redisTemplate.opsForValue().get("cloudHello");
        return "read success:" + value;
    }

    @RequestMapping(value = "/hello/query1/v1")
    public String queryCount() {
        return service.queryCount();
    }

    @RequestMapping(value = "/hello/setToken/v1")
    public String setCookie(HttpServletRequest req, HttpServletResponse rsp) {
        cookieUtil.setCookie(rsp, "_msgo_", "1409760575959818248101244564");
        AuthBean authBean = new AuthBean();
        authBean.setProvinceCode("11");
        authBean.setPhoneNo("18511838352");
        authBean.setChannelId("1111af117");
        authBean.setUserSource("dev");
        authBean.setEparchyCode("110");
        authBean.setToken("1409760575959818248101244564");
        authBean.setChannelMainType("10");
        authBean.setDeviceType("a");
        authBean.setDepartCode("11af117");
        authBean.setDepartId("1111af117");
        authBean.setDevId("18511838352");
        authBean.setDevName("张旭阳");
        authBean.setDevBound("1");


        try {
            redisTemplate.opsForValue().set("msgo2:u:1409760575959818248101244564", "1", 1, TimeUnit.MILLISECONDS);
            Thread.sleep(1000);
        } catch (Exception e) {

        }
        HashOperations hashOperations = redisTemplate.opsForHash();


        Map<String, String> map = JSON.parseObject(JSON.toJSONString(authBean), Map.class);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if("iOS".equals(entry.getKey())){
                continue;
            }
            hashOperations.put("msgo2:u:1409760575959818248101244564", entry.getKey(), entry.getValue());

        }

        return JSON.toJSONString(authBean);

    }

    @RequestMapping(value = "/hello/setToken/v2")
    public String setCookieJF(HttpServletRequest req, HttpServletResponse rsp) {
        cookieUtil.setCookie(rsp, "_msgo_", "123456789011");
        AuthBean authBean = new AuthBean();
        authBean.setProvinceCode("11");
        authBean.setPhoneNo("17600775973");
        authBean.setChannelId("1111af117");
        authBean.setUserSource("dev");
        authBean.setEparchyCode("110");
        authBean.setToken("123456789011");


        try {
            redisTemplate.opsForValue().set("msgo2:u:123456789011", "1", 1, TimeUnit.MILLISECONDS);
            Thread.sleep(1000);
        } catch (Exception e) {

        }
        HashOperations hashOperations = redisTemplate.opsForHash();


        Map<String, String> map = JSON.parseObject(JSON.toJSONString(authBean), Map.class);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if("iOS".equals(entry.getKey())){
                continue;
            }
            hashOperations.put("msgo2:u:123456789011", entry.getKey(), entry.getValue());

        }
        return JSON.toJSONString(authBean);

    }

    @RequestMapping(value = "/hello/setToken/v3")
    public String setCookieV3(HttpServletRequest req, HttpServletResponse rsp) {
        cookieUtil.setCookie(rsp, "_msgo_", "876785176524");
        AuthBean authBean = new AuthBean();
        authBean.setProvinceCode("11");
        authBean.setPhoneNo("18686864163");
        authBean.setChannelId("1111b0pdd");
        authBean.setUserSource("dev");
        authBean.setEparchyCode("110");
        authBean.setToken("876785176524");


        try {
            redisTemplate.opsForValue().set("msgo2:u:876785176524", "1", 1, TimeUnit.MILLISECONDS);
            Thread.sleep(1000);
        } catch (Exception e) {

        }
        HashOperations hashOperations = redisTemplate.opsForHash();


        Map<String, String> map = JSON.parseObject(JSON.toJSONString(authBean), Map.class);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if("iOS".equals(entry.getKey())){
                continue;
            }
            hashOperations.put("msgo2:u:876785176524", entry.getKey(), entry.getValue());

        }
        return JSON.toJSONString(authBean);
    }

    @RequestMapping(value = "/hello/setToken/v4")
    public String setCookieV4(HttpServletRequest req, HttpServletResponse rsp) {
        cookieUtil.setCookie(rsp, "_msgo_", "1402150385274019840992143020");
        AuthBean authBean = new AuthBean();
        authBean.setProvinceCode("11");
        authBean.setPhoneNo("18686864163");
        authBean.setChannelId("1111b199g");
        authBean.setUserSource("dev");
        authBean.setEparchyCode("110");
        authBean.setToken("1402150385274019840992143020");
        authBean.setChannelMainType("10");
        authBean.setDeviceType("a");
        authBean.setDepartCode("11b199g");
        authBean.setDepartId("1111b199g");
        authBean.setDevId("1100823540");
        authBean.setDevName("陈士伟");
        authBean.setDevBound("1");

        try {
            redisTemplate.opsForValue().set("msgo2:u:1402150385274019840992143020", "1", 1, TimeUnit.MILLISECONDS);
            Thread.sleep(1000);
        } catch (Exception e) {

        }
        HashOperations hashOperations = redisTemplate.opsForHash();


        Map<String, String> map = JSON.parseObject(JSON.toJSONString(authBean), Map.class);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if("iOS".equals(entry.getKey())){
                continue;
            }
            hashOperations.put("msgo2:u:1402150385274019840992143020", entry.getKey(), entry.getValue());

        }
        return JSON.toJSONString(authBean);
    }


    @RequestMapping(value = "/hello/setToken/v5")
    public String setCookieV5(HttpServletRequest req, HttpServletResponse rsp) {
        cookieUtil.setCookie(rsp, "_msgo_", "1402150385274019840992143021");
        AuthBean authBean = new AuthBean();
        authBean.setProvinceCode("34");
        authBean.setPhoneNo("15651612682");
        authBean.setChannelId("3434b01i1");
        authBean.setUserSource("dev");
        authBean.setEparchyCode("340");
        authBean.setToken("1402150385274019840992143021");
        authBean.setChannelMainType("10");
        authBean.setDeviceType("a");
        authBean.setDepartCode("34b01i1");
        authBean.setDepartId("3434b01i1");
        authBean.setDevId("3401843938");
        authBean.setDevName("陈丹丹");
        authBean.setDevBound("1");

        try {
            redisTemplate.opsForValue().set("msgo2:u:1402150385274019840992143021", "1", 1, TimeUnit.MILLISECONDS);
            Thread.sleep(1000);
        } catch (Exception e) {

        }
        HashOperations hashOperations = redisTemplate.opsForHash();


        Map<String, String> map = JSON.parseObject(JSON.toJSONString(authBean), Map.class);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if("iOS".equals(entry.getKey())){
                continue;
            }
            hashOperations.put("msgo2:u:1402150385274019840992143020", entry.getKey(), entry.getValue());

        }
        return JSON.toJSONString(authBean);
    }


    @RequestMapping(value = "/hello/setToken/v6")
    public String setCookieV6(HttpServletRequest req, HttpServletResponse rsp) {
        cookieUtil.setCookie(rsp, "_msgo_", "1402150385274840992143021");
        AuthBean authBean = new AuthBean();
        authBean.setProvinceCode("11");
        authBean.setPhoneNo("15611026831");
        authBean.setChannelId("1111b063w");
        authBean.setUserSource("dev");
        authBean.setEparchyCode("110");
        authBean.setToken("1402150385274840992143021");
        authBean.setChannelMainType("10");
        authBean.setDeviceType("a");
        authBean.setDepartCode("11b063w");
        authBean.setDepartId("1111b063w");
        authBean.setDevId("1102310032");
        authBean.setDevName("孙长华");
        authBean.setDevBound("1");

        try {
            redisTemplate.opsForValue().set("msgo2:u:1402150385274840992143021", "1", 1, TimeUnit.MILLISECONDS);
            Thread.sleep(1000);
        } catch (Exception e) {

        }
        HashOperations hashOperations = redisTemplate.opsForHash();


        Map<String, String> map = JSON.parseObject(JSON.toJSONString(authBean), Map.class);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if("iOS".equals(entry.getKey())){
                continue;
            }
            hashOperations.put("msgo2:u:1402150385274840992143021", entry.getKey(), entry.getValue());

        }
        return JSON.toJSONString(authBean);
    }

    @RequestMapping(value = "/hello/setToken/v7")
    public String setCookieV7(HttpServletRequest req, HttpServletResponse rsp) {
        cookieUtil.setCookie(rsp, "_msgo_", "1402150385274840992143023");
        AuthBean authBean = new AuthBean();
        authBean.setProvinceCode("13");
        authBean.setPhoneNo("18601109543");
        authBean.setChannelId("1111b063w");
        authBean.setUserSource("dev");
        authBean.setEparchyCode("130");
        authBean.setToken("29031f7e7f51321a212852ce3b884e1f");
        authBean.setChannelMainType("10");
        authBean.setDeviceType("a");
        authBean.setDepartCode("11b063w");
        authBean.setDepartId("1111b063w");
        authBean.setDevId("1102310032");
        authBean.setDevName("孙长华");
        authBean.setDevBound("1");

        try {
            redisTemplate.opsForValue().set("msgo2:u:1402150385274840992143023", "1", 1, TimeUnit.MILLISECONDS);
            Thread.sleep(1000);
        } catch (Exception e) {

        }
        HashOperations hashOperations = redisTemplate.opsForHash();


        Map<String, String> map = JSON.parseObject(JSON.toJSONString(authBean), Map.class);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if("iOS".equals(entry.getKey())){
                continue;
            }
            hashOperations.put("msgo2:u:1402150385274840992143023", entry.getKey(), entry.getValue());

        }
        return JSON.toJSONString(authBean);
    }

    @RequestMapping(value = "/hello/setToken/v8")
    public String setCookieV8(HttpServletRequest req, HttpServletResponse rsp) {
        cookieUtil.setCookie(rsp, "_msgo_", "8402150385274840992143023");
        AuthBean authBean = new AuthBean();
        authBean.setProvinceCode("87");
        authBean.setPhoneNo("15609312704");
        authBean.setChannelId("8787a0427");
        authBean.setUserSource("dev");
        authBean.setEparchyCode("870");
        authBean.setToken("29031f7e7f51321a212852ce3b884e1f");
        authBean.setChannelMainType("10");
        authBean.setDeviceType("a");
        authBean.setDepartCode("87a0427");
        authBean.setDepartId("8787a0427");
        authBean.setDevId("8702436071");
        authBean.setDevName("段晓红");
        authBean.setDevBound("1");

        try {
            redisTemplate.opsForValue().set("msgo2:u:8402150385274840992143023", "1", 1, TimeUnit.MILLISECONDS);
            Thread.sleep(1000);
        } catch (Exception e) {

        }
        HashOperations hashOperations = redisTemplate.opsForHash();


        Map<String, String> map = JSON.parseObject(JSON.toJSONString(authBean), Map.class);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if("iOS".equals(entry.getKey())){
                continue;
            }
            hashOperations.put("msgo2:u:8402150385274840992143023", entry.getKey(), entry.getValue());

        }
        return JSON.toJSONString(authBean);
    }

    @RequestMapping(value = "/hello/setToken")
    public String setUserCookie(AuthBean authBean, HttpServletResponse rsp) {
        String cookieValue = UUID.randomUUID().toString().replace("-","");
        cookieUtil.setCookie(rsp, "_msgo_", cookieValue);
        Map userMap = service.queryUserInfo(authBean.getPhoneNo());
        String channelId = StringUtils.isEmpty(authBean.getChannelId()) ? MapUtils.getString(userMap, "CHANNEL_ID") : authBean.getChannelId();
        String channelMainType = "";
        if (StringUtils.isNotEmpty(channelId)) {
            channelMainType = service.queryChannelMainType(channelId);
        }
        String devBound = "";
        if (StringUtils.isNotEmpty(authBean.getPhoneNo())) {
            devBound = service.queryDevBound(authBean.getPhoneNo());
        }
        authBean.setProvinceCode(StringUtils.isEmpty(authBean.getProvinceCode()) ? MapUtils.getString(userMap, "PROVINCE_CODE") : authBean.getProvinceCode());
        authBean.setPhoneNo(authBean.getPhoneNo());
        authBean.setChannelId(channelId);
        authBean.setUserSource(StringUtils.isEmpty(authBean.getUserSource()) ? MapUtils.getString(userMap, "USER_SOURCE") : authBean.getUserSource());
        authBean.setEparchyCode(StringUtils.isEmpty(authBean.getEparchyCode()) ? MapUtils.getString(userMap, "EPARCHY_CODE") : authBean.getEparchyCode());
        authBean.setToken(cookieValue);
        authBean.setChannelMainType(StringUtils.isNotEmpty(channelMainType) ? channelMainType : authBean.getChannelMainType());
        authBean.setDeviceType(StringUtils.isEmpty(authBean.getDeviceType()) ? MapUtils.getString(userMap, "DEVICE_TYPE") : authBean.getDeviceType());
        authBean.setDepartCode(StringUtils.isEmpty(authBean.getDepartCode()) ? MapUtils.getString(userMap, "DEPART_CODE") : authBean.getDepartCode());
        authBean.setDepartId(StringUtils.isEmpty(authBean.getDepartId()) ? MapUtils.getString(userMap, "DEPART_ID") : authBean.getDepartId());
        authBean.setDevId(StringUtils.isEmpty(authBean.getDevId()) ? MapUtils.getString(userMap, "DEVELOPER_ID") : authBean.getDevId());
        authBean.setDevName(StringUtils.isEmpty(authBean.getDevName()) ? MapUtils.getString(userMap, "DEVELOPER_NAME") : authBean.getDevName());
        authBean.setDevBound(StringUtils.isEmpty(authBean.getDevBound()) ? devBound : authBean.getDevBound());

        try {
            redisTemplate.opsForValue().set("msgo2:u:".concat(cookieValue), "1", 1, TimeUnit.MILLISECONDS);
            Thread.sleep(1000);
        } catch (Exception e) {

        }
        HashOperations hashOperations = redisTemplate.opsForHash();


        Map<String, String> map = JSON.parseObject(JSON.toJSONString(authBean), Map.class);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if("iOS".equals(entry.getKey())){
                continue;
            }
            hashOperations.put("msgo2:u:".concat(cookieValue), entry.getKey(), entry.getValue());

        }
        return JSON.toJSONString(authBean);
    }


    @RequestMapping(value = "/getTokenV/v1")
    public String test(HttpServletRequest req, HttpServletResponse rsp) {
        Map entries = redisTemplate.opsForHash().entries("msgo2:u:1522312662596kR1weo4c37");
        return JSON.toJSONString(entries);
    }

    //清附加产品缓存
    @RequestMapping(value = "/clear/optProduct")
    public void clear(String phoneNo) {
        String key = "msg_index_is_wopayflag".concat(phoneNo);
        redisTemplate.delete(key);
    }

    @RequestMapping(value = "/redis/set")
    public String addSet(String phone, String name, String count, String flag, String channelId) {
        String REDIS_KEY = "msgo:demonstrator:cart:" + channelId;
        Set g;
        if ("0".equals(flag)) {
            Map mm1 = Maps.newHashMap();
            mm1.put("goodsId", phone);
            mm1.put("applyMode", name);
            mm1.put("count", count);
            mm1.put("goodsName", "华为手机");
            redisTemplate.opsForSet().add(REDIS_KEY, JSON.toJSONString(mm1));
            g = redisTemplate.opsForSet().members(REDIS_KEY);
        } else if ("2".equals(flag)) {
            Map mm1 = Maps.newHashMap();
            mm1.put("goodsId", phone);
            mm1.put("applyMode", name);
            mm1.put("count", count);
            mm1.put("goodsName", "华为手机");
            redisTemplate.opsForSet().remove(REDIS_KEY, JSON.toJSONString(mm1));
            g = redisTemplate.opsForSet().members(REDIS_KEY);
        } else {
            g = redisTemplate.opsForSet().members(REDIS_KEY);
        }
        return JSON.toJSONString(g);
    }

    public static void main(String[] args) {
        System.out.print(UUID.randomUUID().toString().replace("-",""));
    }

}
