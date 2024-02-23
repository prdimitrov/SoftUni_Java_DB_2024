package com.example.advquerying.services;

import com.example.advquerying.entities.Ingredient;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IngredientService {
    List<Ingredient> findByNameStartsWith(String name);

    List<Ingredient> findByNameInOrderByPriceAsc(List<String> names);

    void deleteByName(String name);

    void increasePriceByTenPercent();

    void updatePriceByNames(List<String> names);
}
