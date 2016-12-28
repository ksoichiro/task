package com.ksoichiro.task.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Post("/update-save")
public @interface UpdateSave {
}
