package com.ksoichiro.task.service;

import com.ksoichiro.task.App;
import com.ksoichiro.task.domain.Account;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

@Sql("/schema-h2.sql")
@Sql({"/truncate.sql", "/data-login.sql"})
@SpringBootTest(classes = App.class)
public class LoginServiceTests extends AbstractTransactionalJUnit4SpringContextTests {
    @Autowired
    private LoginService loginService;

    @Test
    public void loadUserByUsername() {
        UserDetails userDetails = loginService.loadUserByUsername("a");
        assertThat(userDetails, is(notNullValue()));
        Account account = (Account) userDetails;
        assertThat(account.getName(), is("A"));
        assertThat(account.getId(), is(1));
    }

    @Test(expected = UsernameNotFoundException.class)
    public void loadUserByUsernameWithEmptyName() {
        loginService.loadUserByUsername("");
    }

    @Test(expected = UsernameNotFoundException.class)
    public void loadUserByUsernameUserNotFound() {
        loginService.loadUserByUsername("c");
    }
}
