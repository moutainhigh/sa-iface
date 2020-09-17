package com.ai.cloud.bean.demonstrator;

import lombok.Data;


@Data
public class OrderLogInfoBean {

    /**
     * 省份编码
     */
    private String provinceCode;
    /**
     * 订单标识
     */
    private String orderId;
    /**
     * 商品标识
     */
    private String goodsId;
    /**
     * 操作人ID
     */
    private String operatorId;
    /**
     * 操作人姓名
     */
    private String operateName;
    /**
     * 操作人手机号码
     */
    private String operatePhone;
    /**
     * 操作时间
     */
    private String operateTime;
    /**
     * 订单原来的状态
     */
    private String originalState;
    /**
     * 订单现在状态
     */
    private String currentState;
    /**
     * 商品原来状态
     */
    private String goodsOriginalState;
    /**
     * 商品现在状态：01 发货，02 签收
     */
    private String goodsCurrentState;
    /**
     * 处理结果描述
     */
    private String dealContent;
    /**
     * 处理结果编码
     */
    private String resultCode;
    /**
     * 处理类型
     */
    private String dealType;
    /**
     * 处理结果
     */
    private String resultInfo;
    /**
     * 操作类型：01：客户操作 02：操作员操作 03：系统操作
     */
    private String operateType;
    /**
     * 展示标记：0：前台后台都不显示 1：前台显示 2：后台显示 3：前台后台都显示
     */
    private String showFlag;
}
