package com.danglinh.project_bookstore.test;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

@RestController
public class testController {

    @GetMapping("/")
    public String hello() {
        return "hello";
    }

    @GetMapping("/get-avatar")
    public File getAvatar() {
        return new File("D:/fullstack/upload/folder/test.png");
    }
}
