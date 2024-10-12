package org.example.inl.users.controller;

import org.example.inl.users.model.User;
import org.example.inl.users.model.userDTO;
import org.example.inl.users.service.userService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
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


    @PostMapping ("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody userDTO consoleUserLogin) throws IllegalAccessException {

        boolean authenticated = userService.authenticateUser(consoleUserLogin);
        if (authenticated) {
            // to-do säkerhetsgrejwer här
            return ResponseEntity.ok("Login successful" + consoleUserLogin.getEmail());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect login details");
        }
    }



    @DeleteMapping ("/remove/{email}")
    public String removeUser(@PathVariable("email") String consoleEmailDelete) {

        userService.removeUser(consoleEmailDelete);

        return "USER DELETED: " + consoleEmailDelete;
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


