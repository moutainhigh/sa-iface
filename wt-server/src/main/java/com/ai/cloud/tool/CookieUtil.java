package com.ai.cloud.tool;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by wen on 2016/3/30.
 */
@Component
@RefreshScope
public class CookieUtil {

  @Value("${domain.10010.com}")
  private String domain ;

  public String getCookieValue(HttpServletRequest request, String cookieName) {
    Cookie cookie = getCookie(request, cookieName);
    return cookie == null ? null : cookie.getValue();
  }

  public Cookie getCookie(HttpServletRequest request, String cookieName) {
    Cookie[] cookies = request.getCookies();
    if (cookies == null) {
      return null;
    }
    for (Cookie cookie : cookies) {
      if (cookieName.equals(cookie.getName())) {
        return cookie;
      }
    }
    return null;
  }

  public void setCookie(HttpServletResponse response, String cookieName,
      String cookieValue) {
    Cookie cookie = new Cookie(cookieName, cookieValue);
    cookie.setDomain(domain);
    cookie.setPath("/");
    response.addCookie(cookie);
  }

  public void setCookie(HttpServletResponse response, String cookieName,
                        String cookieValue, int maxAge){
    Cookie cookie = new Cookie(cookieName, cookieValue);
    cookie.setDomain(domain);
    cookie.setPath("/");
    cookie.setMaxAge(maxAge);
    response.addCookie(cookie);
  }

  public void delCookie(HttpServletResponse response, String cookieName) {
    Cookie cookie = new Cookie(cookieName, null);
    cookie.setDomain(domain);
    cookie.setPath("/");
    cookie.setMaxAge(0);
    response.addCookie(cookie);
  }

}
