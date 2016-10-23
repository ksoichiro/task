package com.ksoichiro.task.web;

import com.ksoichiro.task.domain.Account;
import com.ksoichiro.task.domain.Task;
import com.ksoichiro.task.form.TaskCreateForm;
import com.ksoichiro.task.form.TaskUpdateForm;
import com.ksoichiro.task.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/task")
@Slf4j
public class TaskController {
    @Autowired
    private TaskService taskService;

    @RequestMapping("/today")
    public String today(@AuthenticationPrincipal Account account, Model model, @PageableDefault Pageable pageable) {
        model.addAttribute("tasks", taskService.findByAccountAndScheduledAtIsToday(account, pageable));
        return "task/today";
    }

    @RequestMapping("/all")
    public String all(@AuthenticationPrincipal Account account, Model model, @PageableDefault Pageable pageable) {
        model.addAttribute("tasks", taskService.findByAccount(account, pageable));
        return "task/all";
    }

    @RequestMapping("/create")
    public String create(TaskCreateForm taskCreateForm, BindingResult bindingResult, Model model) {
        return "task/create";
    }

    @RequestMapping(value = "/create-save", method = RequestMethod.POST)
    public String createSave(@AuthenticationPrincipal Account account, @Validated TaskCreateForm taskCreateForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return create(taskCreateForm, bindingResult, model);
        }
        try {
            Task task = new Task();
            task.setAccount(account);
            BeanUtils.copyProperties(taskCreateForm, task);
            taskService.create(task);
        } catch (Exception e) {
            log.warn("Failed to create task for account {}", account.getId(), e);
            bindingResult.reject("error.task.create");
            return create(taskCreateForm, bindingResult, model);
        }
        return "redirect:/task/today";
    }

    @RequestMapping("/update/{id}")
    public String update(@PathVariable Integer id,
                         @AuthenticationPrincipal Account account,
                         TaskUpdateForm taskUpdateForm, BindingResult bindingResult, Model model) {
        Task task = taskService.findByIdAndAccount(id, account);
        BeanUtils.copyProperties(task, taskUpdateForm);
        return "task/update";
    }

    @RequestMapping(value = "/update-save", method = RequestMethod.POST)
    public String updateSave(@AuthenticationPrincipal Account account,
                             @Validated TaskUpdateForm taskUpdateForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return update(taskUpdateForm.getId(), account, taskUpdateForm, bindingResult, model);
        }
        try {
            Task task = new Task();
            BeanUtils.copyProperties(taskUpdateForm, task);
            task.setAccount(account);
            taskService.update(task);
        } catch (Exception e) {
            log.warn("Failed to update task for account {}", account.getId(), e);
            bindingResult.reject("error.task.update");
            return update(taskUpdateForm.getId(), account, taskUpdateForm, bindingResult, model);
        }
        return "redirect:/task/today";
    }
}
