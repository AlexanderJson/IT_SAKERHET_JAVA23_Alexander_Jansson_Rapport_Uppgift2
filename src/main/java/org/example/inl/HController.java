package org.example.inl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HController {

    @Autowired
    userService service;

    @GetMapping("/users")
    public List<User> sayHello() {

        return service.getAllUsers();
    }
}
