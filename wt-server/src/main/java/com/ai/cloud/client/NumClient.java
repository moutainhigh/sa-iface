package com.ai.cloud.client;


import com.ai.cloud.client.hystrix.NumClientFallbackFactory;
import com.ai.cloud.client.pojo.NumSelectionParam;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author xiaoniu
 * 号码服务调用客户端
 */
@FeignClient(name="num-service",fallbackFactory = NumClientFallbackFactory.class)
public interface NumClient {
    /**
     * 号码查询
     * @param  param
     * @return
     */
    @RequestMapping(value = "/num/selection/v1")
    String selection(@RequestBody NumSelectionParam param);
}
