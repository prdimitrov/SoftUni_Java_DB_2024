package com.example.advquerying.repositories;

import com.example.advquerying.entities.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    List<Ingredient> findByNameStartsWith(String name);

    List<Ingredient> findByNameInOrderByPriceAsc(List<String> names);

    void deleteByName(String name);

    @Transactional
    @Modifying
    @Query("UPDATE Ingredient i SET i.price = i.price * 1.1")
    void increasePriceByTenPercent();

    @Transactional
    @Modifying
    @Query("UPDATE Ingredient i SET i.price = i.price * 1.1 WHERE i.name IN :names")
    void updatePriceByNames(List<String> names);
}
