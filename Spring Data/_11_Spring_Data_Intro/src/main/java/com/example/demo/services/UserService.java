package com.example.demo.services;

import com.example.demo.models.User;

public interface UserService {
//    void register(User user); На Банкин не му харесва това
    void register(String username, int age);

    User findByUsername(String first);
}
