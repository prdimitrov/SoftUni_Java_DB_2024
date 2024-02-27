package com.example._16_EX_Spring_Data_Auto_Mapping_Objects;

import com.example._16_EX_Spring_Data_Auto_Mapping_Objects.exceptions.UserNotLoggedInException;
import com.example._16_EX_Spring_Data_Auto_Mapping_Objects.exceptions.ValidationException;
import com.example._16_EX_Spring_Data_Auto_Mapping_Objects.services.ExecutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ConsoleRunner implements CommandLineRunner {


    private final ExecutorService executorService;

    @Autowired
    public ConsoleRunner(ExecutorService executorService) {
        this.executorService = executorService;
    }



    @Override
    public void run(String... args) {

        Scanner scanner = new Scanner(System.in);
        String command = scanner.nextLine();


            String result;
            try {
                result = executorService.execute(command);
            } catch (ValidationException | UserNotLoggedInException ex) {
                result = ex.getMessage();
            }
            System.out.println(result);
        }
    }
