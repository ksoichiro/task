package com.ksoichiro.task.dto;

import com.ksoichiro.task.constant.TaskStatusEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.junit.Test;

import java.util.Date;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

public class TaskDTOTests {
    @Data
    @EqualsAndHashCode(callSuper = true)
    static class TaskDTO2 extends TaskDTO {
    }

    @Test
    @SuppressWarnings("all")
    public void testEquals() {
        TaskDTO dto = new TaskDTO();
        assertThat(dto.equals(dto), is(true));
        assertThat(dto.equals(null), is(false));

        assertThat(dto.equals("test"), is(false));
        dto.hashCode();

        TaskDTO dto2 = new TaskDTO();
        assertThat(dto.equals(dto2), is(true));
        dto.hashCode();

        dto2.setName("b");
        assertThat(dto.equals(dto2), is(false));

        dto.setName("a");
        assertThat(dto.equals(dto2), is(false));
        dto.hashCode();

        dto2.setName("a");
        dto2.setStatus(TaskStatusEnum.DOING);
        assertThat(dto.equals(dto2), is(false));
        dto.hashCode();

        dto.setStatus(TaskStatusEnum.NOT_YET);
        assertThat(dto.equals(dto2), is(false));
        dto.hashCode();

        dto2.setStatus(TaskStatusEnum.NOT_YET);
        Date date = new Date();
        dto2.setScheduledAt(date);
        assertThat(dto.equals(dto2), is(false));
        dto.hashCode();

        dto.setScheduledAt(date);
        assertThat(dto.equals(dto2), is(true));
        dto.hashCode();

        TaskDTO2 dto3 = new TaskDTO2();
        dto3.setName("a");
        dto3.setStatus(TaskStatusEnum.NOT_YET);
        dto3.setScheduledAt(date);
        assertThat(dto.equals(dto3), is(false));
    }

    @Test
    @SuppressWarnings("all")
    public void testToString() {
        TaskDTO dto = new TaskDTO();
        assertThat(dto.toString(), is(notNullValue()));
    }

    @Test
    @SuppressWarnings("all")
    public void testHashCode() {
        TaskDTO dto = new TaskDTO();
        dto.hashCode();
    }
}
