package org.example.inl.users.service;


import org.example.inl.users.model.User;
import org.example.inl.users.model.userDTO;
import org.example.inl.users.repository.userRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class userService {

    private final userRepo UserRepo;


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
    public boolean removeUser(String consoleEmailDelete) {


        User foundUser = UserRepo.findByEmail(consoleEmailDelete);
        if (foundUser != null) {
            UserRepo.delete(foundUser);
            return true;
        }return false;

    }


    // CREATE
    public void addUser(User createdUser) throws IllegalAccessException {

        if(createdUser.getEmail() == null || createdUser.getPassword() == null) {
            throw new IllegalArgumentException("Email and password fields cant be left empty");
        }
        UserRepo.save(createdUser);
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
        return foundUser.getPassword().equals(loginUser.getPassword());
    }

}
