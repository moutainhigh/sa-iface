package com.ai.cloud.bean.goods;

import lombok.Data;

@Data
public class GoodsSearchReq {

    // 商品ID(支持多个商品ID查询，多个以都好分隔，例如:361909186315,361912130692)
    private String goodsId;

    // 搜索关键字
    private String keyword;

    private String provinceCode;

    // 业务分类(裸机,合约机)
    private String busItem;

    // 终端类型(泛终端,手机,配件)
    private String termType;

    // 品牌名称
    private String termBrand;

    // 价格排序(0:降序；1升序，为空默认降序排列)
    private String priceSort;

    //起始价格
    private String beginPrice;

    // 结束价格
    private String endPrice;

    // 每页返回商品数量(为空默认10)
    private String pageSize;

    // 页码(1,2,3,4……为空默认1 )
    private String pageNum;

    // 加密数据
    private String a;
}
