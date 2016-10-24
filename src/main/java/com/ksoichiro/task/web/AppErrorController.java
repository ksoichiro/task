package com.ksoichiro.task.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Locale;
import java.util.Map;

@Controller
@Slf4j
public class AppErrorController implements ErrorController {
    @Autowired
    private ErrorAttributes errorAttributes;

    @Autowired
    private MessageSource messageSource;

    @Override
    public String getErrorPath() {
        return "/error";
    }

    @RequestMapping("/error")
    public String error(Model model) {
        Map<String, Object> attrs = errorAttributes.getErrorAttributes(RequestContextHolder.currentRequestAttributes(), true);
        model.addAttribute("error", attrs);
        // Try to add an additional guidance message for specific status
        try {
            model.addAttribute("guidance", messageSource.getMessage("http.error." + attrs.get("status"), null, Locale.getDefault()));
        } catch (NoSuchMessageException ignore) {
        }

        return "error";
    }
}
