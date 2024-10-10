package org.example.inl;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class userService {

    private final userRepository UserRepository;


    public userService(userRepository userRepository) {
        UserRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return UserRepository.findAll();
    }


}
