package com.ksoichiro.task.web;

import com.ksoichiro.task.App;
import com.ksoichiro.task.domain.Account;
import com.ksoichiro.task.domain.Task;
import com.ksoichiro.task.repository.AccountRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Sql({"/truncate.sql", "/web/data-task.sql"})
@SpringApplicationConfiguration(App.class)
@WebAppConfiguration
@TestPropertySource(properties = "application.security.enabled: true")
public class TaskControllerTests extends AbstractTransactionalJUnit4SpringContextTests {
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
    @SuppressWarnings("unchecked")
    public void all() throws Exception {
        Account account = accountRepository.findByUsername("a");
        MvcResult mvcResult = mockMvc.perform(get("/task/all").with(user(account)))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("tasks"))
            .andExpect(view().name("task/all"))
            .andReturn();
        ModelMap modelMap = mvcResult.getModelAndView().getModelMap();
        Object listObj = modelMap.get("tasks");
        assertThat(listObj, is(notNullValue()));
        assertThat(listObj, is(instanceOf(Page.class)));
        Page<Task> page = (Page<Task>) listObj;
        assertThat(page.getTotalElements(), is(4L));
    }

    @Test
    public void countTaskAll() throws Exception {
        Account account = accountRepository.findByUsername("a");
        mockMvc.perform(get("/task/all/count").with(user(account)))
            .andExpect(status().isOk())
            .andExpect(content().string("4"));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void today() throws Exception {
        Account account = accountRepository.findByUsername("a");
        MvcResult mvcResult = mockMvc.perform(get("/task/today").with(user(account)))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("tasks"))
            .andExpect(view().name("task/today"))
            .andReturn();
        ModelMap modelMap = mvcResult.getModelAndView().getModelMap();
        Object listObj = modelMap.get("tasks");
        assertThat(listObj, is(notNullValue()));
        assertThat(listObj, is(instanceOf(Page.class)));
        Page<Task> page = (Page<Task>) listObj;
        assertThat(page.getTotalElements(), is(3L));
    }

    @Test
    public void countTaskToday() throws Exception {
        Account account = accountRepository.findByUsername("a");
        mockMvc.perform(get("/task/today/count").with(user(account)))
            .andExpect(status().isOk())
            .andExpect(content().string("3"));
    }
}
