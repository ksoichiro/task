package com.ksoichiro.task.web;

import com.ksoichiro.task.domain.Account;
import com.ksoichiro.task.domain.Project;
import com.ksoichiro.task.repository.ProjectRepository;
import com.ksoichiro.task.service.ProjectService;
import com.ksoichiro.task.service.TeamService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Arrays;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ProjectControllerTests {
    @InjectMocks
    private ProjectController projectController;

    @Mock
    private ProjectService projectService;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private TeamService teamService;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
            .standaloneSetup(projectController)
            .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
            .build();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void index() throws Exception {
        Account account = new Account();
        account.setId(1);
        when(projectService.findByAccount(eq(account), anyObject()))
            .thenReturn(new PageImpl<>(Arrays.asList(new Project(), new Project())));
        mockMvc.perform(get("/project").with(user(account)))
            .andExpect(status().isOk())
            .andExpect(view().name("project/index"));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void create() throws Exception {
        Account account = new Account();
        account.setId(1);
        mockMvc.perform(get("/project/create").with(user(account)))
            .andExpect(status().isOk())
            .andExpect(view().name("project/create"));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void createSave() throws Exception {
        Account account = new Account();
        account.setId(1);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("cd", "foo2");
        params.add("name", "Foo2");
        params.add("teamId", "2");
        mockMvc.perform(post("/project/create-save")
            .with(user(account)).with(csrf()).params(params))
            .andExpect(status().is3xxRedirection());
    }

    @Test
    public void createSaveWithValidationError() throws Exception {
        Account account = new Account();
        account.setId(1);
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
        Account account = new Account();
        account.setId(1);
        when(projectService.findByIdAndAccount(anyInt(), eq(account)))
            .thenReturn(new Project());
        mockMvc.perform(get("/project/update/1").with(user(account)))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("projectUpdateForm"))
            .andExpect(view().name("project/update"));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void updateSave() throws Exception {
        Account account = new Account();
        account.setId(1);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("id", "1");
        params.add("cd", "baz");
        params.add("name", "Baz");
        mockMvc.perform(post("/project/update-save")
            .with(user(account)).with(csrf()).params(params))
            .andExpect(status().is3xxRedirection());
    }

    @Test
    public void updateSaveWithValidationErrorOnId() throws Exception {
        Account account = new Account();
        account.setId(1);
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
        Account account = new Account();
        account.setId(1);
        when(projectService.findByIdAndAccount(anyInt(), anyObject()))
            .thenReturn(new Project());
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
