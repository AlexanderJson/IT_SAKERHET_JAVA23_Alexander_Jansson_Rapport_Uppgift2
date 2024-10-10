package org.example.inl;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HController {

    @GetMapping("/")
    public String sayHello() {
        return "Hello, World!";
    }
}
