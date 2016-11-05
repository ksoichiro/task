package com.ksoichiro.task.aspect;

import org.aspectj.lang.annotation.Pointcut;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;

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

    @ModelAttribute
    public void setCookieValues(Model model, @CookieValue(value = "sidebar", defaultValue = "true") String sidebar) {
        model.addAttribute("sidebar", Boolean.valueOf(sidebar));
    }
}
