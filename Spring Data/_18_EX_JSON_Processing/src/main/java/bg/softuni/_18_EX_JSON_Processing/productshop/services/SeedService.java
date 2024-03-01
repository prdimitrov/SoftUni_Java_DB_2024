package bg.softuni._18_EX_JSON_Processing.productshop.services;

import java.io.FileNotFoundException;

public interface SeedService {
    void seedUsers() throws FileNotFoundException;

    void seedCategories() throws FileNotFoundException;

    void seedProducts() throws FileNotFoundException;

    default void seedAll() throws FileNotFoundException {
        seedUsers();
        seedCategories();
        seedProducts();
    }
}
