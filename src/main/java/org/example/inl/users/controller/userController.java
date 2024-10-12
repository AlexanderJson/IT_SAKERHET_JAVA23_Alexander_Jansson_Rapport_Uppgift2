package org.example.inl.users.controller;

import org.example.inl.users.model.User;
import org.example.inl.users.service.userService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class userController {

    private final org.example.inl.users.service.userService userService;

    public userController(userService userService) {
        this.userService = userService;
    }

    @GetMapping("/get/{id}")
    public String getUser(@PathVariable Long id) {
        return "Hello, World!";
    }

    @GetMapping("/get/")
    public String getUsers() {
        return "Hello, World!";
    }

    @PostMapping ("/register")
    public ResponseEntity<User> postUser(@RequestBody User consoleUser) {

        User newUser = new User();
        newUser.setEmail(consoleUser.getEmail());
        newUser.setPassword(consoleUser.getPassword());
        userService.addUser(newUser);

        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }


    @DeleteMapping ("/remove/{id}")
    public String removeUser(@PathVariable Long id) {
        return "Hello, World!";
    }

    @PatchMapping("/update{id}/")
    public String patchUser(@PathVariable Long id) {
        return "Hello, World!";
    }
}


