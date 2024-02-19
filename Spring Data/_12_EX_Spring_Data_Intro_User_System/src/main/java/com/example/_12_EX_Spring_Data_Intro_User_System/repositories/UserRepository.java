package com.example._12_EX_Spring_Data_Intro_User_System.repositories;

import com.example._12_EX_Spring_Data_Intro_User_System.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsernameAndPassword(String username, String password);
    User findByUsername(String username);
}
