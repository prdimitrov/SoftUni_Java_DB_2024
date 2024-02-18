package bg.softuni._12_EX_Spring_Boot_Intro_Bookshop.repositories;

import bg.softuni._12_EX_Spring_Boot_Intro_Bookshop.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository
        extends JpaRepository<Category, Integer> {
}
