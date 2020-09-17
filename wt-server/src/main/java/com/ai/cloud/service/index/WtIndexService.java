package com.ai.cloud.service.index;

import org.apache.commons.lang3.StringUtils;
import org.n3r.eql.Eql;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

import static com.ai.cloud.constant.DSName.MSQ;

@Component
public class WtIndexService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    private static final String IS_WOT_OPEN = "IS_WOT_OPEN";
    private static final String IS_WOT_PAGE_OPEN = "IS_WOT_PAGE_OPEN";

    public boolean isWotingOpen(String province, String city, String channel) {

        //查询在缓存中数据
        String key = IS_WOT_OPEN + "_" + province + city + channel;
        String isOpen = redisTemplate.opsForValue().get(key);
        if (StringUtils.isNotEmpty(isOpen)) {
            return "1".equals(isOpen);
        }

        int isWotingCount = new Eql().selectFirst("isWotingOpen").params(province, city, channel).returnType(Integer.class).execute();
        redisTemplate.opsForValue().set(key, Integer.toString(isWotingCount), 600, TimeUnit.SECONDS);
        return 1 == isWotingCount;
    }

    public boolean isWtPageOpen(String province, String city) {

        //查询在缓存中数据
        String key = IS_WOT_PAGE_OPEN + "_" + province + city;
        String isOpen = redisTemplate.opsForValue().get(key);
        if (StringUtils.isNotEmpty(isOpen)) {
            return "1".equals(isOpen);
        }

        String cityStr = new Eql(MSQ).selectFirst("isWtPageOpen").params(province).returnType(String.class).execute();

        boolean isWtPageOpen = StringUtils.isEmpty(cityStr) ? false : (cityStr.contains(city) || "000".equals(cityStr))
                ;
        redisTemplate.opsForValue().set(key, isWtPageOpen ? "1" : "0", 600, TimeUnit.SECONDS);

        return isWtPageOpen;
    }
}
