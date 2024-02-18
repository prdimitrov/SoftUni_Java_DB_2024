package bg.softuni._12_EX_Spring_Boot_Intro_Bookshop.services;

import bg.softuni._12_EX_Spring_Boot_Intro_Bookshop.entities.Category;

import java.util.Set;

public interface CategoryService {
    Set<Category> getRandomCategories();
}
