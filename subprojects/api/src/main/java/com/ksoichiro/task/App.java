package com.ksoichiro.task;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public abstract class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
