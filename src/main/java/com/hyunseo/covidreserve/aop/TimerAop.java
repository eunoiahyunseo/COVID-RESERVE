package com.hyunseo.covidreserve.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

/**
 * @author ihyeonseo
 */
@Aspect
@Component
public class TimerAop {
    @Pointcut("execution(* com.hyunseo.covidreserve.controller..*.*(..))")
    private void cut() {}

    @Pointcut("@annotation(com.hyunseo.covidreserve.aop.annotation.Timer)")
    private void enableTimer() {}

    @Around("cut() && enableTimer()")
    public void around(ProceedingJoinPoint joinPoint) throws Throwable {

        // 메서드 시작 전
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        // 메서드가 실행되는 시점
        Object result = joinPoint.proceed();

        // 메서드 종료 후
        stopWatch.stop();

        System.out.println("총 걸린 시간: " + stopWatch.getTotalTimeSeconds());
    }
}
