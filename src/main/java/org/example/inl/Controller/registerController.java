package org.example.inl.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class registerController {

    @GetMapping("/register")
    public String sayHello() {
        return "Hello, World!";
    }
}


