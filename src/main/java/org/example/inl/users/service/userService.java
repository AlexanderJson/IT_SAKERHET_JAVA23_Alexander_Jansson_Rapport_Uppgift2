package org.example.inl.users.service;


import org.example.inl.users.model.User;
import org.example.inl.users.model.userDTO;
import org.example.inl.users.repository.userRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class userService {

    @Autowired
    private final userRepo UserRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;


    public userService(userRepo userRepo) {
        UserRepo = userRepo;
    }


    // GET
    public List<User> getAllUsers() {
        return UserRepo.findAll();
    }

    public Optional<User> findUserByID(Long id) {
        return UserRepo.findById(id);
    }


    // REMOVE
    public boolean removeUser(String email) {


        User foundUser = UserRepo.findByEmail(email);
        if (foundUser != null) {
            UserRepo.delete(foundUser);
            return true;
        }return false;

    }

    public User findByEmail(String email) {

        return UserRepo.findByEmail(email);
    }

    // CREATE
    public void addUser(userDTO createdUser) throws IllegalAccessException {

        if(createdUser.getEmail() == null || createdUser.getPassword() == null) {
            throw new IllegalArgumentException("Email and password fields cant be left empty");
        }
        String hashedPassword = passwordEncoder.encode(createdUser.getPassword());
        User newUser = new User();
        newUser.setEmail(createdUser.getEmail());
        newUser.setPassword(hashedPassword);

        UserRepo.save(newUser);
    }


    // PATCH/PUT
    public void patchUser(User user) {
        UserRepo.save(user);
    }


    // AUTHENTICATE
    public boolean authenticateUser(userDTO loginUser) throws IllegalAccessException {


        if(loginUser.getEmail() == null || loginUser.getPassword() == null) {
            throw new IllegalArgumentException("SERVICE: Email and password fields cant be left empty");
        }


        User foundUser = UserRepo.findByEmail(loginUser.getEmail());
        return passwordEncoder.matches(loginUser.getPassword(), foundUser.getPassword());
    }

}
