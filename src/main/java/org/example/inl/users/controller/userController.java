package org.example.inl.users.controller;

import org.example.inl.Security.JWT.JwTUtil;
import org.example.inl.users.model.User;
import org.example.inl.users.model.userDTO;
import org.example.inl.users.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
@RequestMapping("/users")
public class userController {

    private final userService userService;
    private final userDTO userDTO = new userDTO();
    @Autowired
    private JwTUtil jwTUtil;
    public userController(userService userService ) {
        this.userService = userService;
    }




    @PostMapping ("/register")
    public ResponseEntity<?> postUser(@RequestBody userDTO consoleUser) throws IllegalAccessException, NoSuchAlgorithmException {
                userService.addUser(consoleUser);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }


    @PostMapping ("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody userDTO consoleUserLogin) throws IllegalAccessException {

        boolean authenticated = userService.authenticateUser(consoleUserLogin);
        if (authenticated) {
            return ResponseEntity.ok("Login successful" + consoleUserLogin.getEmail());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect login details");
        }
    }



    @DeleteMapping ("/remove")
    public ResponseEntity<?> removeUser(@RequestHeader("Authorization") String token)  {

        String email = jwTUtil.extractedUsername(token.substring(7));
       boolean deleted = userService.removeUser(email);

       if (deleted) {
           return ResponseEntity.ok("User deleted successfully");
       }
       else {
           return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Failed to delete: " + email);
       }

    }

    @PatchMapping("/update{id}/")
    public String patchUser(@PathVariable Long id) {
        return "Inte implementerad!";
    }



}


