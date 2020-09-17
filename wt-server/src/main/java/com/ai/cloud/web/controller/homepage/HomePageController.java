package com.ai.cloud.web.controller.homepage;

import com.ai.cloud.base.exception.BusinessException;
import com.ai.cloud.bean.base.BaseRsp;
import com.ai.cloud.bean.scan.DevInfoBean;
import com.ai.cloud.service.common.WtCommonService;
import com.ai.cloud.service.homepage.HomePageService;
import com.ai.cloud.service.index.WtIndexService;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HomePageController {
    private static final Logger logger = LoggerFactory.getLogger(HomePageController.class);

    @Autowired
    HomePageService homePageService;

    @Autowired
    WtCommonService service;

    @Autowired
    WtIndexService wtIndexService;

    @RequestMapping(value = "/wt/homepage/init")
    public String homePageInit(String encryptData) {
        logger.info("沃厅-首页查询-开始-加密参数:{}", encryptData);

        BaseRsp result = new BaseRsp();
        Map pageInfo;
        DevInfoBean devInfo = null;
        try {
            if (StringUtils.isBlank(encryptData)){
                throw new BusinessException("0002","传入的参数为空");
            }
            //解密参数
            devInfo = service.decryptDevInfo(encryptData);
            // 判断渠道是否已配置沃厅
            if(!wtIndexService.isWotingOpen(devInfo.getProvinceCode(), devInfo.getCityCode(), devInfo.getChannelId())) {
                logger.info("沃厅-首页查询-渠道:{}未开通沃厅", devInfo.getChannelId());
                throw new BusinessException("0004", "未开通云厅");
            }
            pageInfo = homePageService.queryPageInfo(devInfo.getProvinceCode(), devInfo.getCityCode(),devInfo.getChannelId());
            logger.info("沃厅-首页查询-查询结果:{},省份:{},地市:{}", JSON.toJSONString(pageInfo),
                    devInfo.getProvinceCode(),devInfo.getCityCode());
        } catch (BusinessException e) {
            logger.info("沃厅-首页查询-业务异常-省份:{},地市:{}",e.getMessage(),devInfo.getProvinceCode(),
                    devInfo.getCityCode());
            result.setRspCode(e.getMessageCode());
            result.setRspDesc(e.getMessage());
            return JSON.toJSONString(result);
        } catch (Exception e) {
            logger.error("沃厅-首页查询-异常-省份:{},地市:{}", devInfo.getProvinceCode(),devInfo.getCityCode(),e);
            result.setRspCode("9999");
            result.setRspDesc("系统异常");
            return JSON.toJSONString(result);
        }
        result.setRspCode("0000");
        result.setRspDesc("查询成功");
        result.setRspBody(pageInfo);
        return JSON.toJSONString(result);
    }

}
