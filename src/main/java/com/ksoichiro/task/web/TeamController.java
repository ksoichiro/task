package com.ksoichiro.task.web;

import com.ksoichiro.task.annotation.Post;
import com.ksoichiro.task.annotation.StandardController;
import com.ksoichiro.task.domain.Account;
import com.ksoichiro.task.form.TeamCreateForm;
import com.ksoichiro.task.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;

@StandardController("/team")
public class TeamController {
    @Autowired
    private TeamService teamService;

    @RequestMapping
    public String index(@AuthenticationPrincipal Account account, Model model, @PageableDefault Pageable pageable) {
        model.addAttribute("teams", teamService.findByAccount(account, pageable));
        return "team/index";
    }

    @RequestMapping("/create")
    public String create(@AuthenticationPrincipal Account account, TeamCreateForm teamCreateForm, BindingResult bindingResult, Model model) {
        return "team/create";
    }

    @Post("/create-save")
    public String createSave(@AuthenticationPrincipal Account account, @Validated TeamCreateForm teamCreateForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return create(account, teamCreateForm, bindingResult, model);
        }
        teamService.create(teamCreateForm.toDTO(account));
        return "redirect:/team";
    }
}
