package bg.softuni._12_EX_Spring_Boot_Intro_Bookshop.repositories;

import bg.softuni._12_EX_Spring_Boot_Intro_Bookshop.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AuthorRepository
        extends JpaRepository<Author, Integer> {

    List<Author> findDisctinctByBooksReleaseDateBefore(LocalDate releaseDate);
    Author findByFirstNameAndLastName(String firstName, String lastName);

}
