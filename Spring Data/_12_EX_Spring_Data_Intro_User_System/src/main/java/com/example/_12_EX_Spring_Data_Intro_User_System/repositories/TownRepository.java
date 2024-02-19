package com.example._12_EX_Spring_Data_Intro_User_System.repositories;

import com.example._12_EX_Spring_Data_Intro_User_System.entities.Town;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TownRepository extends JpaRepository<Town, Integer> {
    Town findByTownName(String name);
}
