package com.ai.cloud.client.hystrix;


import com.ai.cloud.client.IdServerClient;
import com.ai.cloud.client.pojo.OrderSeqRsp;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class IdServerClientFallbackFactory implements FallbackFactory<IdServerClient> {

    private static Logger logger = LoggerFactory.getLogger(IdServerClientFallbackFactory.class);

    @Override
    public IdServerClient create(Throwable throwable) {

        logger.error("id service overload or timeout:{}", throwable.getMessage());

        return new IdServerClient() {
            @Override
            public OrderSeqRsp getOrderNo(String traceId) {
                OrderSeqRsp rsp = new OrderSeqRsp();
                rsp.setTraceId(traceId);
                rsp.setRspCode("9999");
                rsp.setRspDesc("id server overload or timeout");
                return rsp;
            }

            @Override
            public OrderSeqRsp getOrderId(String traceId) {
                OrderSeqRsp rsp = new OrderSeqRsp();
                rsp.setTraceId(traceId);
                rsp.setRspCode("9999");
                rsp.setRspDesc("id server overload or timeout");
                return rsp;
            }
        };
    }
}
