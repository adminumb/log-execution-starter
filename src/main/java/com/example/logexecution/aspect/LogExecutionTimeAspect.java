package com.example.logexecution.aspect;

import com.example.logexecution.annotation.LogExecutionTime;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
@Slf4j
@Aspect
@Component
public class LogExecutionTimeAspect {

    @Around("@annotation(com.example.logexecution.annotation.LogExecutionTime)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        ZonedDateTime startTime =  ZonedDateTime.now(ZoneId.of("UTC"));
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.getDeclaringType().getSimpleName() + "." + signature.getName();
        LogExecutionTime annotation = signature.getMethod().getAnnotation(LogExecutionTime.class);
        String customMessage = annotation.value();

        try {
            Object result = joinPoint.proceed();
            ZonedDateTime endTime = ZonedDateTime.now(ZoneId.of("UTC"));
            long executionTime = Duration.between(startTime, endTime).toMillis();

            if (!customMessage.isEmpty()) {
                log.info("{} - Execution time: {} ms", customMessage, executionTime);
            } else {
                log.info("Method {} executed in {} ms", methodName, executionTime);
            }
            return result;

        } catch (Exception exception) {
            log.error("Exception in method {}: {}", methodName, exception.getMessage(), exception);
            throw exception;
        }
    }
}

