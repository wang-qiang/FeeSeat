/**
 * 
 */
package com.sicent.feeseat.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * 
 * @author wangqiang
 * 
 */
@Component
@Aspect
public class SqlServerAop {

    @Before("execution(* com.sicent.feeseat.SqlServerManager.*(..))")
    public void authorith(JoinPoint controller) {
        System.out.println("@Before...");
        Object[] args = controller.getArgs();
        for (Object obj : args) {
            System.err.println("arg=" + obj.toString());
        }
    }

    @AfterThrowing(throwing = "ex", pointcut = "execution(* com.sicent.feeseat.SqlServerManager.*(..))")
    public void doRecoverActions(Throwable ex) {
        System.out.println("@AfterThrowing Exception：" + ex);
        System.out.println("@AfterThrowing Do Something Else...");
    }

}
