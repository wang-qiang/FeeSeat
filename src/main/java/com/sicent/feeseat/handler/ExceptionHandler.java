/**
 * 
 */
package com.sicent.feeseat.handler;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/**
 * 异常处理类
 * 
 * @author wangqiang
 * 
 */
public class ExceptionHandler implements HandlerExceptionResolver {

    private static final Logger log = LoggerFactory.getLogger(ExceptionHandler.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response,
            Object handler, Exception ex) {

        // 把异常信息全部记入日志
        log.error("Catch Exception: ", ex);

        Map<String, Object> map = new HashMap<String, Object>();

        StringPrintWriter strintPrintWriter = new StringPrintWriter();

        ex.printStackTrace(strintPrintWriter);

        map.put("errorMsg", strintPrintWriter.getString());

        strintPrintWriter.close();

        // 异常分别处理，该发邮件的发邮件，该短信的短信
        if (ex instanceof RuntimeException) {
            return new ModelAndView("err/exception", map);
        } else if (ex instanceof NumberFormatException) {
            return new ModelAndView("err/exception", map);
        }

        return new ModelAndView("err/exception", map);
    }

}
