package com.ksoichiro.task.it;

import com.ksoichiro.task.annotation.MvcTestConfiguration;
import com.ksoichiro.task.domain.Account;
import com.ksoichiro.task.domain.Tag;
import com.ksoichiro.task.repository.AccountRepository;
import com.ksoichiro.task.repository.TagRepository;
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

@Sql({"/truncate.sql", "/data-tag.sql"})
@MvcTestConfiguration
public class TagControllerTests extends AbstractTransactionalJUnit4SpringContextTests {
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TagRepository tagRepository;

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
        MvcResult mvcResult = mockMvc.perform(get("/tag").with(user(account)))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("tags"))
            .andExpect(view().name("tag/index"))
            .andReturn();
        ModelMap modelMap = mvcResult.getModelAndView().getModelMap();
        Object listObj = modelMap.get("tags");
        assertThat(listObj, is(notNullValue()));
        assertThat(listObj, is(instanceOf(Page.class)));
        Page<Tag> page = (Page<Tag>) listObj;
        assertThat(page.getTotalElements(), is(4L));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void create() throws Exception {
        Account account = accountRepository.findByUsername("a");
        mockMvc.perform(get("/tag/create").with(user(account)))
            .andExpect(status().isOk())
            .andExpect(view().name("tag/create"));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void createSave() throws Exception {
        Account account = accountRepository.findByUsername("a");
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("name", "Foo");
        mockMvc.perform(post("/tag/create-save")
            .with(user(account)).with(csrf()).params(params))
            .andExpect(status().is3xxRedirection())
            .andReturn();
        Page<Tag> page = tagRepository.findByAccount(account, new PageRequest(0, 10));
        Tag result = page.getContent().stream().filter(t -> "Foo".equals(t.getName())).findFirst().get();
        assertThat(result, is(notNullValue()));
        assertThat(result.getName(), is("Foo"));
        assertThat(result.getTeam().getId(), is(0));
    }

    @Test
    public void createSaveWithValidationError() throws Exception {
        Account account = accountRepository.findByUsername("a");
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("name", "");
        mockMvc.perform(post("/tag/create-save")
            .with(user(account)).with(csrf()).params(params))
            .andExpect(status().isOk())
            .andExpect(model().hasErrors())
            .andExpect(model().attributeHasFieldErrors("tagCreateForm", "name"));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void update() throws Exception {
        Account account = accountRepository.findByUsername("a");
        mockMvc.perform(get("/tag/update/4").with(user(account)))
            .andExpect(status().isOk())
            .andExpect(view().name("tag/update"));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void updateSave() throws Exception {
        Account account = accountRepository.findByUsername("a");
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("id", "4");
        params.add("name", "Foo");
        mockMvc.perform(post("/tag/update-save")
            .with(user(account)).with(csrf()).params(params))
            .andExpect(status().is3xxRedirection())
            .andReturn();
        Page<Tag> page = tagRepository.findByAccount(account, new PageRequest(0, 10));
        Tag result = page.getContent().stream().filter(t -> "Foo".equals(t.getName())).findFirst().get();
        assertThat(result, is(notNullValue()));
        assertThat(result.getName(), is("Foo"));
        assertThat(result.getTeam().getId(), is(0));
    }

    @Test
    public void updateSaveWithValidationErrorOnId() throws Exception {
        Account account = accountRepository.findByUsername("a");
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("id", "");
        params.add("name", "Foo");
        mockMvc.perform(post("/tag/update-save")
            .with(user(account)).with(csrf()).params(params))
            .andExpect(status().is3xxRedirection());
    }

    @Test
    public void updateSaveWithValidationError() throws Exception {
        Account account = accountRepository.findByUsername("a");
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("id", "4");
        params.add("name", "");
        mockMvc.perform(post("/tag/update-save")
            .with(user(account)).with(csrf()).params(params))
            .andExpect(status().isOk())
            .andExpect(model().attributeHasFieldErrors("tagUpdateForm", "name"));
    }
}
