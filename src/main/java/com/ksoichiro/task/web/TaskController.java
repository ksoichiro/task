package com.ksoichiro.task.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TaskController {
    @RequestMapping
    public String index() {
        return "task/index";
    }
}
