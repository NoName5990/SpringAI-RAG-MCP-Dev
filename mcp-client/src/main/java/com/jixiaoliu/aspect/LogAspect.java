package com.jixiaoliu.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @ClassName LogAspect
 * @Author liujxiao
 * @Version 1.0
 * @Description 日志切面类
 * @date 2026/4/21 上午11:57
 */
@Slf4j
@Aspect
@Component
public class LogAspect {

    private static final long WARN_THRESHOLD = 3000;
    private static final long ERROR_THRESHOLD = 6000;

    /**
     * @Description: 大模型调用AOP切面
     * @Date 2026/4/21 下午12:19
     * @Author liujxiao
     * @param joinPoint
     * @return java.lang.Object
     */
    @Around("execution(* com.jixiaoliu.service.impl..*.*(..))")
    public Object recordTimeLog(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        try {
            Object result = joinPoint.proceed();
            return result;
        } finally {
            // 即使异常也能记录
            long elapsedTime = System.currentTimeMillis() - startTime;
            String pointName = joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName();
            if (elapsedTime >= ERROR_THRESHOLD) {
                log.error("{} 耗时 {} ms", pointName, elapsedTime);
            } else if (elapsedTime >= WARN_THRESHOLD) {
                log.warn("{} 耗时 {} ms", pointName, elapsedTime);
            } else {
                log.info("{} 耗时 {} ms", pointName, elapsedTime);
            }
        }
    }

}
