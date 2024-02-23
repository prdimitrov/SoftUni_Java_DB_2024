package com.example.advquerying;

import com.example.advquerying.entities.Shampoo;
import com.example.advquerying.services.ShampooService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
public class Main implements CommandLineRunner {
    //    ShampooRepository shampooRepository;
    //Направихме Service и няма да използваме вече директно ShampooRepository.
    @Autowired
    ShampooService shampooService;

    public Main(ShampooService shampooService) {
//        this.shampooRepository = shampooRepository;
        this.shampooService = shampooService;
    }

    @Override
    public void run(String... args) throws Exception {

//        ex_01_selectShampoosBySize();
//        ex_07_selectShampooByIngredients();
//        ex_02_selectShampooBySizeOrLabel();
//        _ex_03_selectShampooByPriceHigherThanGivenPrice();
        //TODO: ДОВЪРШИ ОСТАНАЛИТЕ ЗАДАЧИ!!!!!
    }

    private void ex_01_selectShampoosBySize() {
//Create a method that selects all shampoos with a given size, ordered by shampoo id.
        Scanner sc = new Scanner(System.in);
        String size = sc.nextLine();
        for (Shampoo shampoo : this.shampooService.findBySizeOrderById(size)) {
            System.out.printf("%s %s %.2flv.%n",
                    shampoo.getBrand(), shampoo.getSize(), shampoo.getPrice());
        }
    }

    private void ex_02_selectShampooBySizeOrLabel() {
//Create a method that selects all shampoos with a given size or label id.
// Sort the result ascending by price.
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter size: ");
        String size = sc.nextLine();
        System.out.print("Enter label id: ");
        long labelId = Long.parseLong(sc.nextLine());

        for (Shampoo shampoo : this.shampooService.findBySizeOrLabelIdOrderByPrice(size, labelId)) {
            System.out.printf("%s %s %.2flv.%n",
                    shampoo.getBrand(),
                    shampoo.getSize(),
                    shampoo.getPrice());
        }
    }

    private void _ex_03_selectShampooByPriceHigherThanGivenPrice() {
//Create a method that selects all shampoos, which price is higher than a given price. Sort the result descending by price.
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter price: ");
        BigDecimal price = BigDecimal.valueOf(Double.parseDouble(sc.nextLine()));

        for (Shampoo shampoo : this.shampooService.findByPriceGreaterThanOrderByPriceDesc(price)) {
            System.out.printf("%s %s %.2flv.%n",
                    shampoo.getBrand(),
                    shampoo.getSize(),
                    shampoo.getPrice());
        }
    }

    private void ex_07_selectShampooByIngredients() {
        //Create a method that selects all shampoos with ingredients included in a given list.
        Scanner sc = new Scanner(System.in);
        List<String> ingredientsList = new ArrayList<>();
        String ingredientInput = sc.nextLine();
        while (!ingredientInput.isBlank()) {
            ingredientsList.add(ingredientInput);
            ingredientInput = sc.nextLine();
        }
        for (Shampoo shampoo : shampooService.findByIngredients(ingredientsList)) {
            System.out.println(shampoo.getBrand());
        }
    }
}
