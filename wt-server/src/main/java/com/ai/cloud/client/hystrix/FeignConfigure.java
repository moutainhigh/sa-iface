package com.ai.cloud.client.hystrix;

import feign.Request;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xiaoniu 2018/3/20.
 */
@Configuration
public class FeignConfigure {

    public static int connectTimeOutMillis = 1000;

    public static int readTimeOutMillis = 3000;

    @Bean
    public Request.Options options() {
        return new Request.Options(connectTimeOutMillis, readTimeOutMillis);
    }
}
