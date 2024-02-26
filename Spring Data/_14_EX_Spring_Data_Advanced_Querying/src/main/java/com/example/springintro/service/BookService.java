package com.example.springintro.service;

import com.example.springintro.model.entity.Book;
import com.example.springintro.model.entity.BookSummary;
import com.example.springintro.model.entity.EditionType;

import java.io.IOException;
import java.util.List;

public interface BookService {
    void seedBooks() throws IOException;

    List<Book> findAllBooksAfterYear(int year);

    List<String> findAllAuthorsWithBooksWithReleaseDateBeforeYear(int year);

    List<String> findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(String firstName, String lastName);

    List<String> findAllTitlesByAgeRestriction(String ageRestriction);

    List<String> findAllTitlesByEditionAndCopies(EditionType type, int copies);

    List<Book> findByPriceLessThanOrPriceGreaterThan(float minPrice, float maxPrice);

    List<String> findNotReleasedIn(int inputYear);

    List<Book> findBooksReleasedBefore(String inputDate);

    List<String> findAllTitlesContaining(String search);

    List<Book> findByAuthorLastNameStartingWith(String search);

    int countBooksWithTitleLongerThan(int length);


    BookSummary getInformationForTitle(String title);

    int addCopiesToBooksAfter(String date, int amount);

    int deleteWithCopiesLessThan(int amount);
}
