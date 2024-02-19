package com.example._12_EX_Spring_Data_Intro_User_System.services;

import com.example._12_EX_Spring_Data_Intro_User_System.entities.User;

public interface UserService {
    String register(User user);

    String login(User user);

    String delete(String username);

}
