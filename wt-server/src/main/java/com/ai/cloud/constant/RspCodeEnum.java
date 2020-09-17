package com.ai.cloud.constant;

public enum RspCodeEnum {

    SUCCESS("0000"), ERROR("9999"), NOLOGIN("1001"), LOGIN_INVALID("1002"), VERSION_INVALID("1003"), NO_ORDER("2001"), PARAM_ERROR("3001");
    private String code;

    private RspCodeEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return code;
    }

}
