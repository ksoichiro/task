package com.ksoichiro.task.web;

import com.ksoichiro.task.aspect.AbstractControllerAdvice;
import com.ksoichiro.task.domain.Account;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@ControllerAdvice
@Controller
@Aspect
@Slf4j
public class AppErrorController extends AbstractControllerAdvice implements ErrorController {
    @Autowired
    private ErrorAttributes errorAttributes;

    @Autowired
    private MessageSource messageSource;

    @Override
    public String getErrorPath() {
        return "/error";
    }

    @Around("controller() && requestMapping() && anyProjectMethodExecution()")
    public Object controllerExecution(ProceedingJoinPoint pjp) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        Object result = pjp.proceed();
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
        log.info("{} {} {}", response.getStatus(), request.getRequestURI(), getUsername());
        return result;
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Throwable.class)
    public String handleException(Throwable throwable, Model model) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        log.error("500 {} {} {}", request.getRequestURI(), throwable.toString(), getUsername(), throwable);
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
                log.info("404 {} {}", attrs.get("path"), getUsername());
            }
            model.addAttribute("guidance", messageSource.getMessage("http.error." + status, null, Locale.getDefault()));
        } catch (NoSuchMessageException ignore) {
        }

        return "error";
    }

    private String getUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return "";
        }
        Object principal = authentication.getPrincipal();
        if (principal == null) {
            return "";
        }
        if (principal instanceof Account) {
            return ((Account) principal).getUsername();
        }
        return principal.toString();
    }
}
