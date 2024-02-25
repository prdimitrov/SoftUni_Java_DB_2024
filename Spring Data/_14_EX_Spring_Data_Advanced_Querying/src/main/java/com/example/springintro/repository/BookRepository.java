package com.example.springintro.repository;

import com.example.springintro.model.entity.AgeRestriction;
import com.example.springintro.model.entity.Book;
import com.example.springintro.model.entity.BookSummary;
import com.example.springintro.model.entity.EditionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAllByReleaseDateAfter(LocalDate releaseDateAfter);

    List<Book> findAllByReleaseDateBefore(LocalDate releaseDateBefore);

    List<Book> findAllByAuthor_FirstNameAndAuthor_LastNameOrderByReleaseDateDescTitle(String author_firstName, String author_lastName);

//    List<Book> findByAgeRestriction(AgeRestriction restriction);

    @Query("SELECT b.title FROM Book b WHERE ageRestriction = :ageRestriction")
    List<String> findByAgeRestriction(@Param("ageRestriction") AgeRestriction restriction);

    List<Book> findByEditionTypeAndCopiesLessThan(EditionType type, int copies);

    List<Book> findByPriceLessThanOrPriceGreaterThan(BigDecimal lower, BigDecimal higher);

    @Query("SELECT b.title FROM Book b WHERE YEAR(b.releaseDate) NOT LIKE :inputYear")
    List<String> findByReleaseDateYearNot(int inputYear);


    List<Book> findByTitleContaining(String search);

    List<Book> findByAuthorLastNameStartingWith(String search);

    @Query("SELECT COUNT(b) FROM Book b WHERE LENGTH(b.title) > :length")
    int countBooksWithTitleLongerThan(int length);

    @Query("SELECT b.title AS title, " +
            "b.editionType AS editionType, " +
            "b.ageRestriction AS ageRestriction, " +
            "b.price AS price " +
            "FROM Book b " +
            "WHERE b.title = :title")
    BookSummary findSummaryForTitle(String title);

    @Modifying
    @Transactional
    @Query("UPDATE Book b SET b.copies = b.copies + :amount " +
            "WHERE b.releaseDate > :date")
    int addCopiesToBooksAfter(LocalDate date, int amount);

    @Transactional
    int deleteByCopiesLessThan(int amount);
}
