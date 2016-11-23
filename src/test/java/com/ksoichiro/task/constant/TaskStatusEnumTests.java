package com.ksoichiro.task.constant;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class TaskStatusEnumTests {
    @Test
    public void valueOf() {
        assertThat(TaskStatusEnum.valueOf("DOING"), is(TaskStatusEnum.DOING));
    }

    @Test(expected = IllegalArgumentException.class)
    public void fromCode() {
        assertThat(TaskStatusEnum.fromCode(2), is(TaskStatusEnum.HOLD));
        TaskStatusEnum.fromCode(10);
    }
}
