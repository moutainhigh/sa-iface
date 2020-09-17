package com.ai.cloud.client.pojo.goods;


import lombok.Data;

import java.io.Serializable;

@Data
public class DataResponse<T> implements Serializable {
    private String code;
    private String msg;
    private T data;

    public DataResponse() {
    }

    public DataResponse(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public DataResponse(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static DataResponse ok(){
        return new DataResponse("0000","ok");
    }
    public static DataResponse ok(String msg){
        return new DataResponse("0000",msg);
    }
    public static <T> DataResponse<T> ok(T data){
        return new DataResponse("0000","ok",data);
    }
    public static DataResponse error(){
        return new DataResponse("9999","error");
    }
    public static DataResponse error(String msg){
        return new DataResponse("9999",msg);
    }

}