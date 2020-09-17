package com.ai.cloud.auth.interceptor;

import com.ai.cloud.auth.annotation.Auth;
import com.ai.cloud.auth.bean.AuthBean;
import com.ai.cloud.auth.component.impl.MsgoAuth;
import com.ai.cloud.base.exception.AppException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class MsgoAuthInterceptor extends AuthInterceptor {

    @Resource
    MsgoAuth msgoAuth;

    @Override
    protected boolean isNeedAuth(Auth auth) {
        return null != auth;
    }

    @Override
    protected boolean auth(HttpServletRequest request, HttpServletResponse response) {
        return msgoAuth.isLogin(request, response);
    }

    @Override
    protected AuthBean getAuth(HttpServletRequest request) throws AppException{
        return msgoAuth.getUserInfo(request);
    }

    @Override
    protected boolean checkUserAgent(HttpServletRequest request) {
        return msgoAuth.checkUserAgent(request);
    }
}
