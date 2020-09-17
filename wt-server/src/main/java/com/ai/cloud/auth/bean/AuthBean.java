package com.ai.cloud.auth.bean;

import com.alibaba.druid.util.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 登录用户信息
 */
public class AuthBean {

    /**
     * 用户Id
     */
    private String uid;

    /**
     * 用户ID，可能是员工ID、发展人ID和客户经理ID
     */
    private String userId;

    /**
     * 用户名 客户端展示
     */
    private String userName;

    /**
     * 用户角色，dev：发展人 stf：员工 ps：自然人
     */
    private String userSource;

    /**
     * 新老用户标记 o:老用户 n:新用户
     */
    private String userType;

    /**
     * 是否有多个发展人 1：多个
     */
    private String multi;

    private String provinceCode;
    private String provinceName;
    private String eparchyCode;
    private String eparchyName;
    private String departId;
    private String departCode;
    private String departName;
    private String channelId;
    private String channelName;

    /**
     * 渠道类型 10：自有渠道 20：社会渠道
     */
    private String channelMainType;

    /**
     * 登录人员手机号
     */
    private String phoneNo;

    /**
     * 是否绑定了发展人 0：未绑定 1：已绑定
     */
    private String devBound;

    private String devId;
    private String devName;

    /**
     * 发展人手机号
     */
    private String devPhoneNo;

    /**
     * 设备唯一标识
     */
    private String deviceToken;

    /**
     * 设备类型 i:ios a:安卓
     */
    private String deviceType;

    /**
     * 头像URL
     */
    private String avatorUrl;
    private String email;

    /**
     * 本次登录的验证码
     */
    private String captcha;

    /**
     * 上线用户ID
     */
    private String parentUid;

    /**
     * 上线用户手机号
     */
    private String parentPhoneNo;

    /**
     * 根上线用户ID
     */
    private String rootUid;

    /**
     * 根上线用户手机号
     */
    private String rootPhoneNo;

    /**
     * 用户缓存token值
     */
    private String token;

    /**
     * app系统及版本号
     */
    private String os;
    private String version;


    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserSource() {
        return userSource;
    }

    public void setUserSource(String userSource) {
        this.userSource = userSource;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getMulti() {
        return multi;
    }

    public void setMulti(String multi) {
        this.multi = multi;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getEparchyCode() {
        return eparchyCode;
    }

    public void setEparchyCode(String eparchyCode) {
        this.eparchyCode = eparchyCode;
    }

    public String getEparchyName() {
        return eparchyName;
    }

    public void setEparchyName(String eparchyName) {
        this.eparchyName = eparchyName;
    }

    public String getDepartId() {
        return departId;
    }

    public void setDepartId(String departId) {
        this.departId = departId;
    }

    public String getDepartCode() {
        return departCode;
    }

    public void setDepartCode(String departCode) {
        this.departCode = departCode;
    }

    public String getDepartName() {
        return departName;
    }

    public void setDepartName(String departName) {
        this.departName = departName;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getChannelMainType() {
        return channelMainType;
    }

    public void setChannelMainType(String channelMainType) {
        this.channelMainType = channelMainType;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getDevBound() {
        return devBound;
    }

    public void setDevBound(String devBound) {
        this.devBound = devBound;
    }

    public String getDevId() {
        return devId;
    }

    public void setDevId(String devId) {
        this.devId = devId;
    }

    public String getDevName() {
        return devName;
    }

    public void setDevName(String devName) {
        this.devName = devName;
    }

    public String getDevPhoneNo() {
        return devPhoneNo;
    }

    public void setDevPhoneNo(String devPhoneNo) {
        this.devPhoneNo = devPhoneNo;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getAvatorUrl() {
        return avatorUrl;
    }

    public void setAvatorUrl(String avatorUrl) {
        this.avatorUrl = avatorUrl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public String getParentUid() {
        return parentUid;
    }

    public void setParentUid(String parentUid) {
        this.parentUid = parentUid;
    }

    public String getParentPhoneNo() {
        return parentPhoneNo;
    }

    public void setParentPhoneNo(String parentPhoneNo) {
        this.parentPhoneNo = parentPhoneNo;
    }

    public String getRootUid() {
        return rootUid;
    }

    public void setRootUid(String rootUid) {
        this.rootUid = rootUid;
    }

    public String getRootPhoneNo() {
        return rootPhoneNo;
    }

    public void setRootPhoneNo(String rootPhoneNo) {
        this.rootPhoneNo = rootPhoneNo;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getVersion() {
        return version;
    }
    public boolean isIOS() {
        return StringUtils.equalsIgnoreCase("IOS", os);
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }
}
