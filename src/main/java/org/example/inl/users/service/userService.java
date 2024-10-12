package org.example.inl.users.service;


import org.example.inl.users.model.User;
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
    public void removeUser(User user) {
        UserRepo.delete(user);
    }


    // CREATE
    public void addUser(User createdUser) {
        UserRepo.save(createdUser);
    }


    // PATCH/PUT
    public void patchUser(User user) {
        UserRepo.save(user);
    }


}
