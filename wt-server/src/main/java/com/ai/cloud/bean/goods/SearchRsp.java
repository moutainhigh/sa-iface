package com.ai.cloud.bean.goods;

import lombok.Data;

import java.util.List;

@Data
public class SearchRsp {

    private String rspCode;

    private String rspDesc;

    private List<GoodsInfo> goodsItems;

}
