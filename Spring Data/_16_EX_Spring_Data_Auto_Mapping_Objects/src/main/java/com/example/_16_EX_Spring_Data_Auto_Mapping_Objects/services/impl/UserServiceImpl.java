package com.example._16_EX_Spring_Data_Auto_Mapping_Objects.services.impl;

import com.example._16_EX_Spring_Data_Auto_Mapping_Objects.entities.users.RegisterDTO;
import com.example._16_EX_Spring_Data_Auto_Mapping_Objects.entities.users.User;
import com.example._16_EX_Spring_Data_Auto_Mapping_Objects.services.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public User register(RegisterDTO registerData) {
        return null;
    }

    @Override
    public User login() {
        return null;
    }

    @Override
    public void logout() {

    }
}
