package com.ksoichiro.task.domain;

import com.ksoichiro.task.constant.TaskStatusEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.junit.Test;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class TaskTests {
    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class Task2 extends Task {
    }

    @Test
    @SuppressWarnings("all")
    public void testEquals() {
        Task entity1 = new Task();
        Task entity2 = new Task();

        assertThat(entity1.equals(entity2), is(true));

        entity2.setId(1);
        assertThat(entity1.equals(entity2), is(false));

        entity1.setId(1);
        assertThat(entity1.equals(entity2), is(true));

        entity2.setName("A");
        assertThat(entity1.equals(entity2), is(false));

        entity1.setName("A");
        assertThat(entity1.equals(entity2), is(true));

        entity2.setStatus(TaskStatusEnum.DOING);
        assertThat(entity1.equals(entity2), is(false));

        entity1.setStatus(TaskStatusEnum.DOING);
        assertThat(entity1.equals(entity2), is(true));

        Date now = new Date();
        entity2.setScheduledAt(now);
        assertThat(entity1.equals(entity2), is(false));

        entity1.setScheduledAt(now);
        assertThat(entity1.equals(entity2), is(true));

        Account account = new Account();
        entity2.setAccount(account);
        assertThat(entity1.equals(entity2), is(false));

        entity1.setAccount(account);
        assertThat(entity1.equals(entity2), is(true));

        List<Tag> tagList = new ArrayList<>();
        entity2.setTags(tagList);
        assertThat(entity1.equals(entity2), is(false));

        entity1.setTags(tagList);
        assertThat(entity1.equals(entity2), is(true));

        entity2.setCreatedAt(now);
        assertThat(entity1.equals(entity2), is(false));

        entity1.setCreatedAt(now);
        assertThat(entity1.equals(entity2), is(true));

        entity2.setUpdatedAt(now);
        assertThat(entity1.equals(entity2), is(false));

        entity1.setUpdatedAt(now);
        assertThat(entity1.equals(entity2), is(true));

        Task2 entity3 = new Task2();
        BeanUtils.copyProperties(entity1, entity3);
        assertThat(entity1.equals(entity3), is(false));
    }
}
