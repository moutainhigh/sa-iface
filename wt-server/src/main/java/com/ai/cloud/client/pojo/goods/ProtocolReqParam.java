package com.ai.cloud.client.pojo.goods;

/**
 * @author xiaoniu 2018/3/24.
 */
public class ProtocolReqParam {
    private String goodsId;
    private String activityType;
    private String province;
    private String city;
    private String machineType;

    public String getMachineType() {
        return machineType;
    }

    public void setMachineType(String machineType) {
        this.machineType = machineType;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "ProtocolReqParam{" +
                "goodsId='" + goodsId + '\'' +
                ", activityType='" + activityType + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", machineType='" + machineType + '\'' +
                '}';
    }
}
