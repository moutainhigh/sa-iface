package com.ai.cloud.bean.base;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * @author wangzd
 * 响应类
 */
public class CallNewOrderCenterRsp<T> implements Serializable{

    @JSONField(name = "rspCode",ordinal = 1)
    private String rspCode;

    @JSONField(name = "rspDesc",ordinal = 2)
    private String rspDesc;

    /**
     * 报文体
     * 放置业务参数
     */
    @JSONField(name = "body",ordinal = 3)
    private  T  body;

    /**
     * 流水号
     */
    @JSONField(name = "uuid")
    private String uuid;

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

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
