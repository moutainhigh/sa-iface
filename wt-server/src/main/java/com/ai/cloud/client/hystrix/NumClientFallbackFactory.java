package com.ai.cloud.client.hystrix;

import com.ai.cloud.client.NumClient;
import com.ai.cloud.client.pojo.NumSelectionParam;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


/**
 * @author xiaoniu
 */
@Component
public class NumClientFallbackFactory implements FallbackFactory<NumClient> {

    private static Logger logger = LoggerFactory.getLogger(NumClientFallbackFactory.class);

    @Override
    public NumClient create(Throwable cause) {

        logger.error("num service overload or timeout:{}",cause.getMessage());

        return new NumClient() {
            @Override
            public String selection(NumSelectionParam param) {
                return null;
            }
        };
    }
}