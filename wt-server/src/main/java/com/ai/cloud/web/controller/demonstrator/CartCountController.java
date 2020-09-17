package com.ai.cloud.web.controller.demonstrator;

import com.ai.cloud.auth.annotation.Auth;
import com.ai.cloud.auth.bean.AuthBean;
import com.ai.cloud.auth.component.impl.MsgoAuth;
import com.ai.cloud.base.exception.BusinessException;
import com.ai.cloud.bean.base.BaseRsp;
import com.ai.cloud.bean.demonstrator.CartItemBean;
import com.ai.cloud.bean.demonstrator.CartCountBean;
import com.ai.cloud.constant.DemoMachineConstant;
import com.ai.cloud.service.demonstrator.CartService;
import com.ai.cloud.service.demonstrator.DemoMachineCommonService;
import com.ai.cloud.service.demonstrator.CartCountService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
public class CartCountController {
    private static final String DEL_FLAG = "0";

    @Autowired
    CartCountService service;
    @Autowired
    private DemoMachineCommonService commonService;
    @Autowired
    private CartService cartService;
    @Autowired
    MsgoAuth msgoAuth;

    @Auth
    @RequestMapping(value = "/ysj/changeCartCount")
    public BaseRsp queryGoodsList(HttpServletRequest req, CartCountBean paramBean) {
        log.info("演示机-购物车数量变更-请求参数:{}",paramBean);
        BaseRsp result = new BaseRsp();
        AuthBean authBean = msgoAuth.getUserInfo(req);
        try {
            //校验
            dealParam(authBean,paramBean);
            if (!commonService.isDirector(authBean)) {
                throw new BusinessException("0004", "权限不足，当前账户不是所属渠道的演示机厅长");
            }
            service.changeCount(paramBean);
            result.setRspCode("0000");
            result.setRspDesc("操作成功");
            return result;
        }catch (BusinessException e){
            log.error("演示机-购物车数量变更-业务异常:{},登录手机号:{}",e.getMessage(),paramBean.getPhoneNo());
            result.setRspCode(e.getMessageCode());
            result.setRspDesc(e.getMessage());
            return result;
        }catch (Exception e){
            log.error("演示机-购物车数量变更-系统异常:{},登录手机号:{}",e,paramBean.getPhoneNo());
            result.setRspCode("9999");
            result.setRspDesc("系统异常");
            return result;
        }
    }

    public void dealParam(AuthBean authBean,CartCountBean paramBean){
        paramBean.setChannelId(authBean.getChannelId());
        paramBean.setPhoneNo(authBean.getPhoneNo());
        log.info("演示机-购物车数量变更-请求参数:{}",paramBean);
        if (StringUtils.isAnyBlank(paramBean.getChannelId(),
                paramBean.getGoodsId(),paramBean.getOperateFlag())){
            log.error("演示机-购物车数量变更-参数有误-登录手机号:{}",paramBean.getPhoneNo());
            throw new BusinessException("0001","请求参数错误");
        }
        if (DEL_FLAG.equals(paramBean.getOperateFlag())){
            log.info("演示机-购物车数量变更-删除操作-登录手机号:{}",paramBean.getPhoneNo());
            return;
        }
        //变更数量时校验
        dealCartData(paramBean);
    }

    public void dealCartData(CartCountBean paramBean){
        CartItemBean cartItem = new CartItemBean();
        cartItem.setApplyMode(paramBean.getApplicationMode());
        cartItem.setCount(paramBean.getGoodsCount());
        cartItem.setGoodsId(paramBean.getGoodsId());
        cartService.checkCartItemBean(paramBean.getChannelId(),cartItem, DemoMachineConstant.CartOperType.UPDATE_COUNT);
    }
}
