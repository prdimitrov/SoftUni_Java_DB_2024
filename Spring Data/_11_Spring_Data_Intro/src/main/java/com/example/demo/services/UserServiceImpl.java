package com.example.demo.services;

import com.example.demo.models.Account;
import com.example.demo.models.User;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired //Начин с който спринг работи и знае как да добавя зависимоситте на този клас
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void register(String username, int age) {
        // Validate username + age
        if (username.isBlank() || age < 18) {
            throw new RuntimeException("Validation failed");
        }
        // Check username is unique
//        User byUsername = this.userRepository.findByUsername(username);
//        if (byUsername != null) {
//            throw new RuntimeException("Username already in use");
//        }
        Optional<User> byUsername = this.userRepository.findByUsername(username);
        if (byUsername.isPresent()) {
            throw new RuntimeException("Username already in use");
        }

        // Add default account?
        Account account = new Account();
        User user = new User(username, age, account);
        // Save user
        this.userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return this.userRepository.findByUsername(username).get();
    }
}
