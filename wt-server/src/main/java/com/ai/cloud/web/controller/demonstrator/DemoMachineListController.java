package com.ai.cloud.web.controller.demonstrator;

import com.ai.aif.dadb.status.client.utils.CollectionUtils;
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
import com.ai.cloud.service.demonstrator.DemoMachineListService;
import com.ai.cloud.tool.RspHelp;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

import static com.ai.cloud.constant.DemoMachineConstant.*;

/**
 * 演示机列表 控制器
 *
 * @author zhaanping
 * @date 2020-06-12
 */
@RestController
@Slf4j
public class DemoMachineListController {

    @Autowired
    DemoMachineCommonService commonService;

    @Autowired
    DemoMachineListService listService;

    @Autowired
    CartService cartService;

    @Autowired
    MsgoAuth msgoAuth;

    @Autowired
    RspHelp rspHelp;

    /**
     * 可申请列表
     *
     * @param request
     * @param queryBean
     * @return
     */
    @Auth
    @RequestMapping("/demonstrator/canApplyList")
    public BaseRsp qryCanApplyList(HttpServletRequest request, GoodsQueryBean queryBean) {

        AuthBean authBean = msgoAuth.getUserInfo(request);
        String phoneNo = authBean.getPhoneNo();
        String channelId = authBean.getChannelId();
        try {
            if (!commonService.isDirector(authBean)) {
                throw new BusinessException(RspCode.DIRECTOR_AUTH_ERR, "权限不足，当前账户不是所属渠道的演示机厅长");
            }
            if (queryBean == null) {
                return rspHelp.fail(RspCodeEnum.PARAM_ERROR.getCode(), "请求参数不能为空");
            }
            // 分页
            queryBean.setProvinceCode(authBean.getProvinceCode());
            queryBean.setCityCode(authBean.getEparchyCode());
            queryBean.setChannelId(authBean.getChannelId());
            commonService.buildPageQuery(queryBean);
            // 品牌、型号列表
            List<GoodsBrandBean> brandList = listService.qryGoodsBrandList(authBean);
            List<GoodsModelBean> modelList = listService.qryGoodsModelList(authBean);
            // 商品列表
            List<GoodsInfoBean> goodsList = listService.qryCanApplyGoodsList(queryBean);

            // 组装 是否可以申请的标记：已经加入购物车的商品 不可申请
            List<CartItemBean> cartList = cartService.getAllItems(channelId);
            for (GoodsInfoBean g : goodsList) {
                g.setCanApplyFlag(CanApply.YES);
                for (CartItemBean c : cartList) {
                    if (c.getGoodsId().equals(g.getGoodsId())) {
                        g.setCanApplyFlag(CanApply.NO);
                        break;
                    }
                }
            }
            // 购物车中商品种类（含失效商品）
            int cartNum = CollectionUtils.isEmpty(cartList) ? 0 : cartList.size();
            String cartApplyMode = CollectionUtils.isEmpty(cartList) ? "" : cartList.get(0).getApplyMode();

            Map resultMap = Rmap.asMap("referenceId", queryBean.getReferenceId(),
                    "brandList", brandList, "modelList", modelList,
                    "cartNum", cartNum, "cartApplyMode", cartApplyMode,
                    "goodsList", goodsList);
            log.info("码上购-演示机-可申请列表，登录账号：{}，渠道：{}，请求参数：{}，返回体：{}，操作成功",
                    phoneNo, channelId, JSON.toJSONString(queryBean), JSON.toJSONString(resultMap));
            return rspHelp.success(resultMap);
        } catch (BusinessException be) {
            log.error("码上购-演示机-可申请列表，登录账号：{}，渠道：{}，请求参数：{}，业务异常：{}|{}",
                    phoneNo, channelId, JSON.toJSONString(queryBean), be.getMessageCode(), be.getMessage());
            return rspHelp.fail(be.getMessageCode(), be.getMessage());
        } catch (Exception e) {
            log.error("码上购-演示机-可申请列表，登录账号：{}，渠道：{}，请求参数：{}，系统异常",
                    phoneNo, channelId, JSON.toJSONString(queryBean), e);
            return rspHelp.fail(RspCodeEnum.ERROR.getCode(), "系统异常");
        }
    }

    /**
     * 已申请列表
     *
     * @param request
     * @param queryBean
     * @return
     */
    @Auth
    @RequestMapping("/demonstrator/appliedList")
    public BaseRsp qryAppliedList(HttpServletRequest request, OrderQueryBean queryBean) {
        AuthBean authBean = msgoAuth.getUserInfo(request);
        String phoneNo = authBean.getPhoneNo();
        String channelId = authBean.getChannelId();
        try {
            if (!commonService.isDirector(authBean)) {
                throw new BusinessException(RspCode.DIRECTOR_AUTH_ERR, "权限不足，当前账户不是所属渠道的演示机厅长");
            }
            if (queryBean == null) {
                return rspHelp.fail(RspCodeEnum.PARAM_ERROR.getCode(), "请求参数不能为空");
            }
            // 分页
            queryBean.setChannelId(authBean.getChannelId());
            commonService.buildPageQuery(queryBean);

            List<OrderInfoBean> orderList = listService.qryOrderList(queryBean);

            Map resultMap = Rmap.asMap("referenceId", queryBean.getReferenceId(), "orderList", orderList);
            log.info("码上购-演示机-已申请列表，登录账号：{}，渠道：{}，请求参数：{}，返回体：{}，操作成功",
                    phoneNo, channelId, JSON.toJSONString(queryBean), JSON.toJSONString(resultMap));
            return rspHelp.success(resultMap);
        } catch (BusinessException be) {
            log.error("码上购-演示机-已申请列表，登录账号：{}，渠道：{}，请求参数：{}，业务异常：{}|{}",
                    phoneNo, channelId, JSON.toJSONString(queryBean), be.getMessageCode(), be.getMessage());
            return rspHelp.fail(be.getMessageCode(), be.getMessage());
        } catch (Exception e) {
            log.error("码上购-演示机-已申请列表，登录账号：{}，渠道：{}，请求参数：{}，系统异常",
                    phoneNo, channelId, JSON.toJSONString(queryBean), e);
            return rspHelp.fail(RspCodeEnum.ERROR.getCode(), "系统异常");
        }
    }

    /**
     * 订单确认完成
     *
     * @param request
     * @param orderId
     */
    @Auth
    @RequestMapping("/demonstrator/confirmOrder")
    public BaseRsp confirmOrder(HttpServletRequest request, @RequestParam("orderId") String orderId) {
        AuthBean authBean = msgoAuth.getUserInfo(request);
        String phoneNo = authBean.getPhoneNo();
        String channelId = authBean.getChannelId();
        try {
            if (!commonService.isDirector(authBean)) {
                throw new BusinessException(RspCode.DIRECTOR_AUTH_ERR, "权限不足，当前账户不是所属渠道的演示机厅长");
            }
            if (StringUtils.isBlank(orderId)) {
                return rspHelp.fail(RspCodeEnum.PARAM_ERROR.getCode(), "订单编码不能为空");
            }

            int row = listService.confirmOrder(authBean, orderId);

            log.info("码上购-演示机-订单确认完成，登录账号：{}，渠道：{}，订单号：{}，受影响行数：{}，操作成功", phoneNo, channelId, orderId, row);
            return rspHelp.success("");
        } catch (BusinessException be) {
            log.error("码上购-演示机-订单确认完成，登录账号：{}，渠道：{}，订单号：{}，业务异常：{}|{}",
                    phoneNo, channelId, orderId, be.getMessageCode(), be.getMessage());
            return rspHelp.fail(be.getMessageCode(), be.getMessage());
        } catch (Exception e) {
            log.error("码上购-演示机-订单确认完成，登录账号：{}，渠道：{}，订单号：{}，异常",
                    phoneNo, channelId, orderId, e);
            return rspHelp.fail(RspCodeEnum.ERROR.getCode(), "系统异常");
        }
    }

}
