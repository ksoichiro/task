package com.ksoichiro.task.web;

import com.ksoichiro.task.App;
import com.ksoichiro.task.domain.Account;
import com.ksoichiro.task.repository.AccountRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@Sql({"/truncate.sql", "/web/data-task.sql"})
@SpringApplicationConfiguration(App.class)
@WebIntegrationTest
@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource(properties = "application.security.enabled: true")
public class AppErrorControllerTests extends AbstractTransactionalJUnit4SpringContextTests {
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private AccountRepository accountRepository;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders
            .webAppContextSetup(webApplicationContext)
            .apply(springSecurity())
            .build();
    }

    @Test
    public void internalServerError() throws Exception {
        Account account = accountRepository.findByUsername("a");
        mockMvc.perform(get("/test/problematic").with(user(account)))
            .andExpect(status().is5xxServerError())
            .andExpect(view().name("error"));
    }

    @Test
    public void notFound() throws Exception {
        Account account = accountRepository.findByUsername("a");
        mockMvc.perform(get("/test/unknown").with(user(account)))
            .andExpect(status().isNotFound());
    }

    @Test
    public void notFoundError() throws Exception {
        Account account = accountRepository.findByUsername("a");
        TestingAuthenticationToken testingAuthenticationToken = new TestingAuthenticationToken(account, null);
        SecurityContextHolder.getContext().setAuthentication(testingAuthenticationToken);
        mockMvc.perform(get("/error")
            .requestAttr("javax.servlet.error.status_code", 404)
            .requestAttr("javax.servlet.error.request_uri", "/foo/bar"))
            .andExpect(view().name("error"));
    }
}
