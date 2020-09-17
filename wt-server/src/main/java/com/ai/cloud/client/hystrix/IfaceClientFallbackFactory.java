package com.ai.cloud.client.hystrix;

import com.ai.cloud.client.IfaceClient;
import com.ai.cloud.client.pojo.IdentityReqParam;
import com.ai.cloud.client.pojo.IdentityRsp;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


/**
 * @author xiaoniu
 * 公用接口微服务
 * 熔断器默认处理
 */
@Component
public class IfaceClientFallbackFactory implements FallbackFactory<IfaceClient> {

    private static Logger logger = LoggerFactory.getLogger(IfaceClientFallbackFactory.class);


    @Override
    public IfaceClient create(Throwable cause) {

        logger.error("iface service overload or timeout:{}", cause.getMessage());

        return new IfaceClient() {

            @Override
            public IdentityRsp identity(IdentityReqParam reqBean) {
                return null;
            }

//            @Override
//            public BaseRsp msgSend(MsgReqParam param) {
//                BaseRsp baseRsp = new BaseRsp();
//                baseRsp.setRspCode("2000");
//                baseRsp.setRspDesc("iface service over time");
//                return baseRsp;
//            }

        };
    }
}