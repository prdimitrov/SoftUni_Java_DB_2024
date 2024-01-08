--
USE book_library;
--

-- 1.	Find Book Titles
SELECT title FROM books WHERE SUBSTRING(title, 1, 3) = "The";

-- 2.	Replace Titles
SELECT REPLACE(title, "The", "***") AS Title FROM books WHERE SUBSTRING(title, 1, 3) = "The";

-- 3.	Sum Cost of All Books
SELECT ROUND(SUM(cost), 2) FROM books;
-- може и с SELECT FORMAT(SUM(cost), 2) FROM books;

-- 4.	Days Lived
SELECT CONCAT(first_name, " ", last_name) AS "Full Name",
DATEDIFF(died, born) FROM authors;

-- 5.	Harry Potter Books
SELECT title FROM books WHERE SUBSTRING(title, 1, 12) = "Harry Potter";
