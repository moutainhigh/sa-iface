package com.ai.cloud.bean.demonstrator;

import lombok.Data;

@Data
public class OrderGoodsInfoBean {

    /**
     * 订单id
     */
    private String orderId;
    /**
     * 商品名字
     */
    private String goodsName;
    /**
     * 商品id
     */
    private String goodsId;
    /**
     * 申请商品数量
     */
    private int applyCount;
    /**
     * 商品状态 00 初始值 01 已发货 02 已签收 03 已驳回
     */
    private String goodsState;
    /**
     * 商品状态描述
     */
    private String goodsStateDesc;
    /**
     * 商品品牌编码
     */
    private String goodsBrand;
    /**
     * 商品品牌描述
     */
    private String goodsBrandDesc;
    /**
     * 商品型号编码
     */
    private String goodsType;
    /**
     * 商品型号描述
     */
    private String goodsTypeDesc;
    /**
     * 创建时间
     */
    private String createTime;
}
