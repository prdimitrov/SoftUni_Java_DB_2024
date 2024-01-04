CREATE TABLE gamebar;

USE gamebar;

-- 1. Create Tables

CREATE TABLE employees (
id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
first_name VARCHAR(50) NOT NULL,
last_name VARCHAR(50) NOT NULL);
 
CREATE TABLE categories (
id INT AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(50) NOT NULL);

CREATE TABLE products (
id INT AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(50) NOT NULL,
category_id INT NOT NULL);

-- 2. Insert Data in Tables

INSERT employees (first_name, last_name) VALUES ("Edin", "Gospodin"); 
INSERT employees (first_name, last_name) VALUES ("Gospodin", "Gospodinov"); 
INSERT employees (first_name, last_name) VALUES ("Nqkakuv", "Chovek"); 

-- 3. Alter Tables

ALTER TABLE employees
ADD COLUMN middle_name VARCHAR(50);

-- 4. Adding Constraints

ALTER TABLE products
ADD CONSTRAINT my_fk
FOREIGN KEY (category_id)
REFERENCES categories (id);

-- 5. Modifying Columns

ALTER TABLE employees 
CHANGE COLUMN middle_name middle_name VARCHAR(100) NOT NULL;

