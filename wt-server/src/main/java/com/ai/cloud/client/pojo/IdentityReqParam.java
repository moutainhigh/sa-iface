package com.ai.cloud.client.pojo;

/**
 * @author pengqiang on 2017/5/17.
 */
public class IdentityReqParam {

    /**
     * 省份编码
     */
    private String provinceCode;
    /**
     * 地市编码
     */
    private String eparchyCode;
    /**
     * 区县编码
     */
    private String districtCode;
    /**
     * 证件类型：
     * 1：15位身份证编码
     * 2：17位身份证编码
     * 3：18位身份证编码
     */
    private String idType;
    /**
     * 证件编码
     */
    private String certNum;
    /**
     * 证件姓名
     */
    private String certName;
    /**
     * 曾用名
     */
    private String usedName;
    /**
     * 性别 M:男  F：女
     */
    private String sex;
    /**
     * 民族
     */
    private String nation;
    /**
     * 出生日期(yyyyMMdd)
     */
    private String birthday;
    /**
     * 认证类型：
     * 01 组合认证（先本地认证，如果本地不存在，调用公安系统认证）
     * 02 公安认证
     */
    private String certType;
    /**
     * 婚姻状况
     * 1：未婚
     * 2：已婚（初婚、复婚、再婚）
     * 3：丧偶
     * 4：离异
     */
    private String marriage;
    /**
     * 服务处所（工作单位）
     */
    private String servicePlace;
    /**
     * 文化程度
     * 1：研究生（研究生及以上、硕士及以上）
     * 2：本科（大学、大学本科）
     * 3：专科（大学专科）
     * 4：高中（中专）
     * 5：初中（小学、初中及以下、学龄前）
     * 6：文盲
     */
    private String cultureDegree;
    /**
     * 籍贯省市县
     */
    private String originPlace;
    /**
     * 所属省市县
     */
    private String belongPlace;
    /**
     * 出生地省市县
     */
    private String birthPlace;
    /**
     * 住址
     */
    private String address;

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getEparchyCode() {
        return eparchyCode;
    }

    public void setEparchyCode(String eparchyCode) {
        this.eparchyCode = eparchyCode;
    }

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getCertNum() {
        return certNum;
    }

    public void setCertNum(String certNum) {
        this.certNum = certNum;
    }

    public String getCertName() {
        return certName;
    }

    public void setCertName(String certName) {
        this.certName = certName;
    }

    public String getUsedName() {
        return usedName;
    }

    public void setUsedName(String usedName) {
        this.usedName = usedName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getMarriage() {
        return marriage;
    }

    public void setMarriage(String marriage) {
        this.marriage = marriage;
    }

    public String getServicePlace() {
        return servicePlace;
    }

    public void setServicePlace(String servicePlace) {
        this.servicePlace = servicePlace;
    }

    public String getCultureDegree() {
        return cultureDegree;
    }

    public void setCultureDegree(String cultureDegree) {
        this.cultureDegree = cultureDegree;
    }

    public String getOriginPlace() {
        return originPlace;
    }

    public void setOriginPlace(String originPlace) {
        this.originPlace = originPlace;
    }

    public String getBelongPlace() {
        return belongPlace;
    }

    public void setBelongPlace(String belongPlace) {
        this.belongPlace = belongPlace;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCertType() {
        return certType;
    }

    public void setCertType(String certType) {
        this.certType = certType;
    }

    @Override
    public String toString() {
        return "IdentityReqParam{" +
                "provinceCode='" + provinceCode + '\'' +
                ", eparchyCode='" + eparchyCode + '\'' +
                ", districtCode='" + districtCode + '\'' +
                ", idType='" + idType + '\'' +
                ", certNum='" + certNum + '\'' +
                ", certName='" + certName + '\'' +
                ", usedName='" + usedName + '\'' +
                ", sex='" + sex + '\'' +
                ", nation='" + nation + '\'' +
                ", birthday='" + birthday + '\'' +
                ", certType='" + certType + '\'' +
                ", marriage='" + marriage + '\'' +
                ", servicePlace='" + servicePlace + '\'' +
                ", cultureDegree='" + cultureDegree + '\'' +
                ", originPlace='" + originPlace + '\'' +
                ", belongPlace='" + belongPlace + '\'' +
                ", birthPlace='" + birthPlace + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
