package com.ksoichiro.task.web;

import com.ksoichiro.task.domain.Account;
import com.ksoichiro.task.domain.Tag;
import com.ksoichiro.task.form.TagCreateForm;
import com.ksoichiro.task.service.TagService;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/tag")
@Slf4j
public class TagController {
    @Autowired
    private TagService tagService;

    @RequestMapping
    public String index(@AuthenticationPrincipal Account account, Model model, @PageableDefault Pageable pageable) {
        model.addAttribute("tags", tagService.findByAccount(account, pageable));
        return "tag/index";
    }

    @RequestMapping("/create")
    public String create(TagCreateForm tagCreateForm, BindingResult bindingResult, Model model) {
        return "tag/create";
    }

    @RequestMapping(value = "/create-save", method = RequestMethod.POST)
    public String createSave(@AuthenticationPrincipal Account account, @Validated TagCreateForm tagCreateForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return create(tagCreateForm, bindingResult, model);
        }
        try {
            Tag tag = new Tag();
            tag.setAccount(account);
            BeanUtils.copyProperties(tagCreateForm, tag);
            tagService.create(tag);
        } catch (Exception e) {
            log.warn("Failed to create tag for account {}", account.getId(), e);
            bindingResult.reject("error.task.create");
            return create(tagCreateForm, bindingResult, model);
        }
        return "redirect:/tag";
    }
}
