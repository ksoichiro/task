package com.ksoichiro.task.web;

import com.ksoichiro.task.domain.Account;
import com.ksoichiro.task.domain.Task;
import com.ksoichiro.task.form.TaskCreateForm;
import com.ksoichiro.task.form.TaskUpdateForm;
import com.ksoichiro.task.service.TaskService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/task")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @ModelAttribute
    public TaskCreateForm taskCreateForm() {
        return new TaskCreateForm();
    }

    @RequestMapping
    public String index(@AuthenticationPrincipal Account account, Model model, @PageableDefault Pageable pageable) {
        model.addAttribute("tasks", taskService.findByAccount(account, pageable));
        return "task/index";
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
        Task task = new Task();
        task.setAccount(account);
        BeanUtils.copyProperties(taskCreateForm, task);
        taskService.create(task);
        return "redirect:/task";
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
        Task task = new Task();
        task.setAccount(account);
        BeanUtils.copyProperties(taskUpdateForm, task);
        taskService.update(task, account);
        return "redirect:/task";
    }
}
