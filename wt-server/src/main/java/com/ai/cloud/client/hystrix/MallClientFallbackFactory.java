package com.ai.cloud.client.hystrix;


import com.ai.cloud.client.MallClient;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


/**
 * @author xyd
 */
@Component
public class MallClientFallbackFactory implements FallbackFactory<MallClient> {

    private static Logger logger = LoggerFactory.getLogger(MallClientFallbackFactory.class);



    @Override
    public MallClient create(Throwable cause) {

        logger.error("mall service overload or timeout:{}", cause.getMessage());

        return new MallClient() {
            @Override
            public boolean isUnicomLocalNum(String phoneNo) {
                logger.error("查询是否联通号码接口超时" + phoneNo);
                return false;
            }
        };
    }
}