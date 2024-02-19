package com.example._12_EX_Spring_Data_Intro_User_System;

import com.example._12_EX_Spring_Data_Intro_User_System.entities.User;
import com.example._12_EX_Spring_Data_Intro_User_System.repositories.TownRepository;
import com.example._12_EX_Spring_Data_Intro_User_System.repositories.UserRepository;
import com.example._12_EX_Spring_Data_Intro_User_System.services.TownServiceImpl;
import com.example._12_EX_Spring_Data_Intro_User_System.services.UserService;
import com.example._12_EX_Spring_Data_Intro_User_System.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
public class ConsoleRunner implements CommandLineRunner {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TownRepository townRepository;
    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private TownServiceImpl townService;

    public ConsoleRunner(UserRepository userRepository, UserServiceImpl userService, TownRepository townRepository, TownServiceImpl townService) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.townRepository = townRepository;
        this.townService = townService;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        System.out.println("What do you want to do?" +
                "\nPossible actions are userLogin, userRegister, userDelete.");
        Scanner scanner = new Scanner(System.in);
        if (scanner.equals("userLogin")) {
            //------------User login------------
            userLogin();
        } else if (scanner.equals("userRegister")) {
////------------Register User------------
            userRegister();
        } else if (scanner.equals("userDelete")) {
//------------Delete User------------
            userDelete();
        }

        //TODO: ЗА ГРАДОВЕТЕ!!
//------------...............------------
    }

    private void userRegister() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter username: ");
        String inputUsername = sc.nextLine();
        System.out.print("Enter password: ");
        String inputPassword = sc.nextLine();
        System.out.print("Enter email: ");
        String inputEmail = sc.nextLine();
        System.out.println("Enter age: ");
        int inputAge = Integer.parseInt(sc.nextLine());
        System.out.print("Enter town name: ");
        String inputTownName = sc.nextLine();
        System.out.print("Enter town country: ");
        String inputTownCountry = sc.nextLine();
        User user = new User(inputUsername, inputPassword, inputEmail, LocalDateTime.now(), inputAge);
        System.out.println(userService.register(user));
    }

    private void userLogin() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter username: ");
        String username = sc.nextLine();
        System.out.print("Enter password: ");
        String password = sc.nextLine();

        User user = new User(username, password);
        String loginMessage = userService.login(user);
        System.out.println(loginMessage);
    }

    private void userDelete() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter username: ");
        String usernameToDeleteInput = sc.nextLine();
        sc.close();
        System.out.println(userService.delete(usernameToDeleteInput));
    }
}

