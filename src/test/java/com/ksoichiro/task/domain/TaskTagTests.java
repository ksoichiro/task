package com.ksoichiro.task.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.junit.Test;
import org.springframework.beans.BeanUtils;

import java.util.Date;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class TaskTagTests {
    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class TaskTag2 extends TaskTag {
    }

    @Test
    @SuppressWarnings("all")
    public void testEquals() {
        TaskTag entity1 = new TaskTag(null, null);
        TaskTag entity2 = new TaskTag();

        assertThat(entity1.equals(entity2), is(true));
        entity1.hashCode();

        entity2.setId(1);
        assertThat(entity1.equals(entity2), is(false));

        entity1.setId(1);
        assertThat(entity1.equals(entity2), is(true));
        entity1.hashCode();

        Task task = new Task();
        entity2.setTask(task);
        assertThat(entity1.equals(entity2), is(false));

        entity1.setTask(task);
        assertThat(entity1.equals(entity2), is(true));
        entity1.hashCode();

        Tag tag = new Tag();
        entity2.setTag(tag);
        assertThat(entity1.equals(entity2), is(false));

        entity1.setTag(tag);
        assertThat(entity1.equals(entity2), is(true));
        entity1.hashCode();

        Date now = new Date();
        entity2.setCreatedAt(now);
        assertThat(entity1.equals(entity2), is(false));

        entity1.setCreatedAt(now);
        assertThat(entity1.equals(entity2), is(true));
        entity1.hashCode();

        entity2.setUpdatedAt(now);
        assertThat(entity1.equals(entity2), is(false));

        entity1.setUpdatedAt(now);
        assertThat(entity1.equals(entity2), is(true));
        entity1.hashCode();

        TaskTag2 entity3 = new TaskTag2();
        BeanUtils.copyProperties(entity1, entity3);
        assertThat(entity1.equals(entity3), is(false));
    }
}
