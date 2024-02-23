package com.example.advquerying;

import com.example.advquerying.entities.Ingredient;
import com.example.advquerying.entities.Shampoo;
import com.example.advquerying.services.IngredientService;
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
    @Autowired
    IngredientService ingredientService;

    public Main(ShampooService shampooService, IngredientService ingredientService) {
//        this.shampooRepository = shampooRepository;
        this.shampooService = shampooService;
        this.ingredientService = ingredientService;
    }

    @Override
    public void run(String... args) throws Exception {

//        ex_01_selectShampoosBySize();
//        ex_02_selectShampooBySizeOrLabel();
//        _ex_03_selectShampooByPriceHigherThanGivenPrice();
//        _ex_04_selectIngredientsByName();
//        _ex_05_selectIngredientsByNames();
//        _ex_06_countShampoosByPrice();
//        _ex_07_selectShampooByIngredients();
//        _ex_08_selectShampoosByIngredientsCount();
//        _ex_09_deleteIngredientsByName();
//        _ex_10_updateIngredientsPriceBy10Percent();
//        _ex_11_updateIngredientsByNames();
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

    private void _ex_04_selectIngredientsByName() {
    /*
    Create a method that selects all ingredients, which name starts with given letters
    Example:
    Input	Output
    M	    Macadamia Oil
            Mineral-Collagen
            Micro Crystals
    */
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter letters: ");
        String inputLetters = sc.nextLine();

        for (Ingredient ingredient : ingredientService.findByNameStartsWith(inputLetters)) {
            System.out.println(ingredient.getName());
        }
    }

    private void _ex_05_selectIngredientsByNames() {
            /*
            5.	Select Ingredients by Names
Create a method that selects all ingredients, which are contained in a given list. Sort the result ascending by price.
             */
        Scanner sc = new Scanner(System.in);
        //Input.
        String inputIngredient = sc.nextLine();

        //List of ingredients.
        List<String> ingredientsList = new ArrayList<>();

        while (!inputIngredient.isBlank()) {
            //Add an ingredient name to the list.
            ingredientsList.add(inputIngredient);

            //Input an ingredient again.
            inputIngredient = sc.nextLine();
        }

        for (Ingredient ingredient : ingredientService.findByNameInOrderByPriceAsc(ingredientsList)) {
            System.out.println(ingredient.getName());
        }
    }

    private void _ex_06_countShampoosByPrice() {
    /*
    Create a method that counts all shampoos with
    price lower than a given price.
     */
        Scanner sc = new Scanner(System.in);
        System.out.print("Input price: ");
        BigDecimal inputPrice = BigDecimal.valueOf(Double.parseDouble(sc.nextLine()));
        System.out.println("Count: " + shampooService.countByPriceLessThan(inputPrice));
    }

    private void _ex_07_selectShampooByIngredients() {
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

    private void _ex_08_selectShampoosByIngredientsCount() {
            /*
            Create a method that
            selects all shampoos with ingredients less than a given number.
             */
        Scanner sc = new Scanner(System.in);
        System.out.println("Input number: ");
        int inputNumber = Integer.parseInt(sc.nextLine());
        for (Shampoo shampoo : shampooService.findByIngredientsCountLessThan(inputNumber)) {
            System.out.println(shampoo.getBrand());
        }
    }

    private void _ex_09_deleteIngredientsByName() {
            /*
            Create a method that deletes ingredients by a given name. Use named query.
            */
        Scanner sc = new Scanner(System.in);
        String inputName = sc.nextLine();

        ingredientService.deleteByName(inputName);
    }

    private void _ex_10_updateIngredientsPriceBy10Percent() {
            /*
            10.	Update Ingredients by Price
            Create a method that increases the price of all ingredients by 10%. Use named query.
             */
        ingredientService.increasePriceByTenPercent();
    }

    private void _ex_11_updateIngredientsByNames() {
            /*
            11.	Update Ingredients by Names
Create a method that updates the price of all ingredients, which names are in a given list.
             */
        Scanner sc = new Scanner(System.in);
        String inputName = sc.nextLine();
        List<String> ingredientsNamesList = new ArrayList<>();

        while (!inputName.isBlank()) {
            ingredientsNamesList.add(inputName);
            inputName = sc.nextLine();
        }
        if (!ingredientsNamesList.isEmpty()) {
            ingredientService.updatePriceByNames(ingredientsNamesList);
            System.out.println("Ingredients prices updated successfully.");
        } else {
            System.out.println("No ingredients names provided.");
        }
    }
}
