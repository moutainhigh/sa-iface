package com.ai.cloud.web.controller.demonstrator;


import com.ai.cloud.auth.annotation.Auth;
import com.ai.cloud.auth.bean.AuthBean;
import com.ai.cloud.auth.component.impl.MsgoAuth;
import com.ai.cloud.base.exception.AppException;
import com.ai.cloud.base.exception.BusinessException;
import com.ai.cloud.bean.base.BaseRsp;
import com.ai.cloud.constant.OrderConstant;
import com.ai.cloud.service.demonstrator.ApplySubmitService;
import com.ai.cloud.service.demonstrator.DemoMachineCommonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
public class ApplySubmitController {

    @Autowired
    MsgoAuth msgoAuth;
    @Autowired
    StringRedisTemplate redisTemplate;
    @Autowired
    ApplySubmitService submitService;

    @Autowired
    DemoMachineCommonService demoMachineCommonService;

    @Auth
    @RequestMapping("/wt/apply/submit/v1")
    public BaseRsp saveOrderInfo(HttpServletRequest request) {
        BaseRsp rsp = new BaseRsp();
        String limitRedisKey = OrderConstant.LIMIT_KEY_REDIS;
        long count = 0;
        String logPhone = "";
        try {

            AuthBean authBean = msgoAuth.getUserInfo(request);
            logPhone = authBean.getPhoneNo();
            //限制重复提交
            limitRedisKey = limitRedisKey + logPhone;
            count = redisTemplate.boundValueOps(limitRedisKey).increment(1);
            log.info("演示机-{}-限制重复提交-数量-{}", logPhone, count);
            if (count > 1) {
                log.error("演示机-{}-重复提交-数量-{}", logPhone, count);
                throw new BusinessException(OrderConstant.LIMIT_ERROR_CODE, "已经提交订单，请务重复提交!");
            }
            //判断是否是厅长
            boolean flag = demoMachineCommonService.isDirector(authBean);
            if (!flag) {
                log.error("演示机-{}-该帐号没有权限", logPhone);
                throw new BusinessException("2006", "该帐号没有权限");
            }
            submitService.submitOrder(authBean);
            rsp.setRspCode("0000");
            rsp.setRspDesc("申请成功");
            log.error("演示机-{}-提交成功", logPhone);
        } catch (AppException e) {
            log.error("演示机-{}-提交申请App异常", logPhone, e);
            rsp.setRspCode(e.getMessageCode());
            rsp.setRspDesc(e.getMessage());
        } catch (BusinessException e) {
            log.error("演示机-{}-提交申请业务异常", logPhone, e);
            rsp.setRspCode(e.getMessageCode());
            rsp.setRspDesc(e.getMessage());
        } catch (Exception e) {
            log.error("演示机-{}-提交申请系统异常",logPhone, e);
            rsp.setRspCode("9999");
            rsp.setRspDesc("提交失败");
        } finally {
            if (!OrderConstant.LIMIT_ERROR_CODE.equals(rsp.getRspCode())) {
                redisTemplate.delete(limitRedisKey);
            } else {
                if (count < 4) {
                    redisTemplate.expire(limitRedisKey, 30, TimeUnit.SECONDS);
                }
            }
        }
        return rsp;
    }
}
