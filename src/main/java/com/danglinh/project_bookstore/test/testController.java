package com.danglinh.project_bookstore.test;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class testController {

    @GetMapping("/")
    public String hello() {
        return "hello";
    }
}
