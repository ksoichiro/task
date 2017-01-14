package com.ksoichiro.task.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test/problematic")
public class ProblematicController {
    @RequestMapping
    public String index() {
        throw new RuntimeException("Test");
    }
}
