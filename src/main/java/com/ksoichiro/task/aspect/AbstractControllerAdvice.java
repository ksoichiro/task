package com.ksoichiro.task.aspect;

import org.aspectj.lang.annotation.Pointcut;

public abstract class AbstractControllerAdvice {
    @Pointcut("@within(org.springframework.stereotype.Controller)")
    public void controller() {
    }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void requestMapping() {
    }

    @Pointcut("execution(* com.ksoichiro.task..*.*(..))")
    public void anyProjectMethodExecution() {
    }
}
