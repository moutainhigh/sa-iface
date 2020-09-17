package com.ai.cloud.client.hystrix;


import com.ai.cloud.bean.base.BaseRsp;
import com.ai.cloud.client.ForeignClient;
import com.ai.cloud.client.pojo.MsgReqParam;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


/**
 * @author wangzd
 * 公用接口微服务
 * 熔断器默认处理
 */
@Component
public class ForeignClientFallbackFactory implements FallbackFactory<ForeignClient> {

    private static Logger logger = LoggerFactory.getLogger(ForeignClientFallbackFactory.class);


    @Override
    public ForeignClient create(Throwable cause) {

        logger.error("foreign service overload or timeout:{}", cause.getMessage());

        return new ForeignClient() {

            @Override
            public BaseRsp msgSend(MsgReqParam param) {
                logger.error("Foreign熔断 短信发送失败，请求参数：{}", param);
                BaseRsp baseRsp = new BaseRsp();
                baseRsp.setRspCode("2000");
                baseRsp.setRspDesc("foreign service msgSend over time");
                return baseRsp;
            }


        };

    }



}