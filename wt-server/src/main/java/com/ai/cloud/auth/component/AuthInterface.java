package com.ai.cloud.auth.component;



import com.ai.cloud.auth.bean.AuthBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public abstract class AuthInterface {

  public abstract boolean isLogin(HttpServletRequest request, HttpServletResponse response);

  public abstract AuthBean getUserInfo(HttpServletRequest request);

  public abstract AuthBean getUserInfoNoCheckVersion(HttpServletRequest request);

  public abstract boolean checkUserAgent(HttpServletRequest request);

}
