/**
 * 
 */
package com.sicent.feeseat.aop;

import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sicent.feeseat.util.FeeSeatContext;

/**
 * 
 * @author wangqiang
 * 
 */
@Component
@Aspect
public class FeeSeatAop {

    private static final Logger log = LoggerFactory.getLogger(FeeSeatAop.class);

    /**
     * 不需要权限控制的method
     */
    private List<String> unLimitMethod;

    @Around("execution(* com.sicent.feeseat.ctrl.*.*(..))")
    public Object authorith(ProceedingJoinPoint pjp) throws Throwable {
        log.info("验证用户权限");

        String ctrl = pjp.getTarget().getClass().getName() + "." + pjp.getSignature().getName();
        log.info("拦截到用户访问" + ctrl);

        if (FeeSeatContext.getRequest().getSession().getAttribute("user") == null) {
            log.info("没有用户登录信息");
            return "{\"success\":false, \"msg\":\"没有权限访问\"}";
        }
        if (unLimitMethod.contains(ctrl)) {
            log.info("没有访问权限");
            return "{\"success\":false, \"msg\":\"没有权限访问\"}";
        }

        return pjp.proceed();
    }

    public void setUnLimitMethod(List<String> unLimitMethod) {
        this.unLimitMethod = unLimitMethod;
    }

}
