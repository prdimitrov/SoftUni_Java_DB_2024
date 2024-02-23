package com.example.advquerying.repositories;

import com.example.advquerying.entities.Shampoo;
import com.example.advquerying.entities.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ShampooRepository extends JpaRepository<Shampoo, Long> {
    List<Shampoo> findByBrand(String brand);
    List<Shampoo> findByBrandAndSize(String brand, Size size);
    List<Shampoo> findBySizeOrderById(Size size);

    @Query(value = "SELECT s FROM Shampoo AS s " +
            "JOIN s.ingredients AS i " +
            "WHERE i.name = :name")
//    List<Shampoo> findByIngredient(String ingredient); Можем да използваме параметъра ingredient, директно след i.name,
    //като директно го подадем. WHERE i.name = :ingredient. Дргугия начин е:
    List<Shampoo> findByIngredient(@Param("name") String ingredient);

    @Query("SELECT s FROM Shampoo AS s " +
            "JOIN s.ingredients AS i " +
            "WHERE i.name IN :ingredients")
    List<Shampoo> findByIngredients(List<String> ingredients);

    List<Shampoo> findBySizeOrLabelIdOrderByPrice(Size parsedSize, long labelId);

    List<Shampoo> findByPriceGreaterThanOrderByPriceDesc(BigDecimal price);

    long countByPriceLessThan(BigDecimal price);

    @Query("SELECT s FROM Shampoo s WHERE SIZE(s.ingredients) < :number")
    List<Shampoo> findByIngredientsCountLessThan(int number);
}
