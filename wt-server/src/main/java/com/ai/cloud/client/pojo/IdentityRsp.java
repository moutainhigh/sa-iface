package com.ai.cloud.client.pojo;


import com.ai.cloud.bean.base.BaseRsp;

/**
 * @author xiaoniu 2017/12/4.
 */
public class IdentityRsp extends BaseRsp {

    /**
     * 网纹照，JPEG照片编码， base64字符串编码
     */
    private String certPhoto;

    private String photoType;

    /**
     * 本次认证类型
     * 01 本地认证
     * 02 公安认证
     */
    private String certType;

    /**
     * 身份证姓名比对结果（一致/不一致）
     */
    private String certNameCompareResult;

    /**
     * 身份证号码比对结果（一致/不一致）
     */
    private String certNumCompareResult;

    /**
     * 曾用名比对结果（一致/不一致））
     */
    private String usedNameCompareResult;

    /**
     * 性别比对结果（一致/不一致）
     */
    private String sexCompareResult;

    /**
     * 民族比对结果（一致/不一致）
     */
    private String nationCompareResult;

    /**
     * 出生日期比对结果（一致/不一致）
     */
    private String birthdayCompareResult;

    /**
     * 文化程度比对结果（一致/不一致）
     */
    private String cultureDegreeCompareResult;

    /**
     * 婚姻状况比对结果（一致/不一致）
     */
    private String marriageCompareResult;

    /**
     * 服务处所比对结果（一致/不一致）
     */
    private String servicePlaceCompareResult;

    /**
     * 籍贯省市县比对结果（一致/不一致）
     */
    private String originPlaceCompareResult;

    /**
     * 所属省市县比对结果（一致/不一致）
     */
    private String belongPlaceCompareResult;

    /**
     * 出生地省市县比对结果（一致/不一致）
     */
    private String birthPlaceCompareResult;

    /**
     * 住址比对结果（一致/不一致）
     */
    private String addressCompareResult;


    public String getCertPhoto() {
        return certPhoto;
    }

    public void setCertPhoto(String certPhoto) {
        this.certPhoto = certPhoto;
    }

    public String getPhotoType() {
        return photoType;
    }

    public void setPhotoType(String photoType) {
        this.photoType = photoType;
    }

    public String getCertType() {
        return certType;
    }

    public void setCertType(String certType) {
        this.certType = certType;
    }

    public String getCertNameCompareResult() {
        return certNameCompareResult;
    }

    public void setCertNameCompareResult(String certNameCompareResult) {
        this.certNameCompareResult = certNameCompareResult;
    }

    public String getCertNumCompareResult() {
        return certNumCompareResult;
    }

    public void setCertNumCompareResult(String certNumCompareResult) {
        this.certNumCompareResult = certNumCompareResult;
    }

    public String getUsedNameCompareResult() {
        return usedNameCompareResult;
    }

    public void setUsedNameCompareResult(String usedNameCompareResult) {
        this.usedNameCompareResult = usedNameCompareResult;
    }

    public String getSexCompareResult() {
        return sexCompareResult;
    }

    public void setSexCompareResult(String sexCompareResult) {
        this.sexCompareResult = sexCompareResult;
    }

    public String getNationCompareResult() {
        return nationCompareResult;
    }

    public void setNationCompareResult(String nationCompareResult) {
        this.nationCompareResult = nationCompareResult;
    }

    public String getBirthdayCompareResult() {
        return birthdayCompareResult;
    }

    public void setBirthdayCompareResult(String birthdayCompareResult) {
        this.birthdayCompareResult = birthdayCompareResult;
    }

    public String getCultureDegreeCompareResult() {
        return cultureDegreeCompareResult;
    }

    public void setCultureDegreeCompareResult(String cultureDegreeCompareResult) {
        this.cultureDegreeCompareResult = cultureDegreeCompareResult;
    }

    public String getMarriageCompareResult() {
        return marriageCompareResult;
    }

    public void setMarriageCompareResult(String marriageCompareResult) {
        this.marriageCompareResult = marriageCompareResult;
    }

    public String getServicePlaceCompareResult() {
        return servicePlaceCompareResult;
    }

    public void setServicePlaceCompareResult(String servicePlaceCompareResult) {
        this.servicePlaceCompareResult = servicePlaceCompareResult;
    }

    public String getOriginPlaceCompareResult() {
        return originPlaceCompareResult;
    }

    public void setOriginPlaceCompareResult(String originPlaceCompareResult) {
        this.originPlaceCompareResult = originPlaceCompareResult;
    }

    public String getBelongPlaceCompareResult() {
        return belongPlaceCompareResult;
    }

    public void setBelongPlaceCompareResult(String belongPlaceCompareResult) {
        this.belongPlaceCompareResult = belongPlaceCompareResult;
    }

    public String getBirthPlaceCompareResult() {
        return birthPlaceCompareResult;
    }

    public void setBirthPlaceCompareResult(String birthPlaceCompareResult) {
        this.birthPlaceCompareResult = birthPlaceCompareResult;
    }

    public String getAddressCompareResult() {
        return addressCompareResult;
    }

    public void setAddressCompareResult(String addressCompareResult) {
        this.addressCompareResult = addressCompareResult;
    }

}
