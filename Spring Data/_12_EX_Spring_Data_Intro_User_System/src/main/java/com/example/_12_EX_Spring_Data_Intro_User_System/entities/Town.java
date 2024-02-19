package com.example._12_EX_Spring_Data_Intro_User_System.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "towns")
public class Town {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Basic
    private String name;
    @Basic
    private String country;
    /*
    User should have born town and currently living in town.
    The town has a name and country, based on its location.
     */
    @OneToMany(mappedBy = "town")
    private List<User> users;

    public Town() {
    }

    public Town(String name, String country, List<User> users) {
        this.name = name;
        this.country = country;
        this.users = new ArrayList<>();
    }

    public Town(String name, String country) {
        this.name = name;
        this.country = country;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
