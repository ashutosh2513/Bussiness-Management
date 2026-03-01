package com.smartbilling.common;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class AppLoggingAspect {

    @Pointcut("within(com.smartbilling.web..*) || within(com.smartbilling.service..*) || within(com.smartbilling.repository..*)")
    public void appLayers() {}

    @Around("appLayers()")
    public Object logExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        long start = System.currentTimeMillis();

        log.info("Inside function={} of class={}", methodName, className);
        try {
            Object result = joinPoint.proceed();
            long tookMs = System.currentTimeMillis() - start;
            log.info("Outside function={} of class={} durationMs={}", methodName, className, tookMs);
            return result;
        } catch (Throwable ex) {
            long tookMs = System.currentTimeMillis() - start;
            log.error("Error in function={} of class={} durationMs={} message={}", methodName, className, tookMs, ex.getMessage(), ex);
            throw ex;
        }
    }

    @AfterReturning("@annotation(auditableAction)")
    public void auditAction(AuditableAction auditableAction) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String actor = auth != null ? auth.getName() : "anonymous";
        log.info("audit action={} actor={}", auditableAction.value(), actor);
    }
}
