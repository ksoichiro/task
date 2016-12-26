package com.ksoichiro.task.web;

import com.ksoichiro.task.annotation.Get;
import com.ksoichiro.task.annotation.Post;
import com.ksoichiro.task.annotation.StandardController;
import com.ksoichiro.task.constant.TaskStatusEnum;
import com.ksoichiro.task.domain.Account;
import com.ksoichiro.task.domain.Task;
import com.ksoichiro.task.dto.TaskDTO;
import com.ksoichiro.task.form.TaskCreateForm;
import com.ksoichiro.task.form.TaskSearchForm;
import com.ksoichiro.task.form.TaskUpdateForm;
import com.ksoichiro.task.service.TagService;
import com.ksoichiro.task.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@StandardController("/task")
@Slf4j
public class TaskController {
    @Autowired
    private TaskService taskService;

    @Autowired
    private TagService tagService;

    @RequestMapping("/today/count")
    @ResponseBody
    public String countTaskToday(@AuthenticationPrincipal Account account) {
        return taskService.countByAccountAndScheduledAtIsToday(account).toString();
    }

    @RequestMapping("/all/count")
    @ResponseBody
    public String countTaskAll(@AuthenticationPrincipal Account account) {
        return taskService.countByAccount(account).toString();
    }

    @Get("/today")
    public String today(@AuthenticationPrincipal Account account, TaskSearchForm taskSearchForm, Model model, @PageableDefault Pageable pageable) {
        model.addAttribute("allTaskStatus", TaskStatusEnum.values());
        model.addAttribute("tasks", taskService.findByAccountAndScheduledAtIsToday(account, pageable));
        return "task/today";
    }

    @Post("/today")
    public String todaySearch(@AuthenticationPrincipal Account account, TaskSearchForm taskSearchForm, Model model, @PageableDefault Pageable pageable) {
        model.addAttribute("allTaskStatus", TaskStatusEnum.values());
        TaskDTO dto = taskSearchForm.toTaskDTO(account);
        dto.setScheduledAt(new Date());
        model.addAttribute("tasks", taskService.findByAccountAndConditions(dto, pageable));
        return "task/today";
    }

    @Get("/all")
    public String all(@AuthenticationPrincipal Account account, TaskSearchForm taskSearchForm, Model model, @PageableDefault Pageable pageable) {
        model.addAttribute("allTaskStatus", TaskStatusEnum.values());
        model.addAttribute("tasks", taskService.findByAccount(account, pageable));
        return "task/all";
    }

    @Post("/all")
    public String allSearch(@AuthenticationPrincipal Account account, TaskSearchForm taskSearchForm, Model model, @PageableDefault Pageable pageable) {
        model.addAttribute("allTaskStatus", TaskStatusEnum.values());
        model.addAttribute("tasks", taskService.findByAccountAndConditions(taskSearchForm.toTaskDTO(account), pageable));
        return "task/all";
    }

    @RequestMapping("/create")
    public String create(@AuthenticationPrincipal Account account, TaskCreateForm taskCreateForm, BindingResult bindingResult, Model model) {
        model.addAttribute("allTaskStatus", TaskStatusEnum.values());
        model.addAttribute("myTags", tagService.findByAccount(account));
        return "task/create";
    }

    @Post("/create-save")
    public String createSave(@AuthenticationPrincipal Account account, @Validated TaskCreateForm taskCreateForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return create(account, taskCreateForm, bindingResult, model);
        }
        try {
            taskService.create(taskCreateForm.toTaskDTO(account));
        } catch (Exception e) {
            log.warn("Failed to create task for account {}", account.getId(), e);
            bindingResult.reject("error.task.create");
            return create(account, taskCreateForm, bindingResult, model);
        }
        return "redirect:/task/today";
    }

    @RequestMapping("/update/{id}")
    public String update(@PathVariable Integer id,
                         @AuthenticationPrincipal Account account,
                         TaskUpdateForm taskUpdateForm, BindingResult bindingResult, Model model) {
        Task task = taskService.findByIdAndAccount(id, account);
        BeanUtils.copyProperties(task, taskUpdateForm);
        model.addAttribute("allTaskStatus", TaskStatusEnum.values());
        model.addAttribute("myTags", tagService.findByAccount(account));
        return "task/update";
    }

    @Post("/update-save")
    public String updateSave(@AuthenticationPrincipal Account account,
                             @Validated TaskUpdateForm taskUpdateForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            if (StringUtils.isEmpty(taskUpdateForm.getId())) {
                // Can't decide the task to update
                return "redirect:/task/today";
            }
            return update(taskUpdateForm.getId(), account, taskUpdateForm, bindingResult, model);
        }
        try {
            taskService.update(taskUpdateForm.toTaskDTO(account));
        } catch (Exception e) {
            log.warn("Failed to update task for account {}", account.getId(), e);
            bindingResult.reject("error.task.update");
            return update(taskUpdateForm.getId(), account, taskUpdateForm, bindingResult, model);
        }
        return "redirect:/task/today";
    }
}
