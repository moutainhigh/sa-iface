package com.ai.cloud.web.controller.scan;

import com.ai.cloud.base.exception.BusinessException;
import com.ai.cloud.base.lang.Rmap;
import com.ai.cloud.bean.base.BaseRsp;
import com.ai.cloud.bean.scan.ScanInfoBean;
import com.ai.cloud.bean.scan.ScanMessageBean;
import com.ai.cloud.constant.RspCodeEnum;
import com.ai.cloud.service.scan.SaveScanInfoService;
import com.ai.cloud.tool.RspHelp;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 保存扫码用户信息 Controller
 *
 * @author zhaanping
 * @date 2020-03-20
 */
@Slf4j
@RestController
public class SaveScanInfoController {

    @Autowired
    SaveScanInfoService saveScanInfoService;

    @Autowired
    RspHelp rspHelp;

    /**
     * 保存扫码用户信息
     *
     * @param reqBean
     * @return
     */
    @RequestMapping("/scan/saveInfo")
    public BaseRsp saveScanInfo(ScanInfoBean reqBean) {
        try {
            log.info("码上购-沃厅-保存扫码用户信息，请求参数：{}，开始。。。", reqBean);

            ScanMessageBean msgBean = saveScanInfoService.saveScanInfo(reqBean);

            log.info("码上购-沃厅-保存扫码用户信息，进店码：{}，请求参数：{}，结束。。。", msgBean.getScanMessageId(), JSON.toJSONString(reqBean));
            return rspHelp.success(Rmap.asMap("messageId", msgBean.getScanMessageId()));
        } catch (BusinessException be) {
            log.error("码上购-沃厅-保存扫码用户信息，请求参数：{}，业务异常：{}|{}", JSON.toJSONString(reqBean), be.getMessageCode(), be.getMessage());
            return rspHelp.fail(be.getMessageCode(), be.getMessage());
        } catch (Exception e) {
            log.error("码上购-沃厅-保存扫码用户信息，请求参数：{}，系统异常", JSON.toJSONString(reqBean), e);
            return rspHelp.fail(RspCodeEnum.ERROR.getCode(), "系统异常");
        }
    }
}
