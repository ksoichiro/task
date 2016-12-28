package com.ksoichiro.task.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Post("/create-save")
public @interface CreateSave {
}
