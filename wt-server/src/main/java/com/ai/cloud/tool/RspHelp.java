package com.ai.cloud.tool;


import com.ai.cloud.base.lang.Dates;
import com.ai.cloud.bean.base.BaseRsp;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author xiaoniu
 * 服务响应 帮助类
 */
@Component
public class RspHelp {

    private static final String SUCCESS_CODE = "0000";
    private static final String SUCCESS_DESC = "操作成功";
    private static final String ERROR_CODE = "9999";
    private static final String ERROR_DESC = "服务异常";

    @Autowired
    private HttpServletRequest request;


    public String obtainTraceId(){
        return request.getHeader("x-b3-traceId");
    }

    /**success**/
    public  <T> BaseRsp<T> success(T rspBody){
        return success(SUCCESS_DESC,rspBody);
    }

    /**success**/
    public  <T> BaseRsp<T> success(String respDesc,T rspBody){
        BaseRsp rsp = new BaseRsp();
        rsp.setRspCode(SUCCESS_CODE);
        rsp.setRspDesc(respDesc);
        rsp.setTraceId(obtainTraceId());
        String timeStamp = Dates.format(new Date(),"yyyy-MM-dd HH:mm:ss");
        rsp.setTimestamp(timeStamp);
        rsp.setRspBody(rspBody);
        return rsp;
    }


    /**业务异常 **/
    public   BaseRsp fail(String respCode,String respDesc){
        return fail(respCode,respDesc,"");
    }

    /**业务异常**/
    public  <T> BaseRsp<T> fail(String rspCode,String rspDesc,T rspBody){
        if(StringUtils.isEmpty(rspCode)){
            rspCode = ERROR_CODE;
        }
        BaseRsp rsp = new BaseRsp();
        rsp.setRspCode(rspCode);
        rsp.setRspDesc(rspDesc);
        rsp.setTraceId(obtainTraceId());
        String timeStamp = Dates.format(new Date(),"yyyy-MM-dd HH:mm:ss");
        rsp.setTimestamp(timeStamp);
        rsp.setRspBody(rspBody);
        return rsp;
    }


    /**系统异常**/
    public   BaseRsp error(){
        return error(ERROR_CODE,ERROR_DESC);
    }

    /**系统异常**/
    public   BaseRsp error(String errorCode,String errorDesc){
        BaseRsp rsp = new BaseRsp();
        rsp.setRspCode(errorCode);
        rsp.setTraceId(obtainTraceId());
        String timeStamp = Dates.format(new Date(),"yyyy-MM-dd HH:mm:ss");
        rsp.setTimestamp(timeStamp);
        return rsp;
    }

}
