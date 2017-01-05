package com.ksoichiro.task.web;

import com.ksoichiro.task.annotation.*;
import com.ksoichiro.task.constant.TaskStatusEnum;
import com.ksoichiro.task.domain.Account;
import com.ksoichiro.task.dto.TaskDTO;
import com.ksoichiro.task.form.TaskCreateForm;
import com.ksoichiro.task.form.TaskSearchForm;
import com.ksoichiro.task.form.TaskUpdateForm;
import com.ksoichiro.task.service.TagService;
import com.ksoichiro.task.service.TaskService;
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
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@StandardController("/task")
@AllArgsConstructor(onConstructor = @__({@Autowired}))
@Slf4j
public class TaskController {
    private TaskService taskService;

    private TagService tagService;

    @RequestMapping("/today/count")
    @ResponseBody
    public String countTaskToday(@LoginAccount Account account) {
        return taskService.countByAccountAndScheduledAtIsToday(account).toString();
    }

    @RequestMapping("/all/count")
    @ResponseBody
    public String countTaskAll(@LoginAccount Account account) {
        return taskService.countByAccount(account).toString();
    }

    @Get("/today")
    public String today(@LoginAccount Account account, TaskSearchForm taskSearchForm, Model model, @PageableDefault Pageable pageable) {
        model.addAttribute("allTaskStatus", TaskStatusEnum.values());
        model.addAttribute("tasks", taskService.findByAccountAndScheduledAtIsToday(account, pageable));
        return "task/today";
    }

    @Post("/today")
    public String todaySearch(@LoginAccount Account account, TaskSearchForm taskSearchForm, Model model, @PageableDefault Pageable pageable) {
        model.addAttribute("allTaskStatus", TaskStatusEnum.values());
        TaskDTO dto = taskSearchForm.toDTO(account);
        dto.setScheduledAt(new Date());
        model.addAttribute("tasks", taskService.findByAccountAndConditions(dto, pageable));
        return "task/today";
    }

    @Get("/all")
    public String all(@LoginAccount Account account, TaskSearchForm taskSearchForm, Model model, @PageableDefault Pageable pageable) {
        model.addAttribute("allTaskStatus", TaskStatusEnum.values());
        model.addAttribute("tasks", taskService.findByAccount(account, pageable));
        return "task/all";
    }

    @Post("/all")
    public String allSearch(@LoginAccount Account account, TaskSearchForm taskSearchForm, Model model, @PageableDefault Pageable pageable) {
        model.addAttribute("allTaskStatus", TaskStatusEnum.values());
        model.addAttribute("tasks", taskService.findByAccountAndConditions(taskSearchForm.toDTO(account), pageable));
        return "task/all";
    }

    @Create
    public String create(@LoginAccount Account account, TaskCreateForm taskCreateForm, BindingResult bindingResult, Model model) {
        model.addAttribute("allTaskStatus", TaskStatusEnum.values());
        model.addAttribute("myTags", tagService.findByAccount(account));
        return "task/create";
    }

    @CreateSave
    public String createSave(@LoginAccount Account account, @Validated TaskCreateForm taskCreateForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return create(account, taskCreateForm, bindingResult, model);
        }
        try {
            taskService.create(taskCreateForm.toDTO(account));
        } catch (Exception e) {
            log.warn("Failed to create task for account {}", account.getId(), e);
            bindingResult.reject("error.task.create");
            return create(account, taskCreateForm, bindingResult, model);
        }
        return "redirect:/task/today";
    }

    @Update
    public String update(@PathVariable Integer id,
                         @LoginAccount Account account,
                         TaskUpdateForm taskUpdateForm, BindingResult bindingResult, Model model) {
        taskUpdateForm.copyFrom(taskService.findByIdAndAccount(id, account));
        model.addAttribute("allTaskStatus", TaskStatusEnum.values());
        model.addAttribute("myTags", tagService.findByAccount(account));
        return "task/update";
    }

    @UpdateSave
    public String updateSave(@LoginAccount Account account,
                             @Validated TaskUpdateForm taskUpdateForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            if (taskUpdateForm.cannotDecideWhatToUpdate()) {
                return "redirect:/task/today";
            }
            return update(taskUpdateForm.getId(), account, taskUpdateForm, bindingResult, model);
        }
        try {
            taskService.update(taskUpdateForm.toDTO(account));
        } catch (Exception e) {
            log.warn("Failed to update task for account {}", account.getId(), e);
            bindingResult.reject("error.task.update");
            return update(taskUpdateForm.getId(), account, taskUpdateForm, bindingResult, model);
        }
        return "redirect:/task/today";
    }
}
