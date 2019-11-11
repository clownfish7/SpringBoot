package com.clownfish7.springbootsomething.aspect;

import com.alibaba.fastjson.JSON;
import com.clownfish7.springbootsomething.annotation.SysLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author yzy
 * @classname SysLogAspect
 * @description 系统日志切面
 * @create 2019-10-14 11:12
 */
@Slf4j
@Aspect
@Component
public class SysLogAspect {

    @Pointcut("@annotation(com.clownfish7.springbootsomething.annotation.SysLog)")
    public void logPointCut() {

    }

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        Object result = point.proceed();
        long time = System.currentTimeMillis() - beginTime;
        saveSysLog(point, time);
        return result;
    }

    private void saveSysLog(ProceedingJoinPoint joinPoint, long time) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        SysLog sysLog = method.getAnnotation(SysLog.class);
        //注解上的描述
        String value = sysLog.value();

        String clazzName = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();

        Object[] args = joinPoint.getArgs();
        String params = JSON.toJSONString(args);

        log.info("value : {} - className : {} - methodName : {} - params : {} - runTime : {} ms", value, clazzName, methodName, params, time);
    }

}
