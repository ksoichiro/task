package com.ksoichiro.task.web;

import com.ksoichiro.task.annotation.MvcTestConfiguration;
import com.ksoichiro.task.domain.Account;
import com.ksoichiro.task.domain.Project;
import com.ksoichiro.task.domain.Task;
import com.ksoichiro.task.domain.Team;
import com.ksoichiro.task.form.ProjectUpdateForm;
import com.ksoichiro.task.repository.AccountRepository;
import com.ksoichiro.task.repository.ProjectRepository;
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

import java.util.Collections;
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

@Sql({"/truncate.sql", "/web/data-project.sql"})
@MvcTestConfiguration
public class ProjectControllerTests extends AbstractTransactionalJUnit4SpringContextTests {
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ProjectRepository projectRepository;

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
    public void index() throws Exception {
        Account account = accountRepository.findByUsername("b");
        MvcResult mvcResult = mockMvc.perform(get("/project").with(user(account)))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("projects"))
            .andExpect(view().name("project/index"))
            .andReturn();
        ModelMap modelMap = mvcResult.getModelAndView().getModelMap();
        Object listObj = modelMap.get("projects");
        assertThat(listObj, is(notNullValue()));
        assertThat(listObj, is(instanceOf(Page.class)));
        Page<Task> page = (Page<Task>) listObj;
        assertThat(page.getTotalElements(), is(2L));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void create() throws Exception {
        Account account = accountRepository.findByUsername("a");
        MvcResult mvcResult = mockMvc.perform(get("/project/create").with(user(account)))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("teams"))
            .andExpect(view().name("project/create"))
            .andReturn();
        ModelMap modelMap = mvcResult.getModelAndView().getModelMap();
        Object listObj = modelMap.get("teams");
        assertThat(listObj, is(notNullValue()));
        List<Team> teams = (List<Team>) listObj;
        assertThat(teams.stream().map(Team::getId).collect(Collectors.toList()), is(Collections.singletonList(2)));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void createSave() throws Exception {
        Account account = accountRepository.findByUsername("a");
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("cd", "foo2");
        params.add("name", "Foo2");
        params.add("teamId", "2");
        mockMvc.perform(post("/project/create-save")
            .with(user(account)).with(csrf()).params(params))
            .andExpect(status().is3xxRedirection())
            .andReturn();
        Page<Project> page = projectRepository.findByAccount(account, new PageRequest(0, 10));
        Project result = page.getContent().stream().filter(p -> "Foo2".equals(p.getName())).findFirst().get();
        assertThat(result, is(notNullValue()));
        assertThat(result.getCd(), is("foo2"));
        assertThat(result.getName(), is("Foo2"));
        assertThat(result.getTeam().getId(), is(2));
    }

    @Test
    public void createSaveWithValidationError() throws Exception {
        Account account = accountRepository.findByUsername("a");
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("name", "");
        params.add("cd", "foo2");
        params.add("teamId", "2");
        mockMvc.perform(post("/project/create-save")
            .with(user(account)).with(csrf()).params(params))
            .andExpect(status().isOk())
            .andExpect(model().hasErrors())
            .andExpect(model().attributeHasFieldErrors("projectCreateForm", "name"));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void update() throws Exception {
        Account account = accountRepository.findByUsername("a");
        MvcResult mvcResult = mockMvc.perform(get("/project/update/1").with(user(account)))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("projectUpdateForm"))
            .andExpect(view().name("project/update"))
            .andReturn();
        Object form = mvcResult.getModelAndView().getModel().get("projectUpdateForm");
        assertThat(form, is(notNullValue()));
        ProjectUpdateForm projectUpdateForm = (ProjectUpdateForm) form;
        assertThat(projectUpdateForm.getCd(), is("foo"));
        assertThat(projectUpdateForm.getName(), is("Foo"));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void updateSave() throws Exception {
        Account account = accountRepository.findByUsername("a");
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("id", "1");
        params.add("cd", "baz");
        params.add("name", "Baz");
        mockMvc.perform(post("/project/update-save")
            .with(user(account)).with(csrf()).params(params))
            .andExpect(status().is3xxRedirection())
            .andReturn();
        Page<Project> page = projectRepository.findByAccount(account, new PageRequest(0, 10));
        Project result = page.getContent().stream().filter(p -> "Baz".equals(p.getName())).findFirst().get();
        assertThat(result, is(notNullValue()));
        assertThat(result.getId(), is(1));
        assertThat(result.getName(), is("Baz"));
        assertThat(result.getCd(), is("baz"));
    }

    @Test
    public void updateSaveWithValidationErrorOnId() throws Exception {
        Account account = accountRepository.findByUsername("a");
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("id", "");
        params.add("cd", "baz");
        params.add("name", "Baz");
        mockMvc.perform(post("/project/update-save")
            .with(user(account)).with(csrf()).params(params))
            .andExpect(status().is3xxRedirection());
    }

    @Test
    public void updateSaveWithValidationError() throws Exception {
        Account account = accountRepository.findByUsername("a");
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("id", "1");
        params.add("cd", "baz");
        params.add("name", "");
        mockMvc.perform(post("/project/update-save")
            .with(user(account)).with(csrf()).params(params))
            .andExpect(status().isOk())
            .andExpect(model().attributeHasFieldErrors("projectUpdateForm", "name"));
    }
}
