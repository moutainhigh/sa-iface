package com.ai.cloud.bean.demonstrator;

import lombok.Data;

/**
 * 购物车 实体类
 *
 * @author zhaanping
 * @date 2020-06-11
 */
@Data
public class CartItemBean {

    /**
     * 商品编码
     */
    private String goodsId;

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 申请模式 01 厂家配置  02 折扣买断  03 租赁租用
     */
    private String applyMode;

    /**
     * 数量（单位：台）
     */
    private String count;
}
