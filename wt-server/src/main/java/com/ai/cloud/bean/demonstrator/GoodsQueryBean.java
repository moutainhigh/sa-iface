package com.ai.cloud.bean.demonstrator;

import lombok.Data;

/**
 * 演示机商品查询 实体类
 *
 * @author zhaanping
 * @date 2020-06-12
 */
@Data
public class GoodsQueryBean extends PageQueryBean {

    /**
     * 搜索关键字
     */
    private String keyword;

    /**
     * 品牌编码
     */
    private String brandCode;

    /**
     * 型号编码
     */
    private String modelCode;

    /**
     * 省份编码
     */
    private String provinceCode;

    /**
     * 地市编码
     */
    private String cityCode;
}
