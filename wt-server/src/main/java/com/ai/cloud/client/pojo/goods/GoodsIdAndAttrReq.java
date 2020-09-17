package com.ai.cloud.client.pojo.goods;

import lombok.Data;

import java.util.List;

@Data
public class GoodsIdAndAttrReq {

    private List<String> goodsIdList;
    private List<String> goodsAttrList;
}
