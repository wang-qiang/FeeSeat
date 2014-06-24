/**
 * 
 */
package com.sicent.feeseat.aop;

import org.aspectj.lang.JoinPoint;

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
public class MyAop {

    @Before("execution(* com.sicent.feeseat.controller.*.*(..))")
    public void authorith(JoinPoint controller) {
        System.out.println("@Before...");
        Object[] args = controller.getArgs();
        for (Object obj : args) {
            System.err.println("=========" + obj.toString());
        }
    }

    /*
     * @Before("execution(* com.sicent.demo.controller.*.*(..))") public void authorith(JoinPoint
     * controller) { System.out.println("@Before..."); Object[] args = controller.getArgs(); for
     * (Object obj : args) { System.err.println("=========" + obj.toString()); } }
     * 
     * @AfterReturning(returning = "rvt", pointcut =
     * "execution(* com.sicent.demo.controller.*.*(..))") public void log(Object rvt) {
     * System.out.println("@AfterReturning 目标方法返回值：" + rvt); System.out.println("Log Records..."); }
     * 
     * @AfterThrowing(throwing = "ex", pointcut = "execution(* com.sicent.demo.controller.*.*(..))")
     * public void doRecoverActions(Throwable ex) { System.out.println("@AfterThrowing Exception：" +
     * ex); System.out.println("@AfterThrowing Do Something Else..."); }
     * 
     * @After("execution(* com.sicent.demo.controller.*.*(..))") public void release() {
     * System.out.println("@After Release Resources..."); }
     */

    /*
     * @Around("execution(* com.sicent.demo.controller.*.*(..))") public Object
     * processTx(ProceedingJoinPoint jp) throws java.lang.Throwable {
     * System.out.println("@Around  执行目标方法前..."); // 执行目标方法，并保存目标方法执行后的返回值 User user = (User)
     * jp.proceed(new String[] { "被改变的参数" }); // throw new Exception("errrrrr///", new
     * Throwable("ex")); // System.out.println("@Around 执行目标方法后，模拟结束事务..."); //
     * user.setName(user.getName()+"新增的内容"); System.out.println("@Around  执行目标方法后..."); return user;
     * }
     */
}
