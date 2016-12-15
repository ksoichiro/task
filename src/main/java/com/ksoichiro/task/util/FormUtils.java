package com.ksoichiro.task.util;

import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class FormUtils {
    public static void copyDate(Supplier<String> supplier, Consumer<Date> consumer) {
        if (!StringUtils.isEmpty(supplier.get())) {
            consumer.accept(DateUtils.toDateFromString(supplier.get()));
        }
    }
}
