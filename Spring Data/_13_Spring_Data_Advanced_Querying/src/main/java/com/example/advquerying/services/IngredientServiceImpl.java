package com.example.advquerying.services;

import com.example.advquerying.entities.Ingredient;
import com.example.advquerying.repositories.IngredientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientServiceImpl implements IngredientService {
    IngredientRepository ingredientRepository;

    public IngredientServiceImpl(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public List<Ingredient> findByNameStartsWith(String name) {

        return this.ingredientRepository.findByNameStartsWith(name);
    }

    @Override
    public List<Ingredient> findByNameInOrderByPriceAsc(List<String> names) {

        return this.ingredientRepository.findByNameInOrderByPriceAsc(names);
    }

    @Override
    public void deleteByName(String name) {

        this.ingredientRepository.deleteByName(name);
    }

    @Override
    public void increasePriceByTenPercent() {

        this.ingredientRepository.increasePriceByTenPercent();
    }

    @Override
    public void updatePriceByNames(List<String> names) {

        this.ingredientRepository.updatePriceByNames(names);
    }
}
