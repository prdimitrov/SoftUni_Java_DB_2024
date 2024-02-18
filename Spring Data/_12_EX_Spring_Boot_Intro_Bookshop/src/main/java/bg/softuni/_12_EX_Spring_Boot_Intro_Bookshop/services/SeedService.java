package bg.softuni._12_EX_Spring_Boot_Intro_Bookshop.services;

import java.io.IOException;

public interface SeedService {
    void seedAuthors() throws IOException;

    void seedCategories() throws IOException;

    void seedBooks() throws IOException;

    default void seedAll () throws IOException {
        seedAuthors();
        seedCategories();
        seedBooks();
    }
}
