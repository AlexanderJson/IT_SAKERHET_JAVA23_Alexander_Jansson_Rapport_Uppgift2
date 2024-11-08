package org.example.inl.users.service;


import org.example.inl.Security.Encryption.AESEncryption;
import org.example.inl.users.model.User;
import org.example.inl.users.model.userDTO;
import org.example.inl.users.repository.userRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class userService {

    @Autowired
    private final userRepo UserRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AESEncryption aesEncryption;

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
    public void addUser(userDTO createdUser) throws IllegalAccessException, NoSuchAlgorithmException {

        if(createdUser.getEmail() == null || createdUser.getPassword() == null) {
            throw new IllegalArgumentException("Email and password fields cant be left empty");
        }
        String hashedPassword = passwordEncoder.encode(createdUser.getPassword());
        System.out.println("Hashed Password: " + hashedPassword);
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128);
        SecretKey secretKey = keyGenerator.generateKey();
        String base64Key = Base64.getEncoder().encodeToString(secretKey.getEncoded());

        User newUser = new User();
        newUser.setEmail(createdUser.getEmail());
        newUser.setPassword(hashedPassword);
        newUser.setAesKey(base64Key);

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
