package com.ai.cloud.client.pojo.goods;

import lombok.Data;

/**
 * {
 *       "attrCode": "string",
 *       "attrName": "string",
 *       "attrValCode": "string",
 *       "attrValDesc": "string",
 *       "attrValName": "string",
 *       "eopAttrCode": "string",
 *       "goodsId": "string"
 *     }
 */
@Data
public class GoodsAttrInfoBean {

    private String attrCode;
    private String attrName;
    private String attrValCode;
    private String attrValDesc;
    private String attrValName;
    private String goodsId;
    private String extraValue;
    private String extraValue3;

    public GoodsAttrInfoBean() {}

    public GoodsAttrInfoBean(GoodsByAttrListRsp rsp) {
        this.attrCode = rsp.getAttrCode();
        this.attrValCode = rsp.getValCode();
        this.attrValDesc = rsp.getValDesc();
        this.attrValName = rsp.getValName();
        this.extraValue = rsp.getExtraValue();
        this.extraValue3 = rsp.getExtraValue3();
    }

    public GoodsAttrInfoBean(GoodsAttrRsp rsp) {
        this.attrCode = rsp.getAttrCode();
        this.attrName = rsp.getAttrName();
        this.attrValCode = rsp.getAttrValCode();
        this.attrValDesc = rsp.getAttrValDesc();
        this.attrValName = rsp.getAttrValName();
        this.goodsId = rsp.getGoodsId();
    }
}
