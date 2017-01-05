package com.ksoichiro.task.web;

import com.ksoichiro.task.annotation.Create;
import com.ksoichiro.task.annotation.CreateSave;
import com.ksoichiro.task.annotation.LoginAccount;
import com.ksoichiro.task.annotation.StandardController;
import com.ksoichiro.task.domain.Account;
import com.ksoichiro.task.form.TeamCreateForm;
import com.ksoichiro.task.service.TeamService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;

@StandardController("/team")
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class TeamController {
    private TeamService teamService;

    @RequestMapping
    public String index(@LoginAccount Account account, Model model, @PageableDefault Pageable pageable) {
        model.addAttribute("teams", teamService.findByAccount(account, pageable));
        return "team/index";
    }

    @Create
    public String create(@LoginAccount Account account, TeamCreateForm teamCreateForm, BindingResult bindingResult, Model model) {
        return "team/create";
    }

    @CreateSave
    public String createSave(@LoginAccount Account account, @Validated TeamCreateForm teamCreateForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return create(account, teamCreateForm, bindingResult, model);
        }
        teamService.create(teamCreateForm.toDTO(account));
        return "redirect:/team";
    }
}
