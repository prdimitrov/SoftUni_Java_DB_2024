package com.example._12_EX_Spring_Data_Intro_User_System.services;

import com.example._12_EX_Spring_Data_Intro_User_System.entities.Town;
import com.example._12_EX_Spring_Data_Intro_User_System.repositories.TownRepository;
import org.springframework.stereotype.Service;

@Service
public class TownServiceImpl implements TownService {
    private final TownRepository townRepository;

    public TownServiceImpl(TownRepository townRepository) {
        this.townRepository = townRepository;
    }

    @Override
    public void addTown(String name, String country) {
        if (townRepository.findByTownName(name) != null) {
            throw new IllegalArgumentException("Town " + name + " already exists.");
        }
        Town town = new Town(name, country);
        townRepository.save(town);
    }
}
