package com.ai.cloud.client.pojo.goods;


import lombok.Data;

/**
 * @author wzx
 * @date 2019/10/12 11:18
 */
@Data
public class ProductRsp {
    private String productId;
    private String productName;
    private String productType;
    private String productValue;
    private String productDesc;
    private String productCategory;
    private String enterpriseName;
    private String enterpriseVal;
    private String goodsName;
    private String version;
}
