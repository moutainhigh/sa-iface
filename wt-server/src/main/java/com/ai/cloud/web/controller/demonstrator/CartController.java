package com.ai.cloud.web.controller.demonstrator;

import com.ai.cloud.auth.annotation.Auth;
import com.ai.cloud.auth.bean.AuthBean;
import com.ai.cloud.auth.component.impl.MsgoAuth;
import com.ai.cloud.base.exception.BusinessException;
import com.ai.cloud.base.lang.Rmap;
import com.ai.cloud.bean.base.BaseRsp;
import com.ai.cloud.bean.demonstrator.*;
import com.ai.cloud.constant.RspCodeEnum;
import com.ai.cloud.service.demonstrator.CartService;
import com.ai.cloud.service.demonstrator.DemoMachineCommonService;
import com.ai.cloud.tool.RspHelp;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Map;

import static com.ai.cloud.constant.DemoMachineConstant.*;

/**
 * 演示机购物车 控制器
 *
 * @author zhaanping
 * @date 2020-06-11
 */
@RestController
@Slf4j
public class CartController {

    @Autowired
    MsgoAuth msgoAuth;
    @Autowired
    RspHelp rspHelp;
    @Autowired
    private CartService cartService;
    @Autowired
    private DemoMachineCommonService commonService;

    /**
     * 申请列表(购物车列表）
     *
     * @param request
     * @return
     */
    @Auth
    @RequestMapping("/demonstrator/cartList")
    public BaseRsp qryCartList(HttpServletRequest request) {

        AuthBean authBean = msgoAuth.getUserInfo(request);
        String phoneNo = authBean.getPhoneNo();
        String channelId = authBean.getChannelId();
        try {
            if (!commonService.isDirector(authBean)) {
                throw new BusinessException(RspCode.DIRECTOR_AUTH_ERR, "权限不足，当前账户不是所属渠道的演示机厅长");
            }

            // 购物车商品列表
            List<CartGoodsInfoBean> cartGoodsList = cartService.qryCartGoodsList(channelId);
            // 购物车内有效商品总台数
            int applyTotalCount = 0;
            for (CartGoodsInfoBean cg : cartGoodsList) {
                if (ValidFlag.YES.equals(cg.getValidFlag())) {
                    applyTotalCount += Integer.parseInt(cg.getCount());
                }
            }
            // 示例 是否展示标记
            String showIllustrateFlag = cartService.qryShowIllustrateFlag(phoneNo);

            Map resultMap = Rmap.asMap("applyTotalCount", applyTotalCount, "showIllustrateFlag", showIllustrateFlag,
                    "goodsList", cartGoodsList);
            log.info("码上购-演示机-申请列表(购物车列表），登录账号：{}，渠道：{}，返回体：{}，操作成功",
                    phoneNo, channelId, JSON.toJSONString(resultMap));
            return rspHelp.success(resultMap);
        } catch (BusinessException be) {
            log.error("码上购-演示机-申请列表(购物车列表），登录账号：{}，渠道：{}，业务异常：{}|{}",
                    phoneNo, channelId, be.getMessageCode(), be.getMessage());
            return rspHelp.fail(be.getMessageCode(), be.getMessage());
        } catch (Exception e) {
            log.error("码上购-演示机-申请列表(购物车列表），登录账号：{}，渠道：{}，系统异常",
                    phoneNo, channelId, e);
            return rspHelp.fail(RspCodeEnum.ERROR.getCode(), "系统异常");
        }
    }

    /**
     * 申请模式（加入购物车）
     *
     * @param request
     * @param cartItemBean
     * @return
     */
    @Auth
    @RequestMapping("/demonstrator/addCart")
    public BaseRsp addCart(HttpServletRequest request, CartItemBean cartItemBean) {
        AuthBean authBean = msgoAuth.getUserInfo(request);
        String phoneNo = authBean.getPhoneNo();
        String channelId = authBean.getChannelId();
        try {
            if (!commonService.isDirector(authBean)) {
                throw new BusinessException(RspCode.DIRECTOR_AUTH_ERR, "权限不足，当前账户不是所属渠道的演示机厅长");
            }

            cartService.checkCartItemBean(channelId, cartItemBean, CartOperType.ADD);
            cartService.pushItem(channelId, cartItemBean);

            log.info("码上购-演示机-加入购物车，登录账号：{}，渠道：{}，请求参数：{}，操作成功",
                    phoneNo, channelId, JSON.toJSONString(cartItemBean));
            return rspHelp.success("");
        } catch (BusinessException be) {
            log.error("码上购-演示机-加入购物车，登录账号：{}，渠道：{}，请求参数：{}，业务异常：{}|{}",
                    phoneNo, channelId, JSON.toJSONString(cartItemBean), be.getMessageCode(), be.getMessage());
            return rspHelp.fail(be.getMessageCode(), be.getMessage());
        } catch (Exception e) {
            log.error("码上购-演示机-加入购物车，登录账号：{}，渠道：{}，请求参数：{}，系统异常",
                    phoneNo, channelId, JSON.toJSONString(cartItemBean), e);
            return rspHelp.fail(RspCodeEnum.ERROR.getCode(), "系统异常");
        }
    }
}
