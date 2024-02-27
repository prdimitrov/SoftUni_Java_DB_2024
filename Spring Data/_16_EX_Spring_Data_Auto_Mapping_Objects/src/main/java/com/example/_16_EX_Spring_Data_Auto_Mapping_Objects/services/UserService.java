package com.example._16_EX_Spring_Data_Auto_Mapping_Objects.services;

import com.example._16_EX_Spring_Data_Auto_Mapping_Objects.entities.users.RegisterDTO;
import com.example._16_EX_Spring_Data_Auto_Mapping_Objects.entities.users.User;

public interface UserService {
    User register(RegisterDTO registerData);

    User login();

    void logout();
}
