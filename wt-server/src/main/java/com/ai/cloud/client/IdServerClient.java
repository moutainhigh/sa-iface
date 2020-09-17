package com.ai.cloud.client;

import com.ai.cloud.client.hystrix.IdServerClientFallbackFactory;
import com.ai.cloud.client.pojo.OrderSeqRsp;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "id-server", fallbackFactory = IdServerClientFallbackFactory.class)
public interface IdServerClient {

    @RequestMapping("/get/order/no")
    OrderSeqRsp getOrderNo(@RequestParam("traceId") String traceId);


    @RequestMapping("/get/order/id")
    OrderSeqRsp getOrderId(@RequestParam("traceId") String traceId);
}
