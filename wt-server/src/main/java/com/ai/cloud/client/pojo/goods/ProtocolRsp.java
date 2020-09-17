package com.ai.cloud.client.pojo.goods;

import java.sql.Blob;

/**
 * @author xiaoniu 2018/3/24.
 */
public class ProtocolRsp {

    private String id;
    private String version;
    private String type;
    private Blob content;
    private String photo;
    private String releaseNo;
    private String scenceCode;
    private String contentStr;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getReleaseNo() {
        return releaseNo;
    }

    public void setReleaseNo(String releaseNo) {
        this.releaseNo = releaseNo;
    }

    public String getScenceCode() {
        return scenceCode;
    }

    public void setScenceCode(String scenceCode) {
        this.scenceCode = scenceCode;
    }

    public Blob getContent() {
        return content;
    }

    public void setContent(Blob content) {
        this.content = content;
    }

    public String getContentStr() {
        return contentStr;
    }

    public void setContentStr(String contentStr) {
        this.contentStr = contentStr;
    }
}
