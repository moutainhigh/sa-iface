package com.ai.cloud.auth.interceptor;


import com.ai.cloud.auth.annotation.Auth;
import com.ai.cloud.auth.bean.AuthBean;
import com.ai.cloud.base.exception.AppException;
import com.ai.cloud.bean.base.BaseRsp;
import com.ai.cloud.constant.RspCodeEnum;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public abstract class AuthInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        try {
            Auth auth = getAuthAnnotation(handler);

            if (!checkUserAgent(request)) {
                return true;
            }

            if (!isNeedAuth(auth)) {
                return true;
            }

            if (auth(request, response)) {
                return true;
            }
            AuthBean authBean = getAuth(request);
            if (null == authBean) {
                returnError(null, request, response);
                return false;
            }

            return true;
        } catch (AppException appException) {
            returnError(appException, request, response);
        } catch (Exception e) {
            returnError(null, request, response);
        }

        return false;
    }

    private Auth getAuthAnnotation(Object handler) {
        if (!(handler instanceof HandlerMethod)) {
            return null;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Auth auth = handlerMethod.getMethod().getAnnotation(Auth.class);
        return auth;
    }

    protected abstract boolean checkUserAgent(HttpServletRequest request);

    protected abstract boolean isNeedAuth(Auth auth);

    protected abstract boolean auth(HttpServletRequest request, HttpServletResponse response);

    protected abstract AuthBean getAuth(HttpServletRequest request);

    protected AuthBean getAuthInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            return getAuth(request);

        } catch (AppException appException) {
            returnError(appException, request, response);
            return null;
        }
    }


    protected void returnError(AppException e, HttpServletRequest request,
                               HttpServletResponse response) throws IOException {

        BaseRsp baseRsp = new BaseRsp();

        if (e != null) {
            baseRsp.setRspCode(e.getMessageCode());
            baseRsp.setRspDesc(e.getMessage());
        } else {
            baseRsp.setRspCode(RspCodeEnum.NOLOGIN.toString());
            baseRsp.setRspDesc("未登录");
        }

        returnJsonValue(response, baseRsp);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) throws Exception {

    }

    public static void returnJsonValue(HttpServletResponse response, Object result) {
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.write(JSON.toJSONString(result));
        } catch (Exception e) {
            // do nothing
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        }
    }
}
