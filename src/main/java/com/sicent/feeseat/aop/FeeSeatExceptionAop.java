/**
 * 
 */
package com.sicent.feeseat.aop;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 
 * @author wangqiang
 * 
 */
@Component
@Aspect
public class FeeSeatExceptionAop {

    private static final Logger log = LoggerFactory.getLogger(FeeSeatExceptionAop.class);

    @AfterThrowing(throwing = "ex", pointcut = "execution(* com.sicent.feeseat.service.*.*(..))")
    public Object throwExceptions(Throwable ex) {
        log.error("@AfterThrowing Exception：" + ex);
        log.info("@AfterThrowing Do Something Else... Like Emal or SMS...");

        return null;
    }

}
