package com.ksoichiro.task.annotation;

import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@RequestMapping("/create")
public @interface Create {
}
