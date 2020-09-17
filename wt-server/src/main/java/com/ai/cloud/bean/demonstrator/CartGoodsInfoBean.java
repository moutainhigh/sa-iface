package com.ai.cloud.bean.demonstrator;

import lombok.Data;

/**
 * 购物车商品
 *
 * @author zhaanping
 * @date 2020-06-17
 */
@Data
public class CartGoodsInfoBean extends GoodsInfoBean implements Comparable<CartGoodsInfoBean> {

    /**
     * 申请模式 01 厂家配置  02 折扣买断  03 租赁租用
     */
    private String applyMode;

    /**
     * 商品申请数量（单位：台）
     */
    private String count;

    /**
     * 有效标记：0：已失效；1：有效 ；
     */
    private String validFlag;

    /**
     * 失效原因
     */
    private String invalidReason;

    @Override
    public int compareTo(CartGoodsInfoBean o) {
        return getGoodsId().compareTo(o.getGoodsId());
    }
}
