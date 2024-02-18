package bg.softuni._12_EX_Spring_Boot_Intro_Bookshop;

import bg.softuni._12_EX_Spring_Boot_Intro_Bookshop.entities.Author;
import bg.softuni._12_EX_Spring_Boot_Intro_Bookshop.entities.Book;
import bg.softuni._12_EX_Spring_Boot_Intro_Bookshop.repositories.AuthorRepository;
import bg.softuni._12_EX_Spring_Boot_Intro_Bookshop.repositories.BookRepository;
import bg.softuni._12_EX_Spring_Boot_Intro_Bookshop.services.SeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ConsoleRunner implements CommandLineRunner {

    //    @Autowired може и тук да сложим Autowired'a
    private final SeedService seedService;
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @Autowired
    public ConsoleRunner(SeedService seedService,
                         BookRepository bookRepository, AuthorRepository authorRepository) {
        this.seedService = seedService;
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public void run(String... args) throws Exception {
//        System.out.println("Working...");
//        this.seedService.seedAuthors();
//        this.seedService.seedCategories();
//        this.seedService.seedAll();

        //Write Queries
//        1.	Get all books after the year 2000. Print only their titles.
//        this._01_booksAfter2000();
//        2.	Get all authors with at least one book with a release date before 1990. Print their first name and last name.
//        this._02_allAuthorsWithBookBefore1990();
//        3.	Get all authors, ordered by the number of their books (descending). Print their first name, last name and book count.
//        this._03_allAuthorsOrderedByBookCount();
//        4.	Get all books from author George Powell, ordered by their release date (descending), then by book title (ascending). Print the book's title, release date and copies.
//        this._04_findAuthorByFirstNameAndLastName();

    }

    private void _04_findAuthorByFirstNameAndLastName() {
        Author author = this.authorRepository.findByFirstNameAndLastName("George", "Powell");

        System.out.printf("All books from %s %s:%n", author.getFirstName(), author.getLastName());

        List<Book> sortedBooks = author.getBooks().stream()
                .sorted(Comparator.comparing(Book::getReleaseDate).reversed()
                        .thenComparing(Book::getTitle))
                .collect(Collectors.toList());

        sortedBooks.forEach(b -> System.out.printf("\tTitle: %s, Relase Date: %s, Copies: %d%n"
                , b.getTitle(), b.getReleaseDate(), b.getCopies()));
    }


    private void _03_allAuthorsOrderedByBookCount() {
        List<Author> authors = this.authorRepository.findAll();

        authors.stream()
                .sorted((l, r) -> r.getBooks().size() - l.getBooks().size())
                .forEach(a -> {
                    System.out.printf("%s %s -> %d%n",
                            a.getFirstName(),
                            a.getLastName(),
                            a.getBooks().size());
                });
    }


    private void _02_allAuthorsWithBookBefore1990() {
        LocalDate year1990 = LocalDate.of(1990, 1, 1);
        List<Author> authors = this.authorRepository.findDisctinctByBooksReleaseDateBefore(year1990);
        authors.forEach(a -> System.out.println(a.getFirstName() + " " + a.getLastName()));
    }

    private void _01_booksAfter2000() {
        LocalDate year2000 = LocalDate.of(2000, 12, 31);

        List<Book> books = this.bookRepository.findByReleaseDateAfter(year2000);
        books.forEach(b -> System.out.println(b.getTitle()));
        int count = this.bookRepository.countByReleaseDateAfter(year2000);
        System.out.println("Total count: " + count);
    }
}
