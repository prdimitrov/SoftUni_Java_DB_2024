package com.example._16_EX_Spring_Data_Auto_Mapping_Objects.services;

import com.example._16_EX_Spring_Data_Auto_Mapping_Objects.entities.users.LoginDTO;
import com.example._16_EX_Spring_Data_Auto_Mapping_Objects.entities.users.RegisterDTO;
import com.example._16_EX_Spring_Data_Auto_Mapping_Objects.entities.users.User;

import java.util.Optional;

public interface UserService {
    User register(RegisterDTO registerData);

    Optional<User> login(LoginDTO loginData);

    User getLoggeduser();

    void logout();
}
