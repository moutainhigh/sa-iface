package com.ai.cloud.client.pojo;

/**
 * @author xiaoniu 2018/3/14.
 */
public class NumQryCacheRsp {
    /**
     * 查询员工信息
     */
    private String staffId;
    private String merchantId;
    private String channelId;
    private String channelType;
    /**
     * 根据商品id查询号码组
     */
    private String groupKey;

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }

    public String getGroupKey() {
        return groupKey;
    }

    public void setGroupKey(String groupKey) {
        this.groupKey = groupKey;
    }
}
