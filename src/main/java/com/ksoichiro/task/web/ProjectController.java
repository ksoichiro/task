package com.ksoichiro.task.web;

import com.ksoichiro.task.annotation.*;
import com.ksoichiro.task.domain.Account;
import com.ksoichiro.task.form.ProjectCreateForm;
import com.ksoichiro.task.form.ProjectUpdateForm;
import com.ksoichiro.task.service.ProjectService;
import com.ksoichiro.task.service.TeamService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@StandardController("/project")
@AllArgsConstructor(onConstructor = @__({@Autowired}))
@Slf4j
public class ProjectController {
    private ProjectService projectService;

    private TeamService teamService;

    @RequestMapping
    public String index(@LoginAccount Account account, Model model, @PageableDefault Pageable pageable) {
        model.addAttribute("projects", projectService.findByAccount(account, pageable));
        return "project/index";
    }

    @Create
    public String create(@LoginAccount Account account, ProjectCreateForm projectCreateForm, BindingResult bindingResult, Model model) {
        model.addAttribute("teams", teamService.findByAccount(account));
        return "project/create";
    }

    @CreateSave
    public String createSave(@LoginAccount Account account, @Validated ProjectCreateForm projectCreateForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return create(account, projectCreateForm, bindingResult, model);
        }
        projectService.create(projectCreateForm.toDTO(account));
        return "redirect:/project";
    }

    @Update
    public String update(@PathVariable Integer id,
                         @LoginAccount Account account, ProjectUpdateForm projectUpdateForm, BindingResult bindingResult, Model model) {
        projectUpdateForm.copyFrom(projectService.findByIdAndAccount(id, account));
        return "project/update";
    }

    @UpdateSave
    public String updateSave(@LoginAccount Account account, @Validated ProjectUpdateForm projectUpdateForm, BindingResult bindingResult, Model model) {
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
