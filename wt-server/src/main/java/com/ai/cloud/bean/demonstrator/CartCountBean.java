package com.ai.cloud.bean.demonstrator;

import lombok.Data;

@Data
public class CartCountBean {
    /**
     * 商品id
     */
    private String goodsId;
    /**
     *操作区分 0：删除 1：加 2：减
     */
    private String operateFlag;
    /**
     * 加减数量
     */
    private String goodsCount;
    /**
     * 登录号码
     */
    private String phoneNo;
    /**
     * channelId
     */
    private String channelId;
    /**
     * 申请模式
     */
    private String applicationMode;
}
