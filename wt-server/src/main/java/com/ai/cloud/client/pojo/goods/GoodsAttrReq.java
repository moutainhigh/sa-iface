package com.ai.cloud.client.pojo.goods;

import lombok.Data;

import java.util.List;

/**
 * @author JY
 * @date 2019/10/16 20:22
 */
@Data
public class GoodsAttrReq {

    private String goodsId;

    private List<String> attrList;

    public GoodsAttrReq(String goodsId,List<String> attrCodeList){
        this.goodsId = goodsId;
        this.attrList = attrCodeList;
    }

    public GoodsAttrReq() {}
}
