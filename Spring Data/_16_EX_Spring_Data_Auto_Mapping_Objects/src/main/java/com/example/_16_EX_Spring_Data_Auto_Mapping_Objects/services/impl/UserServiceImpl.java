package com.example._16_EX_Spring_Data_Auto_Mapping_Objects.services.impl;

import com.example._16_EX_Spring_Data_Auto_Mapping_Objects.entities.users.LoginDTO;
import com.example._16_EX_Spring_Data_Auto_Mapping_Objects.entities.users.RegisterDTO;
import com.example._16_EX_Spring_Data_Auto_Mapping_Objects.entities.users.User;
import com.example._16_EX_Spring_Data_Auto_Mapping_Objects.exceptions.UserNotLoggedInException;
import com.example._16_EX_Spring_Data_Auto_Mapping_Objects.repositories.UserRepository;
import com.example._16_EX_Spring_Data_Auto_Mapping_Objects.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private User currentUser;

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.currentUser = null;
        this.userRepository = userRepository;
    }

    @Override
    public User register(RegisterDTO registerData) {
        ModelMapper mapper = new ModelMapper();
        User toRegister = mapper.map(registerData, User.class);

        //Правим го, защото по условие е казано, че ако няма потребители и това е първия, той трябва
        //да стане администратор!
        long userCount = this.userRepository.count();
        if (userCount == 0) {
            toRegister.setAdmin(true);
        }


        return this.userRepository.save(toRegister);
    }

    @Override
    public Optional<User> login(LoginDTO loginData) {
        if (this.currentUser != null) {
            // Throw exception / return;
        }
        Optional<User> user = this.userRepository.findByEmailAndPassword(
                loginData.getEmail(), loginData.getPassword());

//        if (user.isPresent()) {
//            this.currentUser = user.get();
//        }
        user.ifPresent(value -> this.currentUser = value);

        return user;
    }

    public User getLoggeduser() {
        if (this.currentUser == null) {
            throw new UserNotLoggedInException();
        }
        return this.currentUser;
    }

    @Override
    public void logout() {
        //TODO: Cannot log out. No user was logged in.
        this.currentUser = null;
    }
}
