package com.ai.cloud.auth.component.impl;

import com.ai.cloud.auth.bean.AuthBean;
import com.ai.cloud.auth.component.AuthInterface;
import com.ai.cloud.base.exception.AppException;
import com.ai.cloud.cache.VersionCache;
import com.ai.cloud.constant.RspCodeEnum;
import com.ai.cloud.service.auth.AuthService;
import com.ai.cloud.tool.CookieUtil;
import com.google.common.base.Splitter;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Component
public class MsgoAuth extends AuthInterface {

    private static final Logger logger = LoggerFactory.getLogger(MsgoAuth.class);

    public static final String COOKIE_NAME = "_msgo_";
    public static final String USER_AGENT_MASHGO = "Mashgo";
    public static final String REQ_HEADER_USER_AGENT = "User-Agent";

    @Autowired
    private CookieUtil cookieUtil;

    @Resource
    private AuthService authService;

    @Autowired
    VersionCache versionCache;


    @Override
    public boolean isLogin(HttpServletRequest request, HttpServletResponse response) {

        String token = getUserString(request);
        logger.info("登录拦截模块_登录校验_cookie[_msgo_]对应的token值为:{}", token);

        if (StringUtils.isEmpty(token)) {
            return false;
        }

        return true;
    }

    @Override
    public AuthBean getUserInfo(HttpServletRequest request) throws AppException {

        if (!checkUserAgent(request)) {
            throw new AppException(RspCodeEnum.LOGIN_INVALID.toString(), "请在码上购助手中访问!");
        }

        String token = getUserString(request);
        logger.info("登录拦截模块_登录校验_cookie[_msgo_]对应的token值为:{}", token);

        if (StringUtils.isEmpty(token)) {
            throw new AppException(RspCodeEnum.NOLOGIN.toString(), "未登录!");
        }

        AuthBean authBean = authService.getAuthByToken(token);
        if (null == authBean) {
            throw new AppException(RspCodeEnum.LOGIN_INVALID.toString(), "此账号已在其它设备登录!");
        }
        String userAgent = request.getHeader(REQ_HEADER_USER_AGENT);
        String osAndVersion = userAgent.substring(userAgent.indexOf(USER_AGENT_MASHGO) + 7);
        List<String> list = Splitter.on("/").trimResults().splitToList(osAndVersion);
        authBean.setOs(list.get(0));
        authBean.setVersion(list.get(1));
        logger.info("登录拦截模块_登录校验_cookie[_msgo_]对应的token值为:{}, 用户信息为:{}", token, authBean);

        if (!checkSupportVersion(authBean)) {
            throw new AppException(RspCodeEnum.VERSION_INVALID.toString(), "app版本过低，请升级app!");
        }

        return authBean;
    }

    @Override
    public AuthBean getUserInfoNoCheckVersion(HttpServletRequest request) throws AppException {

        if (!checkUserAgent(request)) {
            throw new AppException(RspCodeEnum.LOGIN_INVALID.toString(), "请在码上购助手中访问!");
        }

        String token = getUserString(request);
        logger.info("登录拦截模块_登录校验_cookie[_msgo_]对应的token值为:{}", token);

        if (StringUtils.isEmpty(token)) {
            throw new AppException(RspCodeEnum.NOLOGIN.toString(), "未登录!");
        }

        AuthBean authBean = authService.getAuthByToken(token);
        logger.info("登录拦截模块_登录校验_cookie[_msgo_]对应的token值为:{}, 用户信息为:{}", token, authBean);

        if (null == authBean) {
            throw new AppException(RspCodeEnum.LOGIN_INVALID.toString(), "此账号已在其它设备登录!");
        }

        return authBean;
    }

    @Override
    public boolean checkUserAgent(HttpServletRequest request) {
        String userAgent = request.getHeader(REQ_HEADER_USER_AGENT);
        logger.info("登录拦截模块_登录校验_User-Agent值为：{}", userAgent);
        if (StringUtils.isBlank(userAgent) || !StringUtils.contains(userAgent, USER_AGENT_MASHGO)) {
            return false;
        }
        return true;
    }

    /**
     * 读取cookie _msgo_
     *
     * @return
     */
    private String getUserString(HttpServletRequest request) {

        return cookieUtil.getCookieValue(request, COOKIE_NAME);

    }

    private boolean checkSupportVersion(AuthBean authBean) {
        String targetVersion = authBean.getVersion();
        String provinceCode = authBean.getProvinceCode();
        String eparchyCode = authBean.getEparchyCode();
        targetVersion = authBean.isIOS() ? targetVersion.substring(0, targetVersion.indexOf("(")) :
                targetVersion.contains("test") ? targetVersion.substring(0, targetVersion.lastIndexOf(".")) : targetVersion;
        Map supportMap = versionCache.getSupportVersion(authBean.isIOS() ? "i" : "a", provinceCode, eparchyCode);
        String supportVersion = MapUtils.getString(supportMap, "supportTo");

        logger.info("客户端当前版本号:{}, 最低支持版本号:{}", targetVersion, supportVersion);
        String[] target = targetVersion.split("\\.");
        String[] support = supportVersion.split("\\.");
        for (int i = 0; i < target.length; i++) {
            int targetInt = Integer.parseInt(target[i]);
            int supportInt = Integer.parseInt(support[i]);
            if (targetInt > supportInt) {
                logger.info("客户端版本号{}高于最低支持版本号{}", targetVersion, supportVersion);
                return true;
            }
            if (targetInt < supportInt) {
                logger.info("客户端版本号{}低于最低支持版本号{}", targetVersion, supportVersion);
                return false;
            }
        }
        return true;
    }

}
