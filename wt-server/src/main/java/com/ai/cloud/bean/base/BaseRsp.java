package com.ai.cloud.bean.base;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * @author xiaoniu
 * 响应类
 */
public class BaseRsp<T> implements Serializable{

    @JSONField(name = "rspCode",ordinal = 1)
    private String rspCode;

    @JSONField(name = "rspDesc",ordinal = 2)
    private String rspDesc;

    /**
     * 报文体
     * 放置业务参数
     */
    @JSONField(name = "rspBody",ordinal = 3)
    private  T  rspBody;


    /**
     * 流水号
     */
    @JSONField(name = "traceId")
    private String traceId;
    /**
     * 响应时间
     */
    @JSONField(name = "timestamp")
    private String timestamp;


    public String getRspCode() {
        return rspCode;
    }

    public void setRspCode(String rspCode) {
        this.rspCode = rspCode;
    }

    public String getRspDesc() {
        return rspDesc;
    }

    public void setRspDesc(String rspDesc) {
        this.rspDesc = rspDesc;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public T getRspBody() {
        return rspBody;
    }

    public void setRspBody(T rspBody) {
        this.rspBody = rspBody;
    }
}
