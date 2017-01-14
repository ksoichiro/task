package com.ksoichiro.task.web;

import com.ksoichiro.task.annotation.StandardController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;

@StandardController("/demo")
@Slf4j
public class DemoController {
    @RequestMapping("/style")
    public String style() {
        return "demo/style";
    }
}
