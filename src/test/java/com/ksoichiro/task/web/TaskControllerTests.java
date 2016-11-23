package com.ksoichiro.task.web;

import com.ksoichiro.task.annotation.MvcTestConfiguration;
import com.ksoichiro.task.constant.TaskStatusEnum;
import com.ksoichiro.task.domain.Account;
import com.ksoichiro.task.domain.Tag;
import com.ksoichiro.task.domain.Task;
import com.ksoichiro.task.repository.AccountRepository;
import com.ksoichiro.task.repository.TaskRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.ModelMap;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Sql({"/truncate.sql", "/web/data-task.sql"})
@MvcTestConfiguration
public class TaskControllerTests extends AbstractTransactionalJUnit4SpringContextTests {
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TaskRepository taskRepository;

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
    @SuppressWarnings("unchecked")
    public void allSearch() throws Exception {
        Account account = accountRepository.findByUsername("a");
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("name", "mail ");
        MvcResult mvcResult = mockMvc.perform(post("/task/all")
            .with(user(account)).with(csrf()).params(params))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("tasks"))
            .andExpect(view().name("task/all"))
            .andReturn();
        ModelMap modelMap = mvcResult.getModelAndView().getModelMap();
        Object listObj = modelMap.get("tasks");
        assertThat(listObj, is(notNullValue()));
        assertThat(listObj, is(instanceOf(Page.class)));
        Page<Task> page = (Page<Task>) listObj;
        assertThat(page.getTotalElements(), is(3L));
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
    @SuppressWarnings("unchecked")
    public void todaySearch() throws Exception {
        Account account = accountRepository.findByUsername("a");
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("name", "mail ");
        params.add("status", "0");
        MvcResult mvcResult = mockMvc.perform(post("/task/today")
            .with(user(account)).with(csrf()).params(params))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("tasks"))
            .andExpect(view().name("task/today"))
            .andReturn();
        ModelMap modelMap = mvcResult.getModelAndView().getModelMap();
        Object listObj = modelMap.get("tasks");
        assertThat(listObj, is(notNullValue()));
        assertThat(listObj, is(instanceOf(Page.class)));
        Page<Task> page = (Page<Task>) listObj;
        assertThat(page.getTotalElements(), is(2L));
    }

    @Test
    public void countTaskToday() throws Exception {
        Account account = accountRepository.findByUsername("a");
        mockMvc.perform(get("/task/today/count").with(user(account)))
            .andExpect(status().isOk())
            .andExpect(content().string("3"));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void create() throws Exception {
        Account account = accountRepository.findByUsername("a");
        MvcResult mvcResult = mockMvc.perform(get("/task/create").with(user(account)))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("allTaskStatus"))
            .andExpect(view().name("task/create"))
            .andReturn();
        ModelMap modelMap = mvcResult.getModelAndView().getModelMap();
        Object listObj = modelMap.get("allTaskStatus");
        assertThat(listObj, is(notNullValue()));
        TaskStatusEnum[] status = (TaskStatusEnum[]) listObj;
        assertThat(status, is(TaskStatusEnum.values()));
        listObj = modelMap.get("myTags");
        assertThat(listObj, is(notNullValue()));
        List<Tag> tags = (List<Tag>) listObj;
        assertThat(tags.stream().map(Tag::getName).collect(Collectors.toList()), is(Arrays.asList("Work", "Private")));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void createSave() throws Exception {
        Account account = accountRepository.findByUsername("a");
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("name", "Foo");
        params.add("status", "1");
        params.add("tags", "1");
        params.add("tags", "2");
        mockMvc.perform(post("/task/create-save")
            .with(user(account)).with(csrf()).params(params))
            .andExpect(status().is3xxRedirection())
            .andReturn();
        Page<Task> page = taskRepository.findByAccount(account, new PageRequest(0, 10));
        Task result = page.getContent().stream().filter(t -> "Foo".equals(t.getName())).findFirst().get();
        assertThat(result, is(notNullValue()));
        assertThat(result.getName(), is("Foo"));
        assertThat(result.getStatus(), is(TaskStatusEnum.DOING));
        assertThat(result.getTags().stream().map(Tag::getName).collect(Collectors.toList()), containsInAnyOrder("Work", "Private"));
    }

    @Test
    public void createSaveWithValidationError() throws Exception {
        Account account = accountRepository.findByUsername("a");
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("name", "");
        params.add("status", "1");
        params.add("tags", "1");
        params.add("tags", "2");
        mockMvc.perform(post("/task/create-save")
            .with(user(account)).with(csrf()).params(params))
            .andExpect(status().isOk())
            .andExpect(model().hasErrors())
            .andExpect(model().attributeHasFieldErrors("taskCreateForm", "name"));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void update() throws Exception {
        Account account = accountRepository.findByUsername("a");
        MvcResult mvcResult = mockMvc.perform(get("/task/update/4").with(user(account)))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("allTaskStatus"))
            .andExpect(view().name("task/update"))
            .andReturn();
        ModelMap modelMap = mvcResult.getModelAndView().getModelMap();
        Object listObj = modelMap.get("allTaskStatus");
        assertThat(listObj, is(notNullValue()));
        TaskStatusEnum[] status = (TaskStatusEnum[]) listObj;
        assertThat(status, is(TaskStatusEnum.values()));
        listObj = modelMap.get("myTags");
        assertThat(listObj, is(notNullValue()));
        List<Tag> tags = (List<Tag>) listObj;
        assertThat(tags.stream().map(Tag::getName).collect(Collectors.toList()), is(Arrays.asList("Work", "Private")));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void updateSave() throws Exception {
        Account account = accountRepository.findByUsername("a");
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("id", "4");
        params.add("name", "Foo");
        params.add("status", "2");
        params.add("tags", "1");
        params.add("tags", "2");
        mockMvc.perform(post("/task/update-save")
            .with(user(account)).with(csrf()).params(params))
            .andExpect(status().is3xxRedirection())
            .andReturn();
        Page<Task> page = taskRepository.findByAccount(account, new PageRequest(0, 10));
        Task result = page.getContent().stream().filter(t -> "Foo".equals(t.getName())).findFirst().get();
        assertThat(result, is(notNullValue()));
        assertThat(result.getName(), is("Foo"));
        assertThat(result.getStatus(), is(TaskStatusEnum.HOLD));
        assertThat(result.getTags().stream().map(Tag::getName).collect(Collectors.toList()), containsInAnyOrder("Work", "Private"));
    }

    @Test
    public void updateSaveWithValidationErrorOnId() throws Exception {
        Account account = accountRepository.findByUsername("a");
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("id", "");
        params.add("name", "Foo");
        params.add("status", "2");
        params.add("tags", "1");
        params.add("tags", "2");
        mockMvc.perform(post("/task/update-save")
            .with(user(account)).with(csrf()).params(params))
            .andExpect(status().is3xxRedirection());
    }

    @Test
    public void updateSaveWithValidationError() throws Exception {
        Account account = accountRepository.findByUsername("a");
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("id", "4");
        params.add("name", "");
        params.add("status", "2");
        params.add("tags", "1");
        params.add("tags", "2");
        mockMvc.perform(post("/task/update-save")
            .with(user(account)).with(csrf()).params(params))
            .andExpect(status().isOk())
            .andExpect(model().attributeHasFieldErrors("taskUpdateForm", "name"));
    }
}
