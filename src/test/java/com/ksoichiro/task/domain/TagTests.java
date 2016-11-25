package com.ksoichiro.task.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.junit.Test;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class TagTests {
    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class Tag2 extends Tag {
    }

    @Test
    @SuppressWarnings("all")
    public void testEquals() {
        Tag entity1 = new Tag(null, null, null, null);
        Tag entity2 = new Tag();

        assertThat(entity1.equals(entity2), is(true));

        entity2.setId(1);
        assertThat(entity1.equals(entity2), is(false));

        entity1.setId(1);
        assertThat(entity1.equals(entity2), is(true));

        Team team = new Team();
        entity2.setTeam(team);
        assertThat(entity1.equals(entity2), is(false));

        entity1.setTeam(team);
        assertThat(entity1.equals(entity2), is(true));

        Account account = new Account();
        entity2.setAccount(account);
        assertThat(entity1.equals(entity2), is(false));

        entity1.setAccount(account);
        assertThat(entity1.equals(entity2), is(true));

        entity2.setName("A");
        assertThat(entity1.equals(entity2), is(false));

        entity1.setName("A");
        assertThat(entity1.equals(entity2), is(true));

        List<Task> taskList = new ArrayList<>();

        entity2.setTasks(taskList);
        assertThat(entity1.equals(entity2), is(false));

        entity1.setTasks(taskList);
        assertThat(entity1.equals(entity2), is(true));

        Date now = new Date();
        entity2.setCreatedAt(now);
        assertThat(entity1.equals(entity2), is(false));

        entity1.setCreatedAt(now);
        assertThat(entity1.equals(entity2), is(true));

        entity2.setUpdatedAt(now);
        assertThat(entity1.equals(entity2), is(false));

        entity1.setUpdatedAt(now);
        assertThat(entity1.equals(entity2), is(true));

        Tag2 entity3 = new Tag2();
        BeanUtils.copyProperties(entity1, entity3);
        assertThat(entity1.equals(entity3), is(false));
    }
}
