package com.example.springintro;

import com.example.springintro.model.entity.Book;
import com.example.springintro.model.entity.BookSummary;
import com.example.springintro.model.entity.EditionType;
import com.example.springintro.service.AuthorService;
import com.example.springintro.service.BookService;
import com.example.springintro.service.CategoryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Scanner;
import java.util.stream.Collectors;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final BookService bookService;

    public CommandLineRunnerImpl(CategoryService categoryService, AuthorService authorService, BookService bookService) {
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @Override
    public void run(String... args) throws Exception {
//        seedData();
        //printAllBooksAfterYear(2000);
//        printAllAuthorsNamesWithBooksWithReleaseDateBeforeYear(1990);
        //   printAllAuthorsAndNumberOfTheirBooks();
//        printALlBooksByAuthorNameOrderByReleaseDate("George", "Powell");

        //EXERCISES
//        _ex_01_booksTitlesByAgeRestriction();
//        _ex_02_goldenBooks();
//        _ex_03_booksByPrice();
//        _ex_04_notReleasedBooks();
//        _ex_05_booksReleasedBeforeDate();
//        _ex_06_authorsSearch();
//        _ex_07_bookSearch();
//        _ex_08_bookTitleSearch();
//        _ex_09_countBooks();
//        _ex_10_totalBookCopies();
//    _ex_11_reducedBook();
//        _ex_12_increaseBookCopies();
        _ex_13_removeBooks();
    }

    private void _ex_13_removeBooks() {
        /*
        Write a program that removes from the database those books,
        which copies are lower than a given number.
        Print the number of books that were deleted from the database.
         */
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter amount: ");
        int amount = Integer.parseInt(sc.nextLine());
        System.out.println(this.bookService.deleteWithCopiesLessThan(amount));
    }

    private void _ex_12_increaseBookCopies() {
        /*
        Write a program that
        increases the copies of all books released after a given date
        with a given number.
        Print the total amount of book copies that were added.
        Input
        •	On the first line – date in the format dd MMM yyyy.
        If a book is released after that date (exclusively),
        increase its book copies with the provided number
        from the second line of the input.

        •	On the second line –
        the number of book copies each book should be increased with.
         */
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter date: ");
        String date = sc.nextLine();
        System.out.print("Enter amount: ");
        int amount = Integer.parseInt(sc.nextLine());

        int booksUpdated = this.bookService.addCopiesToBooksAfter(date, amount);
        System.out.printf("%d books are released after %s, " +
                "so a total of %d book copies were added.",
                booksUpdated, date, amount * booksUpdated);
    }

    private void _ex_11_reducedBook() {
        /*
        Write a program that prints information
        (title, edition type, age restriction and price) for a book by given title.
        When retrieving the book information
        select only those fields and do NOT include any other information in the returned result.
         */
      Scanner sc = new Scanner(System.in);
        System.out.print("Input title: ");
      String inputTitle = sc.nextLine();

        BookSummary summary = this.bookService.getInformationForTitle(inputTitle);

        System.out.println(summary.getTitle() + " " + summary.getEditionType() +
                " " + summary.getAgeRestriction() + " " + summary.getPrice());
    }

    private void _ex_10_totalBookCopies() {
        /*
        Write a program that prints
        the total number of book copies by author.
        Order the results descending by total book copies.
         */
        this.authorService.getWithTotalCopies()
                .forEach(a -> System.out.println(
                        a.getFirstName() + " " + a.getLastName() + " - " + a.getTotalCopies()));
    }

    private void _ex_09_countBooks() {
        /*
        Write a program that prints the number of books,
         whose title is longer than a given number.
         */
        Scanner sc = new Scanner(System.in);
        System.out.print("Input length: ");
        int length = Integer.parseInt(sc.nextLine());

        int totalBooks = this.bookService.countBooksWithTitleLongerThan(length);
        System.out.println("Output: ");
        System.out.println(length);
        System.out.printf("Comment: \n\tThere are %d books with title longer than %d symbols",
                totalBooks, length);
    }

    private void _ex_08_bookTitleSearch() {
        /*
        Write a program that prints the titles of books,
        which are written by authors,
        whose last name starts with a given string.
         */
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter author's last name starting string: ");
        String search = sc.nextLine();

        System.out.println("Output: ");
        this.bookService.findByAuthorLastNameStartingWith(search)
                .forEach(b -> System.out.printf("%s (%s %s)%n",
                        b.getTitle(), b.getAuthor().getFirstName(),
                        b.getAuthor().getLastName()));
    }

    private void _ex_07_bookSearch() {
        /*
        Write a program that prints
        the titles of books, which contain a given string (regardless of the casing).
         */
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter string: ");
        String search = sc.nextLine();

        System.out.println("Output: ");
        this.bookService.findAllTitlesContaining(search)
                .forEach(System.out::println);
    }

    private void _ex_06_authorsSearch() {
        /*
        Write a program that prints
        the names of those authors, whose first name ends with a given string.
         */
        Scanner sc = new Scanner(System.in);
        System.out.print("Input a string: ");
        String input = sc.nextLine();

        System.out.println("Output: ");
        this.authorService.findByFirstNameEndingWith(input)
                .forEach(a -> System.out.printf("%s %s%n",
                        a.getFirstName(), a.getLastName()));
    }

    private void _ex_05_booksReleasedBeforeDate() {
//        Write a program that prints
//        the title, the edition type and the price of books,
//        which are released before a given date.
//        The date will be in the format dd-MM-yyyy.
        Scanner sc = new Scanner(System.in);
        System.out.print("Input date in format dd-MM-yyyy: ");
        String inputDate = sc.nextLine();
        System.out.println("Output: ");
        bookService.findBooksReleasedBefore(inputDate).
                forEach(b -> System.out.printf("%s %s %.2f%n",
                        b.getTitle(), b.getEditionType(), b.getPrice()));
    }

    void _ex_04_notReleasedBooks() {
        /*
        Write a program that prints the titles of all books that are NOT released in a given year.
        */
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter year: ");
        int inputYear = Integer.parseInt(sc.nextLine());

        System.out.println("Output: ");
        this.bookService.findNotReleasedIn(inputYear).forEach(System.out::println);
    }

    private void _ex_03_booksByPrice() {
        /*
        Write a program that prints
        the titles and prices of books with price lower than 5 and higher than 40.
         */
        bookService.findByPriceLessThanOrPriceGreaterThan(5, 40)
                .forEach(b ->
                        System.out.println(b.getTitle() + " " + " - $" + b.getPrice()));
    }

    private void _ex_02_goldenBooks() {
            /*
            2.	Golden Books
Write a program that prints the titles of the golden edition books, which have less than 5000 copies.
             */
        bookService.findAllTitlesByEditionAndCopies(EditionType.GOLD, 5000)
                .forEach(System.out::println);
    }

    private void _ex_01_booksTitlesByAgeRestriction() {
        /*
    1.	Books Titles by Age Restriction
        Write a program that prints the titles of all books,
        for which the age restriction matches the given input (minor, teen or adult).
        Ignore the casing of the input.
     */
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter age restriction: ");
        String inputAgeRestriction = sc.nextLine();

        System.out.println("Output: ");
        bookService.findAllTitlesByAgeRestriction(inputAgeRestriction)
                .forEach(System.out::println);
    }

    private void printALlBooksByAuthorNameOrderByReleaseDate(String firstName, String lastName) {
        bookService
                .findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(firstName, lastName)
                .forEach(System.out::println);
    }

    private void printAllAuthorsAndNumberOfTheirBooks() {
        authorService
                .getAllAuthorsOrderByCountOfTheirBooks()
                .forEach(System.out::println);
    }

    private void printAllAuthorsNamesWithBooksWithReleaseDateBeforeYear(int year) {
        bookService
                .findAllAuthorsWithBooksWithReleaseDateBeforeYear(year)
                .forEach(System.out::println);
    }

    private void printAllBooksAfterYear(int year) {
        bookService
                .findAllBooksAfterYear(year)
                .stream()
                .map(Book::getTitle)
                .forEach(System.out::println);
    }

    private void seedData() throws IOException {
        categoryService.seedCategories();
        authorService.seedAuthors();
        bookService.seedBooks();
    }
}
