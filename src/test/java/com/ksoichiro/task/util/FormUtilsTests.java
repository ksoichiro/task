package com.ksoichiro.task.util;

import com.ksoichiro.task.domain.Task;
import com.ksoichiro.task.form.TaskCreateForm;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class FormUtilsTests {
    @Test
    public void copyDate() {
        TaskCreateForm form = new TaskCreateForm();
        form.setScheduledAt("2016-12-15 01:02:03");
        Task task = new Task();
        FormUtils.copyDate(form::getScheduledAt, task::setScheduledAt);
        assertThat(DateUtils.toStringFromDate(task.getScheduledAt(), "uuuu-MM-dd HH:mm:ss"), is("2016-12-15 01:02:03"));
    }
}
