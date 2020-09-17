package com.ai.cloud.client.pojo;

/**
 * @author xiaoniu 2018/3/15.
 */
public class NumQryCacheReq {
    /**
     * eg.11_110
     */
    private String provinceCityCode;
    /**
     * 商品编码
     */
    private String goodsId;

    /**
     * 场景编码 默认0 0.号卡类 1.融合宽带
     */
    private String sceneType;

    public String getSceneType() {
        return sceneType;
    }

    public void setSceneType(String sceneType) {
        this.sceneType = sceneType;
    }

    public String getProvinceCityCode() {
        return provinceCityCode;
    }

    public void setProvinceCityCode(String provinceCityCode) {
        this.provinceCityCode = provinceCityCode;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }
}
