package com.ai.cloud.client.hystrix;

import com.ai.cloud.client.CacheClient;
import com.ai.cloud.client.pojo.MerchBean;
import com.ai.cloud.client.pojo.NumQryCacheReq;
import com.ai.cloud.client.pojo.NumQryCacheRsp;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;


/**
 * UserFeignClient的fallbackFactory类，该类需实现FallbackFactory接口，并覆写create方法
 * The fallback factory must produce instances of fallback classes that
 * implement the interface annotated by {@link FeignClient}.
 * @author xiaoniu
 *
 * 默认值处理
 */
@Component
public class CacheClientFallbackFactory implements FallbackFactory<CacheClient> {

    private static Logger logger = LoggerFactory.getLogger(CacheClientFallbackFactory.class);

    @Override
    public CacheClient create(Throwable cause)   {
        logger.error("cache-service over load or timeout:{}",cause.getMessage());

        return new CacheClient()  {
            @Override
            public MerchBean qryEssStaff(String provinceCityCode) {
                return null;
            }

            @Override
            public NumQryCacheRsp numForQry(NumQryCacheReq param) {
                return null;
            }
        };
    }
}