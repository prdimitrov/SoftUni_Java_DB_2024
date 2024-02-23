package com.example.advquerying.services;

import com.example.advquerying.entities.Shampoo;
import com.example.advquerying.entities.Size;
import com.example.advquerying.repositories.ShampooRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
@Service
public class ShampooServiceImpl implements ShampooService {

    ShampooRepository shampooRepository;

    public ShampooServiceImpl(ShampooRepository shampooRepository) {
        this.shampooRepository = shampooRepository;
    }

    @Override
    public List<Shampoo> findByBrand(String brand) {
        return shampooRepository.findByBrand(brand);
    }

    @Override
    public List<Shampoo> findByBrandAndSize(String brand, String size) {
        Size parsedSize = Size.valueOf(size.toUpperCase());
        return shampooRepository.findByBrandAndSize(brand, parsedSize);
    }

    @Override
    public List<Shampoo> findBySizeOrderById(String size) {
        Size parsedSize = Size.valueOf(size.toUpperCase());
        return shampooRepository.findBySizeOrderById(parsedSize);
    }

    @Override
    public List<Shampoo> findByIngredient(String ingredient) {
        return this.shampooRepository.findByIngredient(ingredient);
    }

    @Override
    public List<Shampoo> findByIngredients(List<String> ingredients) {
        return this.shampooRepository.findByIngredients(ingredients);
    }

    @Override
    public List<Shampoo> findBySizeOrLabelIdOrderByPrice(String size, long labelId) {
        Size parsedSize = Size.valueOf(size.toUpperCase());
        return this.shampooRepository.findBySizeOrLabelIdOrderByPrice(parsedSize, labelId);
    }

    @Override
    public List<Shampoo> findByPriceGreaterThanOrderByPriceDesc(BigDecimal price) {
        return this.shampooRepository.findByPriceGreaterThanOrderByPriceDesc(price);
    }


}
