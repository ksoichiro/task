package com.ksoichiro.task.service;

import com.ksoichiro.task.App;
import com.ksoichiro.task.domain.Account;
import com.ksoichiro.task.repository.AccountRepository;
import com.ksoichiro.task.repository.RoleRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@Sql("/schema-h2.sql")
@Sql({"/truncate.sql", "/data-account.sql"})
@SpringBootTest(classes = App.class)
public class AccountServiceTests extends AbstractTransactionalJUnit4SpringContextTests {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AccountService accountService;

    @Test
    public void findById() {
        Account account = accountService.findById(1);
        assertThat(account, is(notNullValue()));
        assertThat(account.getId(), is(1));
    }

    @Test
    public void create() {
        Account account = new Account();
        account.setUsername("foo");
        account.setName("Foo Bar");
        account.setEnabled(true);
        account.setPassword("PASSWORD");
        account.setRole(roleRepository.findOne(1));
        Account created = accountService.create(account);
        assertThat(created, is(notNullValue()));
        assertThat(created.getId(), is(2));
        assertThat(created.getCreatedAt(), is(notNullValue()));
        assertThat(created.getUpdatedAt(), is(notNullValue()));
    }

    @Test
    public void update() {
        Account account = accountRepository.findOne(1);
        account.setUsername("b");
        account.setPassword("foobar");
        Account updated = accountService.update(account);
        assertThat(updated, is(notNullValue()));
        assertThat(updated.getId(), is(1));
        assertThat(updated.getUsername(), is("b"));
        assertThat(updated.getName(), is("A"));
        assertThat(updated.getPassword(), is("foobar"));
        assertThat(updated.getCreatedAt(), is(notNullValue()));
        assertThat(updated.getUpdatedAt(), is(greaterThan(updated.getCreatedAt())));
    }
}
