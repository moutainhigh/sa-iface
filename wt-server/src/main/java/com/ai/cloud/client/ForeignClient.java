package com.ai.cloud.client;


import com.ai.cloud.bean.base.BaseRsp;
import com.ai.cloud.client.hystrix.FeignConfigure;
import com.ai.cloud.client.hystrix.ForeignClientFallbackFactory;
import com.ai.cloud.client.pojo.MsgReqParam;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author wangzd
 * foreign server 服务调用
 */
@FeignClient(name = "foreign-service", configuration = FeignConfigure.class,
        fallbackFactory = ForeignClientFallbackFactory.class)
public interface ForeignClient {

    /**
     * 发送短信接口
     *
     * @param param
     * @return
     */
    @RequestMapping(value = "/foreign/communicate/message/send/v1")
    BaseRsp msgSend(@RequestBody MsgReqParam param);


}
