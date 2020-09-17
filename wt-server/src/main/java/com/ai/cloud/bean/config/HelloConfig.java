package com.ai.cloud.bean.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @author xiaoniu
 *
 * 常量类
 */
@Component
@RefreshScope
public class HelloConfig {
    /**
     * aop服务地址
     */
    @Value("${aop.url}")
    private String aopUrl;

    @Value("${num.api.context}")
    public String numContext;

    public String getAopUrl() {
        return aopUrl;
    }

    public void setAopUrl(String aopUrl) {
        this.aopUrl = aopUrl;
    }

    public String getNumContext() {
        return numContext;
    }

    public void setNumContext(String numContext) {
        this.numContext = numContext;
    }
}
