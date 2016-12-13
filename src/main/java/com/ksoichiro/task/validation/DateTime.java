package com.ksoichiro.task.validation;

import javax.validation.constraints.Pattern;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
@Pattern(regexp = "[0-9]{4}-[0-9]{2}-[0-9]{2} ([0-9]{2}:[0-9]{2}:[0-9]{2})?")
public @interface DateTime {
}
