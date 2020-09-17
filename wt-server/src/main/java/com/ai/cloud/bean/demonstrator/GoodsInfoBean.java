package com.ai.cloud.bean.demonstrator;

import lombok.Data;

/**
 * 演示机商品 实体类
 *
 * @author zhaanping
 * @date 2020-06-12
 */
@Data
public class GoodsInfoBean {

    /**
     * 商品编码
     */
    private String goodsId;

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 品牌编码
     */
    private String brandCode;

    /**
     * 品牌描述
     */
    private String brandName;

    /**
     * 型号编码
     */
    private String modelCode;

    /**
     * 型号描述
     */
    private String modelName;

    /**
     * 申请截止时间
     */
    private String appLimitTime;

    /**
     * 厂商配置的库存数据量
     */
    private String stockNum;

    /**
     * 是否可申请标记：0：不可申请；1：可申请 ；
     */
    private String canApplyFlag;

}
