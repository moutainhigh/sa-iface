package com.ai.cloud.client.pojo.goods;

import lombok.Data;

/**
 * @author wzx
 * @date 2019/10/10 19:33
 */
@Data
public class GoodsStockReq {
    private String goodsId;
    private String provinceCode;
    private String cityCode;
    private String transId;

    public GoodsStockReq(String goodsId,String provinceCode,String cityCode){
        this.goodsId = goodsId;
        this.provinceCode = provinceCode;
        this.cityCode = cityCode;
    }
}
