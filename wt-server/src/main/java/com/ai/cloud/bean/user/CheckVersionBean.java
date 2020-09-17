package com.ai.cloud.bean.user;

import com.alibaba.fastjson.JSON;

/**
 * @Author: wangzd
 * @Date: 2018/12/04
 */
public class CheckVersionBean {

    /**
     * 最新版本id
     */
    private String versionId;
    /**
     * 适用类型
     */
    private String suitType;
    /**
     * 最新版本内容
     */
    private String versionContent;
    /**
     * 最新版本发布时间
     */
    private String effectiveTime;
    /**
     * 最新版本下载地址
     */
    private String downloadUrl;
    /**
     * 最低支持的版本
     */
    private String supportTo;
    /**
     * 推荐升级的版本
     */
    private String recommendTo;

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

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getSupportTo() {
        return supportTo;
    }

    public void setSupportTo(String supportTo) {
        this.supportTo = supportTo;
    }

    public String getRecommendTo() {
        return recommendTo;
    }

    public void setRecommendTo(String recommendTo) {
        this.recommendTo = recommendTo;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
