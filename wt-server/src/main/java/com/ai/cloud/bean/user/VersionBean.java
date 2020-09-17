package com.ai.cloud.bean.user;

/**
 * @Author: Geafan
 * @Date: 2018/3/22 15:37
 */
public class VersionBean {

    //版本id
    private String versionId;
    //适用类型
    private String suitType;
    //版本内容
    private String versionContent;
    //版本发布时间
    private String effectiveTime;
    //最低支持的版本
    private String supportTo;
    //当前版本下载地址
    private String downloadUrl;

    public String getVersionId() {
        return versionId;
    }

    public void setVersionId(String versionId) {
        this.versionId = versionId;
    }

    public String getSuitType() {
        return suitType;
    }

    public void setSuitType(String suitType) {
        this.suitType = suitType;
    }

    public String getVersionContent() {
        return versionContent;
    }

    public void setVersionContent(String versionContent) {
        this.versionContent = versionContent;
    }

    public String getEffectiveTime() {
        return effectiveTime;
    }

    public void setEffectiveTime(String effectiveTime) {
        this.effectiveTime = effectiveTime;
    }

    public String getSupportTo() {
        return supportTo;
    }

    public void setSupportTo(String supportTo) {
        this.supportTo = supportTo;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    @Override
    public String toString() {
        return "VersionBean{" +
                "versionId='" + versionId + '\'' +
                ", suitType='" + suitType + '\'' +
                ", versionContent='" + versionContent + '\'' +
                ", effectiveTime=" + effectiveTime +
                ", supportTo='" + supportTo + '\'' +
                ", downloadUrl='" + downloadUrl + '\'' +
                '}';
    }
}
