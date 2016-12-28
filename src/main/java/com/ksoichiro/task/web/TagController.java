package com.ksoichiro.task.web;

import com.ksoichiro.task.annotation.Post;
import com.ksoichiro.task.annotation.StandardController;
import com.ksoichiro.task.domain.Account;
import com.ksoichiro.task.domain.Tag;
import com.ksoichiro.task.exception.DuplicateTagNameException;
import com.ksoichiro.task.form.TagCreateForm;
import com.ksoichiro.task.form.TagUpdateForm;
import com.ksoichiro.task.service.TagService;
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

@StandardController("/tag")
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

    @Post("/create-save")
    public String createSave(@AuthenticationPrincipal Account account, @Validated TagCreateForm tagCreateForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return create(tagCreateForm, bindingResult, model);
        }
        try {
            tagService.create(tagCreateForm.toDTO(account));
        } catch (DuplicateTagNameException e) {
            log.warn("Failed to create tag for account {}", account.getId(), e);
            bindingResult.reject("error.tag.name.duplicate");
            return create(tagCreateForm, bindingResult, model);
        } catch (Exception e) {
            log.warn("Failed to create tag for account {}", account.getId(), e);
            bindingResult.reject("error.tag.create");
            return create(tagCreateForm, bindingResult, model);
        }
        return "redirect:/tag";
    }

    @RequestMapping("/update/{id}")
    public String update(@PathVariable Integer id,
                         @AuthenticationPrincipal Account account,
                         TagUpdateForm tagUpdateForm, BindingResult bindingResult, Model model) {
        tagUpdateForm.copyFrom(tagService.findByIdAndAccount(id, account));
        return "tag/update";
    }

    @Post("/update-save")
    public String updateSave(@AuthenticationPrincipal Account account,
                             @Validated TagUpdateForm tagUpdateForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            if (StringUtils.isEmpty(tagUpdateForm.getId())) {
                // Can't decide the tag to update
                return "redirect:/tag";
            }
            return update(tagUpdateForm.getId(), account, tagUpdateForm, bindingResult, model);
        }
        try {
            tagService.update(tagUpdateForm.toDTO(account));
        } catch (DuplicateTagNameException e) {
            log.warn("Failed to update tag for account {}", account.getId(), e);
            bindingResult.reject("error.tag.name.duplicate");
            return update(tagUpdateForm.getId(), account, tagUpdateForm, bindingResult, model);
        } catch (Exception e) {
            log.warn("Failed to update tag for account {}", account.getId(), e);
            bindingResult.reject("error.tag.update");
            return update(tagUpdateForm.getId(), account, tagUpdateForm, bindingResult, model);
        }
        return "redirect:/tag";
    }
}
