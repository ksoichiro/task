package com.ksoichiro.task.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.junit.Test;
import org.springframework.beans.BeanUtils;

import java.util.Date;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class TeamAccountTests {
    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class TeamAccount2 extends TeamAccount {
    }


    @Test
    @SuppressWarnings("all")
    public void testEquals() {
        TeamAccount entity1 = new TeamAccount(null, null);
        TeamAccount entity2 = new TeamAccount();

        assertThat(entity1.equals(entity2), is(true));
        entity1.hashCode();

        entity2.setId(1);
        assertThat(entity1.equals(entity2), is(false));

        entity1.setId(1);
        assertThat(entity1.equals(entity2), is(true));
        entity1.hashCode();

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

        TeamAccount2 entity3 = new TeamAccount2();
        BeanUtils.copyProperties(entity1, entity3);
        assertThat(entity1.equals(entity3), is(false));
    }
}
