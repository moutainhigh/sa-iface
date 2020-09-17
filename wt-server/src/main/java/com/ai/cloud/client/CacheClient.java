package com.ai.cloud.client;


import com.ai.cloud.client.hystrix.CacheClientFallbackFactory;
import com.ai.cloud.client.pojo.MerchBean;
import com.ai.cloud.client.pojo.NumQryCacheReq;
import com.ai.cloud.client.pojo.NumQryCacheRsp;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author xiaoniu
 */
@FeignClient(name="cache-service",fallbackFactory = CacheClientFallbackFactory.class)
public interface CacheClient {

    /**
     * 查询员工信息
     * @param provinceCityCode
     * @return
     */
    @RequestMapping(value = "/cache/ess/staff/qry/v1")
    MerchBean qryEssStaff(@RequestParam("provinceCityCode") String provinceCityCode);


    @RequestMapping(value = "/cache/num/qry/v1")
    NumQryCacheRsp numForQry(@RequestBody NumQryCacheReq param);


}
