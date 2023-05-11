package com.cy.store.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;


/**
 * 统计业务方法耗时-AOP
 * @author 魏敏捷
 * @version 1.0
 */
@Aspect //将当前类标记为切面类
@Component //将当前类的对象创建使用维护交由Spring容器维护
public class TimerAspect {

    @Around("execution(* com.cy.store.service.impl.*.*(..))")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {//参数ProceedingJoinPoint接口表示连接点,也就是目标方法的对象
        //开始时间
        long start = System.currentTimeMillis();
        //调用目标方法,比如login方法,getByUid方法
        Object result = pjp.proceed();
        //结束时间
        long end = System.currentTimeMillis();
        System.out.println(pjp.getTarget().getClass().getName() + "类的" + pjp.getSignature().getName() +"方法..." + "耗时:" +(end-start));
        return result;
    }
}

