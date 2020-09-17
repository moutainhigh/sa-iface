package com.ai.cloud.tool;

import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Some simple time savers. Note that most are static methods.
 * <p>
 * Taken from Core Servlets and JavaServer Pages
 * from Prentice Hall and Sun Microsystems Press,
 * http://www.coreservlets.com/.
 * &copy; 2000 Marty Hall; may be freely used or adapted.
 */

public class ServletUtils {

    /**
     * 获取浏览器端IP.
     * 参考：http://xuechenyoyo.iteye.com/blog/586007。
     * x-cluster-client-ip/x-forwarded-for/WL-Proxy-Client-IP/Proxy-Client-IP
     *
     * @param request
     * @return
     */
    public static String getRemoteAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("x-real-ip");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return StringUtils.substringBefore(ip, ",");
    }

}
