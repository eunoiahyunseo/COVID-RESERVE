package com.hyunseo.covidreserve.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
/**
 * @author ihyeonseo
 */

@Aspect
@Component
public class ParameterAop {
//    @Pointcut("execution(* com.hyunseo.covidreserve.controller..*.*(..))")
//    private void cut() {}
//
//    // cut() 메소드가 실행 되는 지점 이전에 before() 메서드 실행
//    @Before("cut()")
//    public void before(JoinPoint joinPoint) {
//
//        // 실행되는 함수 이름
//        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
//        Method method = methodSignature.getMethod();
//        System.out.println(method.getName() + "메서드 실행");
//
//        // 메서드에 들어가 있는 매개변수 배열을 읽어옴
//        Object[] args = joinPoint.getArgs();
//
//        for(Object obj : args) {
//            System.out.println("type : " + obj.getClass().getSimpleName());
//            System.out.println("value : " + obj);
//        }
//    }
//
//    @AfterReturning(value = "cut()", returning = "obj")
//    public void afterReturn(JoinPoint joinPoint, Object obj) {
//        System.out.println("return obj");
//        System.out.println(obj);
//    }



}
