package com.ksoichiro.task.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public abstract class DateUtils {
    public static Date today() {
        return truncateTime(new Date());
    }

    public static Date truncateTime(Date source) {
        return Date.from(LocalDateTime.ofInstant(source.toInstant(), ZoneId.systemDefault()).truncatedTo(ChronoUnit.DAYS).atZone(ZoneId.systemDefault()).toInstant());
    }

    public static String toStringFromDate(Date date, String pattern) {
        final LocalDateTime t = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        final DateTimeFormatter dtf = DateTimeFormatter.ofPattern(pattern);
        return dtf.format(t);
    }

    public static Date toDateFromString(String value) {
        if (value != null && value.matches("[0-9]{4}-[0-9]{2}-[0-9]{2}")) {
            return toDateFromString(value + " 00:00:00", "uuuu-MM-dd HH:mm:ss");
        } else {
            return toDateFromString(value, "uuuu-MM-dd HH:mm:ss");
        }
    }

    public static Date toDateFromString(String value, String pattern) {
        final DateTimeFormatter dtf = DateTimeFormatter.ofPattern(pattern);
        final LocalDateTime t = LocalDateTime.parse(value, dtf);
        return Date.from(t.atZone(ZoneId.systemDefault()).toInstant());
    }
}
