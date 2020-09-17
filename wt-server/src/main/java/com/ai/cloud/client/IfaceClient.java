package com.ai.cloud.client;

import com.ai.cloud.client.hystrix.FeignConfigure;
import com.ai.cloud.client.hystrix.IfaceClientFallbackFactory;
import com.ai.cloud.client.pojo.IdentityReqParam;
import com.ai.cloud.client.pojo.IdentityRsp;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author xyd
 * iface server 服务调用
 */
@FeignClient(name = "iface-service", configuration = FeignConfigure.class,
        fallbackFactory = IfaceClientFallbackFactory.class)
public interface IfaceClient {
    /**
     * 身份证认证接口
     *
     * @param reqBean
     * @return
     */
    @RequestMapping(value = "/iface/identity/check/v1")
    IdentityRsp identity(@RequestBody IdentityReqParam reqBean);

//    /**
//     * 发送短信接口
//     *
//     * @param param
//     * @return
//     */
//    @RequestMapping(value = "/iface/communicate/message/send/v1")
//    BaseRsp msgSend(@RequestBody MsgReqParam param);

}
