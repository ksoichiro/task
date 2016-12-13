package com.ksoichiro.task.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public abstract class DateUtils {
    public static Date today() {
        return truncateTime(new Date());
    }

    public static Date truncateTime(Date source) {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(source);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static String toStringFromDate(Date date, String pattern) {
        LocalDateTime t = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(pattern);
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
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(pattern);
        LocalDateTime t = LocalDateTime.parse(value, dtf);
        return Date.from(t.atZone(ZoneId.systemDefault()).toInstant());
    }
}
