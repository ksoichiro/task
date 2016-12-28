package com.ksoichiro.task.web;

import com.ksoichiro.task.annotation.LoginAccount;
import com.ksoichiro.task.annotation.StandardController;
import com.ksoichiro.task.domain.Account;
import com.ksoichiro.task.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@StandardController("/account")
@Slf4j
public class AccountController {
    @Autowired
    private AccountService accountService;

    @RequestMapping
    public String show(@LoginAccount Account account, Model model) {
        model.addAttribute("account", account);
        return "account/show";
    }
}
