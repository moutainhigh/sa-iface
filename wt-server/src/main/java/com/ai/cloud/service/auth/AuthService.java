package com.ai.cloud.service.auth;

import com.ai.cloud.auth.bean.AuthBean;
import com.ai.cloud.base.lang.Dates;
import com.ai.cloud.base.lang.Rmap;
import com.alibaba.fastjson.JSON;
import org.n3r.eql.Eql;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class AuthService {

    public static final String DATE_FORMAT_YYYYMMDD = "yyyyMMdd";
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    private static String MSG_KEY_USER_PREFIX = "msgo2:u:";
    private static String MSG_KEY_ALL_USER_PREFIX = "msgo2:user:all:";

    public AuthBean getAuthByToken(String token) {

        Map authJson = stringRedisTemplate.opsForHash().entries(MSG_KEY_USER_PREFIX.concat(token));
        return Rmap.isEmpty(authJson) ? null : JSON.parseObject(JSON.toJSONString(authJson), AuthBean.class);
    }


    /**
     * 记录用户最新访问时间
     *
     * @param authBean
     */
    public void recordUserVisitTime(AuthBean authBean) {
        // new Eql().update("recordUserVisitTime").params(authBean).execute();

        recordUsers2Redis(authBean);
    }

    private void recordUsers2Redis(AuthBean authBean) {
        String currDayStr = Dates.formatOneDayDate(0, DATE_FORMAT_YYYYMMDD);
        stringRedisTemplate.opsForSet().add(getAllUserKey4OneDate(currDayStr), authBean.getPhoneNo());
    }


    private String getAllUserKey4OneDate(String dayStr) {
        return MSG_KEY_ALL_USER_PREFIX + dayStr;
    }


    public Map getSupportVersion(String suitType, String provinceCode, String eparchyCode) {
        return new Eql().params(suitType, provinceCode, eparchyCode).selectFirst("querySupportVersion").returnType(Map.class).execute();
    }
}
