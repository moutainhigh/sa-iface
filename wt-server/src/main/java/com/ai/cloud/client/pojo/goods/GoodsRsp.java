package com.ai.cloud.client.pojo.goods;

import lombok.Data;

import java.util.Date;

/**
 * @author wzx
 * @date 2019/10/10 19:33
 */
@Data
public class GoodsRsp {
    private String goodsId;
    private String goodsName;
    private String version;
    private String goodsState;
    private String merchantId;
    private String createStaffId;
    private String tmplId;
    private String basePrice;
    private String favorablePrice;
    private String hqGoodsId;
    private Date createTime;
    private String goodsLable;
}
