package com.ksoichiro.task.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.NestedRuntimeException;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

@Component
@Aspect
@Slf4j
public class ServiceAdvice {
    @Pointcut("@within(org.springframework.stereotype.Service)")
    public void service() {
    }

    @SuppressWarnings({"ThrowableResultOfMethodCallIgnored","IllegalThrows"})
    @Around("service() && execution(* com.ksoichiro.task.service.*.*(..))")
    public Object execution(ProceedingJoinPoint pjp) throws Throwable {
        try {
            return pjp.proceed();
        } catch (NestedRuntimeException e) {
            if (e.contains(SQLException.class)) {
                // If needed, we can handle specific SQL error codes.
                final SQLException cause = getCause(e, SQLException.class);
                log.warn("SQL Error: {}, SQLState: {}, message: {}", cause.getErrorCode(), cause.getSQLState(), cause.getMessage());
            }
            // Rethrow it to delegate handling
            throw e;
        }
    }

    @SuppressWarnings("unchecked")
    <T> T getCause(NestedRuntimeException e, Class<T> exceptionClass) {
        Throwable cause = e.getCause();
        while (cause != null) {
            if (exceptionClass.isAssignableFrom(cause.getClass())) {
                break;
            }
            cause = cause.getCause();
        }
        return (T) cause;
    }
}
