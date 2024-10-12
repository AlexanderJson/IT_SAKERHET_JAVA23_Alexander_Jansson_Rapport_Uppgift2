package org.example.inl.users.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class userController {

    @GetMapping("/get/{id}")
    public String getUser(@PathVariable Long id) {
        return "Hello, World!";
    }

    @GetMapping("/get/")
    public String getUsers() {
        return "Hello, World!";
    }

    @PostMapping ("/register")
    public String postUser() {
        return "Hello, World!";
    }


    @DeleteMapping ("/remove/{id}")
    public String removeUser(@PathVariable Long id) {
        return "Hello, World!";
    }

    @PatchMapping("/update{id}/{")
    public String patchUser(@PathVariable Long id) {
        return "Hello, World!";
    }
}


