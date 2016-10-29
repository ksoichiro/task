package com.ksoichiro.task.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
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
