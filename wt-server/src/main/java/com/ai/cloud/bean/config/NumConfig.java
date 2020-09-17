package com.ai.cloud.bean.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @author xiaoniu
 *
 * 号卡中心配置类
 */
@Component
@RefreshScope
public class NumConfig {
    /**
     * 商城接入编码
     */
    @Value("${num.mall.appId}")
    private String mallAppId;
    /**
     * 商城接入密钥
     */
    @Value("${num.mall.secret}")
    private String mallSecret;
    /**
     *  服务地址
     */
    @Value("${num.api.context}")
    private String numApiContext;

    public String getMallAppId() {
        return mallAppId;
    }

    public void setMallAppId(String mallAppId) {
        this.mallAppId = mallAppId;
    }

    public String getMallSecret() {
        return mallSecret;
    }

    public void setMallSecret(String mallSecret) {
        this.mallSecret = mallSecret;
    }

    public String getNumApiContext() {
        return numApiContext;
    }

    public void setNumApiContext(String numApiContext) {
        this.numApiContext = numApiContext;
    }
}
