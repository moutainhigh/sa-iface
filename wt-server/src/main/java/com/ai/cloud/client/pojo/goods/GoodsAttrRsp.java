package com.ai.cloud.client.pojo.goods;

import lombok.Data;

@Data
public class GoodsAttrRsp {
    private String goodsId;
    private String attrCode;
    private String attrName;
    private String attrValCode;
    private String attrValName;
    private String attrValDesc;
    private String eopAttrCode;
}
