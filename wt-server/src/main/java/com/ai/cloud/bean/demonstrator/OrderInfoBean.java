package com.ai.cloud.bean.demonstrator;

import lombok.Data;

import java.util.List;

@Data
public class OrderInfoBean {

    /**
     * 订单id
     */
    private String orderId;
    /**
     * 省份编码
     */
    private String provinceCode;
    /**
     * 地市编码
     */
    private String cityCode;
    /**
     * 申请人号码
     */
    private String staffId;
    /**
     * 申请人渠道id
     */
    private String channelId;
    /**
     * 申请人渠道名字
     */
    private String channelName;
    /**
     * 申请模式：01 厂家配置 02 折扣买断 03 租赁租用
     */
    private String applyMode;
    /**
     * 申请模式描述
     */
    private String applyModeDesc;
    /**
     * 申请单状态：1 地市待审核 2 地市审核通过 3 地市审核驳回 4 省份审核通过 5 省份审核驳回 6 已完成
     */
    private int orderState;
    /**
     * 申请单状态描述
     */
    private String orderStateDesc;
    /**
     * 驳回原因
     */
    private String refuseDesc;

    private String createTime;

    private String updateTime;

    /**
     * 是否可以确认完成的标记：0：不可以；1：可以；
     */
    private String canConfirmFlag;

    /**
     * 申请人名字
     */
    private String staffName;
    /**
     * 商品信息
     */
    private List<OrderGoodsInfoBean> goodsInfoList;
}
