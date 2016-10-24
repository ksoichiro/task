package com.ksoichiro.task.web;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@ControllerAdvice
@Controller
@Aspect
@Slf4j
public class AppErrorController implements ErrorController {
    @Autowired
    private ErrorAttributes errorAttributes;

    @Autowired
    private MessageSource messageSource;

    @Pointcut("@within(org.springframework.stereotype.Controller)")
    public void controller() {
    }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void requestMapping() {
    }

    @Pointcut("execution(* com.ksoichiro.task.web..*.*(..))")
    public void anyProjectMethodExecution() {
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }

    @Around("controller() && requestMapping() && anyProjectMethodExecution()")
    public Object controllerExecution(ProceedingJoinPoint pjp) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        Object result = pjp.proceed();
        log.info("Processed request {}", request.getRequestURI());
        return result;
    }

    // This won't be called
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public String handleNotFoundError(Throwable throwable, Model model) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        log.error("Not found {}", request.getRequestURI());
        Map<String, Object> attrs = new HashMap<>();
        attrs.put("status", 404);
        attrs.put("error", "Not Found");
        model.addAttribute("error", attrs);
        return "error";
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Throwable.class)
    public String handleException(Throwable throwable, Model model) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        log.error("Unhandled exception in {}: {}", request.getRequestURI(), throwable.toString(), throwable);
        Map<String, Object> attrs = new HashMap<>();
        attrs.put("status", 500);
        attrs.put("error", "Internal Server Error");
        model.addAttribute("error", attrs);
        model.addAttribute("guidance", messageSource.getMessage("http.error.500", null, Locale.getDefault()));
        return "error";
    }

    @RequestMapping("/error")
    public String error(Model model) {
        Map<String, Object> attrs = errorAttributes.getErrorAttributes(RequestContextHolder.currentRequestAttributes(), true);
        model.addAttribute("error", attrs);
        // Try to add an additional guidance message for specific status
        try {
            Integer status = (Integer) attrs.get("status");
            if (status == 404) {
                // TODO With this implementation, the original requested URI is now shown.
                HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
                log.info("Unknown URI (404): {}", request.getRequestURI());
            }
            model.addAttribute("guidance", messageSource.getMessage("http.error." + status, null, Locale.getDefault()));
        } catch (NoSuchMessageException ignore) {
        }

        return "error";
    }
}
