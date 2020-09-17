package com.ai.cloud.web.controller.goods;

import com.ai.cloud.auth.annotation.Auth;
import com.ai.cloud.auth.bean.AuthBean;
import com.ai.cloud.auth.component.impl.MsgoAuth;
import com.ai.cloud.base.exception.BusinessException;
import com.ai.cloud.bean.base.BaseRsp;
import com.ai.cloud.service.goods.GoodsDetailService;
import com.ai.cloud.tool.RspHelp;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Auther JY
 * @Date 2020/3/12
 * DON'T REGRET ANYTHING
 * 沃厅商品详情页
 */
@Slf4j
@RestController
public class GoodsDetailController {

    @Autowired
    GoodsDetailService goodsDetailService;
    @Autowired
    RspHelp rspHelp;
    @Autowired
    MsgoAuth msgoAuth;

    @Auth
    @RequestMapping("/wohall/goods/detail")
    public BaseRsp goodsDetail(String goodsId, HttpServletRequest request) {
        AuthBean authBean = msgoAuth.getUserInfo(request);
        log.info("码上购-沃厅-商品详情-入参：{},账号:{}", goodsId,authBean.getPhoneNo());
        if (StringUtils.isBlank(goodsId)) {
            return rspHelp.error("1001", "入参为空");
        }
        try {
            Map map = goodsDetailService.goodsDetail(goodsId,authBean);
            log.info("码上购-沃厅-商品详情-入参：{},返回：{}", goodsId, JSON.toJSONString(map));
            return rspHelp.success(map);
        } catch (BusinessException be) {
            log.info("码上购-沃厅-商品详情-异常-id：{}", goodsId, be);
            return rspHelp.fail("1009", be.getMessage());
        } catch (Exception e) {
            log.info("码上购-沃厅-商品详情-异常-id：{}", goodsId, e);
            return rspHelp.fail("9999", "查询异常");
        }
    }
}
