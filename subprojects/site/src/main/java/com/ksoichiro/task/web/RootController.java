package com.ksoichiro.task.web;

import com.ksoichiro.task.annotation.StandardController;
import org.springframework.web.bind.annotation.RequestMapping;

@StandardController("/")
public class RootController {
    @RequestMapping({"/", "home"})
    public String index() {
        return "redirect:/task/today";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }
}
