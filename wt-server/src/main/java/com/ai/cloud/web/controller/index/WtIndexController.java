package com.ai.cloud.web.controller.index;

import com.ai.cloud.auth.annotation.Auth;
import com.ai.cloud.auth.bean.AuthBean;
import com.ai.cloud.auth.component.impl.MsgoAuth;
import com.ai.cloud.bean.base.BaseRsp;
import com.ai.cloud.service.index.WtIndexService;
import com.ai.cloud.tool.RspHelp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
public class WtIndexController {

    @Autowired
    RspHelp rspHelp;

    @Autowired
    MsgoAuth msgoAuth;

    @Autowired
    WtIndexService service;

    private static final String USER_SOURCE_DEV = "dev";

    /**
     * 判断沃厅是否已开通
     *
     * @return
     */
    @Auth
    @RequestMapping(value = "/wo/isOpen")
    public BaseRsp masgoIndex(HttpServletRequest req) {
        AuthBean authBean = msgoAuth.getUserInfo(req);

        if(!USER_SOURCE_DEV.equals(authBean.getUserSource())) {
            log.info("工号:{}非发展人工号", authBean.getPhoneNo());
            return rspHelp.fail("9999", "非发展人工号");
        }

        // 判断渠道是否已配置沃厅
        if(!service.isWotingOpen(authBean.getProvinceCode(), authBean.getEparchyCode(), authBean.getChannelId())) {
            log.info("渠道:{}未开通沃厅", authBean.getChannelId());
            return rspHelp.fail("9999", "未开通云厅");
        }

        // 判断所属地市是否已发布推广页面
        if(!service.isWtPageOpen(authBean.getProvinceCode(), authBean.getEparchyCode())) {
            log.info("省份:{},地市:{}未开通沃厅推广页面", authBean.getProvinceCode(), authBean.getEparchyCode());
            return rspHelp.fail("9999", "未开通云厅推广页面");
        }

        return rspHelp.success(null);
    }

}
