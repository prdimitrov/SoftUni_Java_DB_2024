package com.example._12_EX_Spring_Data_Intro_User_System.entities;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Column(name = "registered_on")
    private LocalDateTime registeredOn;

    @Column(name = "last_time_logged_in")
    private LocalDateTime lastTimeLoggedIn;

    @Column(length = 120)
    private int age;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "town_id")
    private Town town;
    public User() {
    }

    //User without town
    public User(String username, String password, String email, LocalDateTime registeredOn, int age) {
        this.setUsername(username);
        this.setPassword(password);
        this.setEmail(email);
        this.registeredOn = LocalDateTime.now();
        this.age = age;
    }
    //User with town
    public User(String username, String password, String email, LocalDateTime registeredOn, int age, Town town) {
        this.setUsername(username);
        this.setPassword(password);
        this.setEmail(email);
        this.registeredOn = LocalDateTime.now();
        this.age = age;
        this.town = town;
    }


    public User(String username, String password) {
        this.setUsername(username);
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        //•	username – Text with a length between 4 and 30 symbols. Required.
        if (isValidUsername(username)) {
            this.username = username;
        } else {
            throw new IllegalArgumentException("Invalid username length.");
        }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        /*
•	password – Required field. Text with length between 6 and 50 symbols. Should contain at least:
o	1 lowercase letter
o	1 uppercase letter
o	1 digit
o	1 special symbol (!, @, #, $, %, ^, &, *, (, ), _, +, <, >, ?)
         */
        if (isPasswordValid(password)) {
            this.password = password;
        } else {
            throw new IllegalArgumentException("Invalid password format!");
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        /*
•	email – Required field. Text that is considered to be in format <user>@<host> where:
o	<user> is a sequence of letters and digits, where '.', '-' and '_' can appear between them (they cannot appear at the beginning or at the end of the sequence).
o	<host> is a sequence of at least two words, separated by dots '.' (dots cannot appear at the beginning or at the end of the sequence)
         */
        if (isEmailValid(email)) {
            this.email = email;
        } else {
            throw new IllegalArgumentException("Invalid email.");
        }
    }

    public LocalDateTime getRegisteredOn() {
        return registeredOn;
    }

    public void setRegisteredOn(LocalDateTime registeredOn) {
        this.registeredOn = registeredOn;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }



    public LocalDateTime getLastTimeLoggedIn() {
        return lastTimeLoggedIn;
    }

    public void setLastTimeLoggedIn(LocalDateTime lastTimeLoggedIn) {
        this.lastTimeLoggedIn = lastTimeLoggedIn;
    }

    private boolean isPasswordValid(String password) {
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&*()_+<>?]).+$";
        return password.matches(regex);
    }

    private boolean isValidUsername(String username) {
        return username.length() >= 4 && username.length() <= 30;
    }

    private boolean isEmailValid(String email) {
        String regex = "^[a-zA-Z0-9]+([._-]?[a-zA-Z0-9]+)*@[a-zA-Z0-9]+(\\.[a-zA-Z0-9]+)+$";
        return email.matches(regex);
    }

    public Town getTown() {
        return town;
    }

    public void setTown(Town town) {
        this.town = town;
    }
}
