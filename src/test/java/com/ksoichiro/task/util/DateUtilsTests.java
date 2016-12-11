package com.ksoichiro.task.util;

import org.junit.Test;

import java.util.Date;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class DateUtilsTests {
    @Test
    public void parse() {
        String expr = "2016-12-11 10:01:30";
        String pattern = "uuuu-MM-dd HH:mm:ss";
        Date d = DateUtils.toDateFromString(expr, pattern);
        assertThat(DateUtils.toStringFromDate(d, pattern), is(expr));
    }
}
