package org.example.inl.users.controller;

import org.example.inl.users.model.User;
import org.example.inl.users.model.userDTO;
import org.example.inl.users.service.userService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class userController {

    private final userService userService;
    private final userDTO userDTO = new userDTO();

    public userController(userService userService ) {
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
    public ResponseEntity<?> postUser(@RequestBody userDTO consoleUser) throws IllegalAccessException {

        User newUser = new User();

            newUser.setEmail(consoleUser.getEmail());
            newUser.setPassword(consoleUser.getPassword());
            userService.addUser(newUser);
            return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);

    }


    @DeleteMapping ("/remove/{id}")
    public String removeUser(@PathVariable Long id) {
        return "Hello, World!";
    }

    @PatchMapping("/update{id}/")
    public String patchUser(@PathVariable Long id) {
        return "Hello, World!";
    }



    // Error management

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> illegalArgumentExceptionHandler(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

}


