package com.ai.cloud.client.pojo.goods;

import lombok.Data;


@Data
public class GoodsQQReq {
    private String goodsId;

    private String productId;

    public GoodsQQReq(String goodsId,String productId){
        this.goodsId = goodsId;
        this.productId = productId;
    }
}
