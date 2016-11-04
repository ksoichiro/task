package com.ksoichiro.task.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/demo")
@Slf4j
public class DemoController {
    @RequestMapping("/style")
    public String style() {
        return "demo/style";
    }
}
