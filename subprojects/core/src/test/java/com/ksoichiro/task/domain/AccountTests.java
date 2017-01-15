package com.ksoichiro.task.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class AccountTests {
    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class Account2 extends Account {
    }

    @Test
    @SuppressWarnings("all")
    public void testEquals() {
        Account entity1 = new Account();
        Account entity2 = new Account();

        assertThat(entity1.equals(entity2), is(true));

        entity2.setId(1);
        assertThat(entity1.equals(entity2), is(false));

        entity1.setId(1);
        assertThat(entity1.equals(entity2), is(true));

        entity2.setUsername("a");
        assertThat(entity1.equals(entity2), is(false));

        entity1.setUsername("a");
        assertThat(entity1.equals(entity2), is(true));

        entity2.setName("A");
        assertThat(entity1.equals(entity2), is(false));

        entity1.setName("A");
        assertThat(entity1.equals(entity2), is(true));

        entity2.setPassword("password");
        assertThat(entity1.equals(entity2), is(false));

        entity1.setPassword("password");
        assertThat(entity1.equals(entity2), is(true));

        entity2.setEnabled(true);
        assertThat(entity1.equals(entity2), is(false));

        entity1.setEnabled(true);
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

        Role role = new Role();
        entity2.setRole(role);
        assertThat(entity1.equals(entity2), is(false));

        entity1.setRole(role);
        assertThat(entity1.equals(entity2), is(true));

        List<TeamAccount> teamAccountList = new ArrayList<>();
        entity2.setTeamAccounts(teamAccountList);
        assertThat(entity1.equals(entity2), is(false));

        entity1.setTeamAccounts(teamAccountList);
        assertThat(entity1.equals(entity2), is(true));

        Account2 entity3 = new Account2();
        entity3.setId(1);
        entity3.setUsername("a");
        entity3.setName("A");
        entity3.setEnabled(true);
        entity3.setCreatedAt(now);
        entity3.setUpdatedAt(now);
        entity3.setRole(role);
        entity3.setTeamAccounts(teamAccountList);
        assertThat(entity1.equals(entity3), is(false));
    }
}
