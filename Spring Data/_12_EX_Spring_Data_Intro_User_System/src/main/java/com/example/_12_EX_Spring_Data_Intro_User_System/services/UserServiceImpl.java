package com.example._12_EX_Spring_Data_Intro_User_System.services;

import com.example._12_EX_Spring_Data_Intro_User_System.entities.User;
import com.example._12_EX_Spring_Data_Intro_User_System.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public String register(User user) {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new IllegalArgumentException("User " + user.getUsername() + " already exists.");
        }
        user.setRegisteredOn(LocalDateTime.now());
        userRepository.save(user);
        return "User " + user.getUsername() + " successfully registered.";
    }

    @Override
    public String login(User user) {
        User foundUser = userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
        if (foundUser != null && !foundUser.isDeleted()) {
            String userOutput = "User " + user.getUsername() + " logged in.";
            user.setLastTimeLoggedIn(LocalDateTime.now());
            return userOutput;
        } else {
            throw new IllegalArgumentException("User " + user.getUsername() + " failed to login.");
        }
    }

    @Override
    public String delete(String username) {
        User foundUser = userRepository.findByUsername(username);
        if (foundUser == null) {
            throw new IllegalArgumentException("User with username: " + username + " not found.");
        }
        if (foundUser.isDeleted()) {
            throw new IllegalArgumentException("User with username: " + username + " already deleted.");
        }
        foundUser.setDeleted(true);
        return "User " + username + " successfully deleted.";
    }


}
