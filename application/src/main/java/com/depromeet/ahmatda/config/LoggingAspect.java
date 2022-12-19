package com.depromeet.ahmatda.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class LoggingAspect {

    public static final String REQUEST = "REQUEST_Method::";
    public static final String REQUEST_PARAM = "REQUEST_PARAMETER::";
    public static final String WHITE_SPACE = "  ";

    @Pointcut("execution (* com.depromeet.ahmatda..*.*Controller.*(..))")
    private void controllerMethodPointCut() {
    }

    @Before("controllerMethodPointCut()")
    public void loggingControllerRequest(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        log.info(REQUEST + signature);
        Object[] args = joinPoint.getArgs();

        for (Object arg : args) {
            log.info(REQUEST_PARAM + arg.getClass().getSimpleName() + WHITE_SPACE + arg);
        }
    }
}
