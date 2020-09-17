package com.ai.cloud.client.pojo.goods;

import lombok.Data;

import java.util.List;

@Data
public class PhotoReq {
    private List<String> goodsIdList;
    private List<String> goodsSizeList;

    public PhotoReq(){}

    public PhotoReq(List<String> goodsIdList,List<String> goodsSizeList){
        this.goodsIdList = goodsIdList;
        this.goodsSizeList = goodsSizeList;
    }
}
