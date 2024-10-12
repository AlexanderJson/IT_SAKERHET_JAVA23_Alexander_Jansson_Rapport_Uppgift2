package org.example.inl.users.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class homeController {

    @GetMapping("/home")
    public String sayHello() {
        return "Hello, World!";
    }
}

