package com.ai.cloud.service;


import com.ai.cloud.client.CacheClient;
import com.ai.cloud.client.NumClient;
import com.ai.cloud.client.pojo.MerchBean;
import com.ai.cloud.client.pojo.NumSelectionParam;
import org.n3r.eql.Eql;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;


/**
 * @author xiaoniu 2017/11/28.
 */
@Component
public class HelloService {

    private static Logger logger = LoggerFactory.getLogger(HelloService.class);

    @Autowired
    CacheClient cacheClient;

    @Autowired
    NumClient numClient;

    public MerchBean helloCache(String proCityCode){
        return cacheClient.qryEssStaff(proCityCode);
    }


    public String helloNum(NumSelectionParam param) {
        return numClient.selection(param);
    }

    public String queryCount() {
        return new Eql("mysql").useSqlFile("com/ai/cloud/service/HelloService.eql").selectFirst("queryCountDemo").returnType(String.class).execute();
    }

    public Map queryUserInfo(String phoneNo){
        return new Eql().selectFirst("queryUserInfo").params(phoneNo).returnType(Map.class).execute();
    }

    public String queryChannelMainType(String channel){
        return new Eql().selectFirst("queryChannelMainType").params(channel).returnType(String.class).execute();
    }

    public String queryDevBound(String phoneNo){
        return new Eql().selectFirst("queryDevBound").params(phoneNo).returnType(String.class).execute();
    }
}
