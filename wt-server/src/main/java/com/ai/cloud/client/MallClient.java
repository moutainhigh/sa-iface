package com.ai.cloud.client;


import com.ai.cloud.client.hystrix.MallClientFallbackFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;


/**
 * @author xyd
 * mall srvice 服务
 */
@FeignClient(name = "mall-service", fallbackFactory = MallClientFallbackFactory.class)
public interface MallClient {





    /**
     * 查询是否联通号码
     *
     * @param phoneNo
     * @return
     */
    @RequestMapping(value = "/mall/number/unicomLocalNum/v1")
    boolean isUnicomLocalNum(@RequestBody String phoneNo);
}
