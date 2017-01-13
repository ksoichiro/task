package com.ksoichiro.task.web;

import com.ksoichiro.task.annotation.MvcTestConfiguration;
import com.ksoichiro.task.domain.Account;
import com.ksoichiro.task.domain.Team;
import com.ksoichiro.task.form.TeamUpdateForm;
import com.ksoichiro.task.repository.AccountRepository;
import com.ksoichiro.task.repository.TeamRepository;
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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Sql({"/truncate.sql", "/web/data-team.sql"})
@MvcTestConfiguration
public class TeamControllerTests extends AbstractTransactionalJUnit4SpringContextTests {
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TeamRepository teamRepository;

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
        Account account = accountRepository.findByUsername("a");
        MvcResult mvcResult = mockMvc.perform(get("/team").with(user(account)))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("teams"))
            .andExpect(view().name("team/index"))
            .andReturn();
        ModelMap modelMap = mvcResult.getModelAndView().getModelMap();
        Object listObj = modelMap.get("teams");
        assertThat(listObj, is(notNullValue()));
        assertThat(listObj, is(instanceOf(Page.class)));
        Page<Team> page = (Page<Team>) listObj;
        assertThat(page.getTotalElements(), is(3L));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void create() throws Exception {
        Account account = accountRepository.findByUsername("a");
        mockMvc.perform(get("/team/create").with(user(account)))
            .andExpect(status().isOk())
            .andExpect(view().name("team/create"));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void createSave() throws Exception {
        Account account = accountRepository.findByUsername("a");
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("cd", "foo");
        params.add("name", "Foo");
        mockMvc.perform(post("/team/create-save")
            .with(user(account)).with(csrf()).params(params))
            .andExpect(status().is3xxRedirection())
            .andReturn();
        Page<Team> page = teamRepository.findByAccount(account, new PageRequest(0, 10));
        Team result = page.getContent().stream().filter(t -> "Foo".equals(t.getName())).findFirst().get();
        assertThat(result, is(notNullValue()));
        assertThat(result.getCd(), is("foo"));
        assertThat(result.getName(), is("Foo"));
    }

    @Test
    public void createSaveWithValidationError() throws Exception {
        Account account = accountRepository.findByUsername("a");
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("cd", "foo");
        params.add("name", "");
        mockMvc.perform(post("/team/create-save")
            .with(user(account)).with(csrf()).params(params))
            .andExpect(status().isOk())
            .andExpect(model().hasErrors())
            .andExpect(model().attributeHasFieldErrors("teamCreateForm", "name"));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void update() throws Exception {
        Account account = accountRepository.findByUsername("a");
        MvcResult mvcResult = mockMvc.perform(get("/team/update/2").with(user(account)))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("teamUpdateForm"))
            .andExpect(view().name("team/update"))
            .andReturn();
        ModelMap modelMap = mvcResult.getModelAndView().getModelMap();
        Object form = modelMap.get("teamUpdateForm");
        assertThat(form, is(notNullValue()));
        TeamUpdateForm teamUpdateForm = (TeamUpdateForm) form;
        assertThat(teamUpdateForm.getId(), is(2));
        assertThat(teamUpdateForm.getCd(), is("team2"));
        assertThat(teamUpdateForm.getName(), is("Team 2"));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void updateSave() throws Exception {
        Account account = accountRepository.findByUsername("a");
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("id", "2");
        params.add("cd", "cannot_be_changed");
        params.add("name", "Team_2");
        mockMvc.perform(post("/team/update-save")
            .with(user(account)).with(csrf()).params(params))
            .andExpect(status().is3xxRedirection())
            .andReturn();
        Page<Team> page = teamRepository.findByAccount(account, new PageRequest(0, 10));
        Team result = page.getContent().stream().filter(t -> "Team_2".equals(t.getName())).findFirst().get();
        assertThat(result, is(notNullValue()));
        assertThat(result.getCd(), is("team2"));
        assertThat(result.getName(), is("Team_2"));
    }

    @Test
    public void updateSaveWithValidationErrorOnId() throws Exception {
        Account account = accountRepository.findByUsername("a");
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("id", "");
        params.add("cd", "bar");
        params.add("name", "Bar");
        mockMvc.perform(post("/team/update-save")
            .with(user(account)).with(csrf()).params(params))
            .andExpect(status().is3xxRedirection());
    }

    @Test
    public void updateSaveWithValidationError() throws Exception {
        Account account = accountRepository.findByUsername("a");
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("id", "2");
        params.add("cd", "team_2");
        params.add("name", "");
        mockMvc.perform(post("/team/update-save")
            .with(user(account)).with(csrf()).params(params))
            .andExpect(status().isOk())
            .andExpect(model().attributeHasFieldErrors("teamUpdateForm", "name"));
    }
}
