package com.ksoichiro.task.web;

import com.ksoichiro.task.domain.Account;
import com.ksoichiro.task.domain.Project;
import com.ksoichiro.task.dto.ProjectDTO;
import com.ksoichiro.task.form.ProjectCreateForm;
import com.ksoichiro.task.form.ProjectUpdateForm;
import com.ksoichiro.task.service.ProjectService;
import com.ksoichiro.task.service.TeamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/project")
@Slf4j
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @Autowired
    private TeamService teamService;

    @RequestMapping
    public String index(@AuthenticationPrincipal Account account, Model model, @PageableDefault Pageable pageable) {
        model.addAttribute("projects", projectService.findByAccount(account, pageable));
        return "project/index";
    }

    @RequestMapping("/create")
    public String create(@AuthenticationPrincipal Account account, ProjectCreateForm projectCreateForm, BindingResult bindingResult, Model model) {
        model.addAttribute("teams", teamService.findByAccount(account));
        return "project/create";
    }

    @RequestMapping(value = "/create-save", method = RequestMethod.POST)
    public String createSave(@AuthenticationPrincipal Account account, @Validated ProjectCreateForm projectCreateForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return create(account, projectCreateForm, bindingResult, model);
        }
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setAccount(account);
        BeanUtils.copyProperties(projectCreateForm, projectDTO);
        projectService.create(projectDTO);
        return "redirect:/project";
    }

    @RequestMapping("/update/{id}")
    public String update(@PathVariable Integer id,
                         @AuthenticationPrincipal Account account, ProjectUpdateForm projectUpdateForm, BindingResult bindingResult, Model model) {
        Project project = projectService.findByIdAndAccount(id, account);
        BeanUtils.copyProperties(project, projectUpdateForm);
        return "project/update";
    }

    @RequestMapping(value = "/update-save", method = RequestMethod.POST)
    public String updateSave(@AuthenticationPrincipal Account account, @Validated ProjectUpdateForm projectUpdateForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            if (StringUtils.isEmpty(projectUpdateForm.getId())) {
                // Can't decide the task to update
                return "redirect:/task/today";
            }
            return update(projectUpdateForm.getId(), account, projectUpdateForm, bindingResult, model);
        }
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setAccount(account);
        BeanUtils.copyProperties(projectUpdateForm, projectDTO);
        projectService.update(projectDTO);
        return "redirect:/project";
    }
}
