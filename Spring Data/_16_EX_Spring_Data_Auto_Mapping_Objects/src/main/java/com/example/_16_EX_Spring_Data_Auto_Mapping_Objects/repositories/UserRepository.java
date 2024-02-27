package com.example._16_EX_Spring_Data_Auto_Mapping_Objects.repositories;

import com.example._16_EX_Spring_Data_Auto_Mapping_Objects.entities.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {



}
