package com.ksoichiro.task.web;

import com.ksoichiro.task.annotation.Post;
import com.ksoichiro.task.annotation.StandardController;
import com.ksoichiro.task.domain.Account;
import com.ksoichiro.task.form.ProjectCreateForm;
import com.ksoichiro.task.form.ProjectUpdateForm;
import com.ksoichiro.task.service.ProjectService;
import com.ksoichiro.task.service.TeamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@StandardController("/project")
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

    @Post("/create-save")
    public String createSave(@AuthenticationPrincipal Account account, @Validated ProjectCreateForm projectCreateForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return create(account, projectCreateForm, bindingResult, model);
        }
        projectService.create(projectCreateForm.toDTO(account));
        return "redirect:/project";
    }

    @RequestMapping("/update/{id}")
    public String update(@PathVariable Integer id,
                         @AuthenticationPrincipal Account account, ProjectUpdateForm projectUpdateForm, BindingResult bindingResult, Model model) {
        projectUpdateForm.copyFrom(projectService.findByIdAndAccount(id, account));
        return "project/update";
    }

    @Post("/update-save")
    public String updateSave(@AuthenticationPrincipal Account account, @Validated ProjectUpdateForm projectUpdateForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            if (projectUpdateForm.cannotDecideWhatToUpdate()) {
                return "redirect:/task/today";
            }
            return update(projectUpdateForm.getId(), account, projectUpdateForm, bindingResult, model);
        }
        projectService.update(projectUpdateForm.toDTO(account));
        return "redirect:/project";
    }
}
