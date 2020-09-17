package com.ai.cloud.client.pojo.goods;

/**
 * @author 95831
 * TF_B_ORDER_OPTPRODUCT
 */
public class ProductMainBeen {
    private String goodsName;
    private String productId;
    private String productName;
    private String productType;
    private String productCategory;
    private String productDesc;
    private String productValue;
    private String enterpriseName;
    private String enterpriseVal;
    private String version;
    private String tmplId;
    private String optFeeCode;
    private String companyCode;
    public ProductMainBeen(){}
    public ProductMainBeen(ProductRsp rsp) {
        if (rsp == null) {
            return;
        }
        this.goodsName = rsp.getGoodsName();
        this.productId = rsp.getProductId();
        this.productName = rsp.getProductName();
        this.productType = rsp.getProductType();
        this.productCategory = rsp.getProductCategory();
        this.productDesc = rsp.getProductDesc();
        this.productValue = rsp.getProductValue();
        this.enterpriseName = rsp.getEnterpriseName();
        this.enterpriseVal = rsp.getEnterpriseVal();
        this.version = rsp.getVersion();
    }


    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public String getProductValue() {
        return productValue;
    }

    public void setProductValue(String productValue) {
        this.productValue = productValue;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public String getEnterpriseVal() {
        return enterpriseVal;
    }

    public void setEnterpriseVal(String enterpriseVal) {
        this.enterpriseVal = enterpriseVal;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getTmplId() {
        return tmplId;
    }

    public void setTmplId(String tmplId) {
        this.tmplId = tmplId;
    }

    public String getOptFeeCode() {
        return optFeeCode;
    }

    public void setOptFeeCode(String optFeeCode) {
        this.optFeeCode = optFeeCode;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    @Override
    public String toString() {
        return "ProductMainBeen{" +
                "goodsName='" + goodsName + '\'' +
                ", productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", productType='" + productType + '\'' +
                ", productCategory='" + productCategory + '\'' +
                ", productDesc='" + productDesc + '\'' +
                ", productValue='" + productValue + '\'' +
                ", enterpriseName='" + enterpriseName + '\'' +
                ", enterpriseVal='" + enterpriseVal + '\'' +
                ", version='" + version + '\'' +
                ", tmplId='" + tmplId + '\'' +
                ", optFeeCode='" + optFeeCode + '\'' +
                ", companyCode='" + companyCode + '\'' +
                '}';
    }
}
