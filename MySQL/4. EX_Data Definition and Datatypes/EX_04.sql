-- 0.	Create Database
CREATE DATABASE minions;
USE minions;

-- 1.	Create Tables

CREATE TABLE minions (
id INT AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(50) NOT NULL,
age INT(3) NOT NULL);
CREATE TABLE towns (
town_id INT PRIMARY KEY AUTO_INCREMENT,
name VARCHAR(50) NOT NULL);

-- 2.	Alter Minions Table

ALTER TABLE minions ADD COLUMN town_id INT;
ALTER TABLE minions
ADD CONSTRAINT my_fk
FOREIGN KEY (town_id)
REFERENCES towns (id);

-- 3.	Insert Records in Both Tables

INSERT INTO towns (id, name)
VALUES
(1, "Sofia"),
(2, "Plovdiv"),
(3, "Varna");

INSERT INTO minions (id, name, age, town_id)
VALUES
(1, "Kevin", 22, 1),
(2, "Bob", 15, 3),
(3, "Steward", NULL, 2);

-- 4.	Truncate Table Minions

TRUNCATE TABLE minions;

-- 5.	Drop All Tables

DROP TABLE minions, towns;

-- 6.	Create Table People

CREATE TABLE people (
id INT AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(200) NOT NULL,
picture BLOB(2),
height FLOAT(2),
weight FLOAT(2),
gender VARCHAR(1) NOT NULL,
birthdate DATE NOT NULL,
biography TEXT);

INSERT INTO people (id, name, height, weight, gender, birthdate, biography)
VALUES
(1, "Peter Meter", 170, 80, "m", '1993-11-13', "Abracadabra Simsalabim"),
(2, "Peter Better", 165, 75, "f", '1995-12-17', "Simsalabim Abracadabra"),
(3, "Peter Getter", 180, 95, "m", '1997-07-23', "AbracadabraSimsalabim"),
(4, "Peter Setter", 190, 115, "m", '2000-10-13', "SimsalabimAbracadabra"),
(5, "Peter Kilometer", 200, 300, "m", '1997-08-11', "salabimAbraSimSimcadabra");

-- 7.	Create Table Users

CREATE TABLE users (
id INT AUTO_INCREMENT PRIMARY KEY,
username VARCHAR(30) NOT NULL,
password VARCHAR(26) NOT NULL,
profile_picture BLOB,
last_login_time DATETIME,
is_deleted BOOLEAN);

INSERT INTO users (id, username, password)VALUES
(1, "peterTheMeatBeater", "onePassword"),
(2, "peterTheMeatBeater2", "onePassword2"),
(3, "peterTheMeatBeater3", "onePassword3"),
(4, "peterTheMeatBeater4", "onePassword4"),
(5, "peterTheMeatBeater5", "onePassword5");

-- 8.	Change Primary Key

ALTER TABLE users
DROP PRIMARY KEY,
ADD CONSTRAINT PRIMARY KEY users(id, username);

-- 9.	Set Default Value of a Field

ALTER TABLE users
CHANGE COLUMN last_login_time last_login_time DATETIME NULL DEFAULT NOW();

-- 10.	 Set Unique Field

ALTER TABLE users
DROP PRIMARY KEY,
ADD CONSTRAINT PRIMARY KEY pk_users(id),
CHANGE COLUMN username username VARCHAR(50) UNIQUE;

-- 11.	Movies Database

CREATE DATABASE movies;
USE movies;
--
--

CREATE TABLE directors (
id INT AUTO_INCREMENT PRIMARY KEY,
director_name VARCHAR(200) NOT NULL,
notes TEXT);

CREATE TABLE genres (
id INT AUTO_INCREMENT PRIMARY KEY,
genre_name VARCHAR(100) NOT NULL,
notes TEXT);

CREATE TABLE categories (
id INT AUTO_INCREMENT PRIMARY KEY,
category_name VARCHAR(100) NOT NULL,
notes TEXT);

CREATE TABLE movies (
id INT AUTO_INCREMENT PRIMARY KEY,
title VARCHAR(100) NOT NULL,
director_id INT,
copyright_year DATE,
length INT(4),
genre_id INT,
category_id INT,
rating DOUBLE,
notes TEXT);

INSERT INTO directors (director_name, notes)
VALUES
('DirectorOne', 'edno'),
('DirectorTwo', 'dve'),
('DirectorThree', 'tri'),
('DirectorFour', 'chetiri'),
('DirectorFive', 'pet');

INSERT INTO genres(genre_name, notes)
VALUES
('GenreOne', 'edno'),
('GenreTwo', 'dve'),
('GenreThree', 'tri'),
('GenreFour', 'chetiri'),
('GenreFive', 'pet');

INSERT INTO categories(category_name, notes)
VALUES
('FirstCategory', NULL),
('SecondCategory', 'Nqma Nishto Tuk'),
('ThirdCategory', 'Again nqma nishto'),
('FourthCategory', 'Iliqn - YO YO'),
('FifthCategory', 'Azis - Ne sme bezgreshni');

INSERT INTO movies(title, copyright_year, rating, notes)
VALUES
('Ujas', '1995-02-15', 5.38,'Very scary movie'),
('Brutal', '1995-02-15', 5.38, 'Very bad movie'),
('Pinko', '1995-02-15', 5.38, 'Old, but gold'),
('Jon Bemkata', '1995-02-15', 5.38,'Maybe yes'),
('Nqma Takava Durjava', '1995-02-15', 5.38, 'Very bad stuff');

-- 12.	Car Rental Database
CREATE DATABASE car_rental;
USE car_rental;
--

CREATE TABLE categories (
id INT AUTO_INCREMENT PRIMARY KEY,
category VARCHAR(20) NOT NULL,
daily_rate DOUBLE,
weekly_rate DOUBLE,
monthly_rate DOUBLE,
weekend_rate DOUBLE);
INSERT INTO categories (category)
VALUES
("firstCategory"),
("secondCategory"),
("thirdCategory");

CREATE TABLE cars (
id INT AUTO_INCREMENT PRIMARY KEY,
plate_number VARCHAR(20) NOT NULL,
make VARCHAR(20),
model VARCHAR(20),
car_year INT NOT NULL,
category_id INT,
doors INT,
picture BLOB,
car_condition VARCHAR(30),
available BOOLEAN);

INSERT INTO cars (plate_number, make, model, car_year)
VALUES
("T9999AK", "Ford", "Mondeo Mk3", 2006),
("CA6666CB", "Ford", "Mondeo Mk2", 1999),
("T9954AK", "Ford", "Mondeo Mk4", 2008);

CREATE TABLE employees (
id INT AUTO_INCREMENT PRIMARY KEY,
first_name VARCHAR(50) NOT NULL,
last_name VARCHAR(50) NOT NULL,
title VARCHAR(50),
notes TEXT);

INSERT INTO employees (first_name, last_name, title)
VALUES
("Harry", "Potter", "Magician"),
("Peter", "Dimitrov", "FordGuy"),
("Master", "Trichko", "Mechanic, who knows everything");

CREATE TABLE customers (
id INT AUTO_INCREMENT PRIMARY KEY,
driver_license_number VARCHAR(20),
full_name VARCHAR(50) NOT NULL,
address VARCHAR(200) NOT NULL,
city VARCHAR(20),
zip_code INT(10),
notes TEXT);

INSERT INTO customers (driver_license_number, full_name, address, city)
VALUES
("AB2255232", "Master Trichko", "Slavqnska 5", "Targovishte"),
("AB2151261", "Oleg Bregovich", "Bratanska 8", "Shumen"),
("ABC9566543", "One Mister", "Apple St. 23", "Montgomery");

CREATE TABLE rental_orders (
id INT AUTO_INCREMENT PRIMARY KEY,
employee_id INT,
customer_id INT,
car_id INT,
car_condition VARCHAR(50),
tank_level VARCHAR(20),
kilometrage_start LONG,
kilometrage_end LONG,
total_kilometrage LONG,
start_date DATE,
end_date DATE,
total_days LONG,
rate_applied DOUBLE,
tax_rate DOUBLE,
order_status TEXT,
notes TEXT);

INSERT INTO rental_orders
(employee_id, customer_id, kilometrage_start, kilometrage_end)
VALUES
(1, 1, 125632, 236741),
(2, 2, 546456, 567586),
(3, 3, 323546, 333456);
 
-- 13.	Basic Insert

CREATE DATABASE soft_uni;
USE soft_uni;

-- UPLOAD ONLY INSERT STATEMENTS !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
-- UPLOAD ONLY INSERT STATEMENTS !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

CREATE TABLE towns (
id INT AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(50) NOT NULL);

CREATE TABLE addresses (
id INT AUTO_INCREMENT PRIMARY KEY,
address_text VARCHAR(100) NOT NULL,
town_id INT);

ALTER TABLE addresses
ADD CONSTRAINT town_fk
FOREIGN KEY (town_id)
REFERENCES towns (id);

CREATE TABLE departments (
id INT AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(50) NOT NULL);

CREATE TABLE employees (
id INT AUTO_INCREMENT PRIMARY KEY,
first_name VARCHAR(50),
middle_name VARCHAR(50),
last_name VARCHAR(50),
job_title VARCHAR(50),
department_id INT,
hire_date DATE,
salary DOUBLE,
address_id INT);

ALTER TABLE employees
ADD CONSTRAINT department_fk
FOREIGN KEY (department_id)
REFERENCES departments (id);

ALTER TABLE employees
ADD CONSTRAINT address_fk
FOREIGN KEY (address_id)
REFERENCES addresses (id);

-- UPLOAD ONLY INSERT STATEMENTS !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
-- UPLOAD ONLY INSERT STATEMENTS !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

INSERT INTO towns (name)
VALUES
("Sofia"),
("Plovdiv"),
("Varna"),
("Burgas");
INSERT INTO departments (name)
VALUES
("Engineering"),
("Sales"),
("Marketing"),
("Software Development"),
("Quality Assurance");
INSERT INTO employees
(first_name, middle_name, last_name, job_title, department_id, hire_date, salary)
VALUES
("Ivan", "Ivanov", "Ivanov", ".NET Developer", 4, '2013-02-01', 3500.00),
("Petar", "Petrov", "Petrov", "Senior Engineer", 1, '2004-03-02', 4000.00),
("Maria", "Petrova", "Ivanova", "Intern", 5, '2016-08-28', 525.25),
("Georgi", "Terziev", "Ivanov", "CEO", 2, '2007-12-09', 3000.00),
("Peter", "Pan", "Pan", "Intern", 3, '2016-08-28', 599.88);

-- 14.	Basic Select All Fields

SELECT * FROM towns;
SELECT * FROM departments;
SELECT * FROM employees;

-- 15.	Basic Select All Fields and Order Them

SELECT * FROM towns ORDER BY name ASC;
SELECT * FROM departments ORDER BY name ASC;
SELECT * FROM employees ORDER BY salary DESC;

-- 16.	Basic Select Some Fields

SELECT name FROM towns ORDER BY name ASC;
SELECT name FROM departments ORDER BY name ASC;
SELECT first_name, last_name, job_title, salary FROM employees ORDER BY salary DESC;

-- 17.	Increase Employees Salary
UPDATE employees
SET salary = salary + salary * 0.10;
SELECT salary FROM employees;
