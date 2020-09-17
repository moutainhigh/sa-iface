package com.ai.cloud.service.search;

import com.ai.cloud.base.lang.Rmap;
import com.ai.cloud.bean.config.ScanConfig;
import com.ai.cloud.bean.scan.DevInfoBean;
import com.ai.cloud.service.common.WtCommonService;
import com.alibaba.fastjson.JSON;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.n3r.eql.Eql;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.ai.cloud.constant.DSName.MSQ;
import static com.ai.cloud.constant.ScanConstant.CACHE_TIME;
import static com.ai.cloud.constant.ScanConstant.POPULAR_SEARCH_KEY;

/**
 * 热门搜索 Service
 *
 * @author zhaanping
 * @date 2020-03-19
 */
@Slf4j
@Service
public class PopularSearchService {

    public static final String SPLIT_SIGN = "|";

    @Autowired
    WtCommonService wtCommonService;

    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    ScanConfig scanConfig;

    /**
     * 热门搜索
     *
     * @param a 分享及二维码 的 加密参数a，例子：a=aes.encode(d=发展人id&c=渠道id&p=省份&e=地市)
     * @return
     */
    public List<Map> popularSearch(String a) {
        // 解密 发展人信息
        DevInfoBean devInfo = wtCommonService.decryptDevInfo(a);
        String provinceCode = devInfo.getProvinceCode();
        String cityCode = devInfo.getCityCode();

        return qryPopularSearchByCache(provinceCode, cityCode);
    }

    /**
     * 通用缓存 查询 热门搜索
     *
     * @param provinceCode
     * @param cityCode
     * @return
     */
    public List<Map> qryPopularSearchByCache(String provinceCode, String cityCode) {
        // 测试环境 直接返回
        if (scanConfig.getTestEnv()) {
            return qryPopularSearch(provinceCode, cityCode);
        }

        String key = POPULAR_SEARCH_KEY + provinceCode + "_" + cityCode;
        String redisValue = redisTemplate.opsForValue().get(key);
        if (StringUtils.isNotEmpty(redisValue)) {
            return JSON.parseArray(redisValue, Map.class);
        }

        List<Map> list = qryPopularSearch(provinceCode, cityCode);
        redisTemplate.opsForValue().set(key, JSON.toJSONString(list), CACHE_TIME, TimeUnit.SECONDS);
        return list;
    }

    /**
     * 查询 热门搜索
     *
     * @param provinceCode
     * @param cityCode
     * @return
     */
    public List<Map> qryPopularSearch(String provinceCode, String cityCode) {
        // 查询 热门搜索
        List<Map> list = Lists.newArrayList();
        String psStr = new Eql(MSQ).selectFirst("qryPopularSearch").params(provinceCode, cityCode).returnType(String.class).execute();
        if (StringUtils.isBlank(psStr)) {
            return list;
        }

        // 查询 热门搜索 具体信息
        List<String> psList = Splitter.on(SPLIT_SIGN).trimResults().splitToList(psStr);
        list = new Eql().select("qryPopularSearchInfo").params(Rmap.asMap("psList", psList)).returnType(Map.class).execute();
        return list;
    }

}
