package com.ai.cloud.base.filter;

import com.ai.cloud.base.exception.AppException;
import com.ai.cloud.base.lang.Rmap;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;


/**
 * @author wave
 * @create 2018-03-29 12:29
 **/
@Component
public class ErrorHandlerResolver implements HandlerExceptionResolver {

    private static Logger logger = LoggerFactory.getLogger(ErrorHandlerResolver.class);

    private static final short AJAX_ERROR_HTTP_CODE = 200;
    private static final String DEFAULT_MSG = "系统繁忙，请稍候再试。";

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
                                         Exception e) {
        logger.error("异常统一处理", e);
        return handleAjaxError(response, e);

    }

    private ModelAndView handleAjaxError(HttpServletResponse response, Exception e) {
        String rspMsg = DEFAULT_MSG;
        try {
            boolean handled = false;
            String rspCode = "9999";
            Throwable throwEx = e;
            while (throwEx != null) {
                handled = true;

                if (throwEx instanceof AppException) {
                    rspMsg = throwEx.getMessage();
                    rspCode = ((AppException) throwEx).getMessageCode();
                }
                throwEx = throwEx.getCause();
            }
            if (!handled) {
                logExceptionMsg(e);
            }
            Map map = Rmap.asMap("rspCode", rspCode, "rspDesc", rspMsg);
            logger.error("通用拦截异常返回:{}", JSON.toJSONString(map));
            return outputContext(response, AJAX_ERROR_HTTP_CODE, JSON.toJSONString(map));
        } catch (Exception ee) {
            logExceptionMsg(ee);
        }

        return null;
    }


    private ModelAndView outputContext(HttpServletResponse response, int statusCode, String message) throws IOException {
        ModelAndView empty = new ModelAndView();

        response.setStatus(statusCode);
        response.setHeader("Content-Type", "text/plain; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();
        writer.write(message);
        writer.flush();
        writer.close();
        empty.clear();
        return empty;
    }


    private void logExceptionMsg(Throwable t) {
        Throwable cause = getRootCause(t);
        logger.error(cause.getMessage(), cause);
    }

    private Throwable getRootCause(Throwable t) {
        Throwable cause = t;
        while (cause.getCause() != null) {
            cause = cause.getCause();
        }
        return cause;
    }

}
