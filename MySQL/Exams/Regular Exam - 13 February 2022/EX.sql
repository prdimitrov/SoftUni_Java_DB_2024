CREATE DATABASE online_stores;
USE online_stores;

-- 01. 
CREATE TABLE brands(
id Int Primary Key AUTO_INCREMENT,
name VARCHAR(40) NOT NULL UNIQUE);

CREATE TABLE categories(
id INT PRIMARY KEY AUTO_INCREMENT, 
name VARCHAR(40) NOT NULL UNIQUE);

CREATE TABLE reviews(
id INT PRIMARY KEY AUTO_INCREMENT, 
content TEXT, 
rating DECIMAL(10, 2) NOT NULL, 
picture_url VARCHAR(80) NOT NULL, 
published_at datetime NOT NULL
);

CREATE TABLE products(
id INT PRIMARY KEY AUTO_INCREMENT, 
name VARCHAR(40) NOT NULL, 
price DECIMAL(19, 2) NOT NULL, 
quantity_in_stock INT, 
description TEXT, 
brand_id INT NOT NULL, 
category_id INT NOT NULL, 
review_id INT, 
CONSTRAINT fk_products_brands 
FOREIGN KEY (brand_id) 
REFERENCES brands(id), 
CONSTRAINT fk_products_categories 
FOREIGN KEY (category_id) 
REFERENCES categories(id), 
CONSTRAINT fk_products_reviews 
FOREIGN KEY (review_id) 
REFERENCES reviews(id));

CREATE TABLE customers(
id INT PRIMARY KEY AUTO_INCREMENT, 
first_name VARCHAR(20) NOT NULL, 
last_name VARCHAR(20) NOT NULL, 
phone VARCHAR(30) UNIQUE NOT NULL, 
address VARCHAR(60) NOT NULL, 
discount_card bit(1) NOT NULL DEFAULT 0); 

CREATE TABLE orders(
id INT PRIMARY KEY AUTO_INCREMENT, 
order_datetime DATETIME NOT NULL, 
customer_id INT NOT NULL, 
CONSTRAINT fk_orders_customers 
FOREIGN KEY (customer_id) 
REFERENCES customers(id));

CREATE TABLE orders_products(
order_id INT, 
product_id INT, 
CONSTRAINT fk_orders_products_orders 
FOREIGN KEY (order_id) 
REFERENCES products(id), 
CONSTRAINT fk_orders_products_products 
FOREIGN KEY (product_id) 
REFERENCES products(id));

-- 02. 
-- You will have to insert records of data into the reviews table, based on the products table.
-- For products with an id equal or greater than 5, insert data in the reviews table with the following values:
-- •	content – set it to the first 15 characters from the description of the product.
-- •	picture_url – set it to the product's name but reversed.
-- •	published_at – set it to 10-10-2010.
-- •	rating – set it to the price of the product divided by 8.
INSERT INTO reviews (content, picture_url, published_at, rating)
SELECT 
SUBSTRING(p.description, 1, 15) AS content, 
REVERSE(p.name) AS picture_url, 
'2010-10-10' AS published_at, 
price / 8 AS rating 
FROM products AS p 
WHERE p.id >= 5;

-- 03. 
UPDATE products 
SET quantity_in_stock = quantity_in_stock - 5 WHERE 
quantity_in_stock >= 60 AND quantity_in_stock <= 70;

-- 04. 
DELETE FROM customers 
WHERE id NOT IN (SELECT customer_id FROM orders);

-- 05. 
SELECT id, name FROM categories ORDER BY name DESC;

-- 06. 
SELECT p.id, p.brand_id, p.name, p.quantity_in_stock FROM products AS p 
WHERE p.price > 1000 AND p.quantity_in_stock < 30 
ORDER BY quantity_in_stock, id;

-- 07. 
SELECT * FROM reviews WHERE 
SUBSTRING(content, 1, 2) Like "My" AND LENGTH(content) > 61 
ORDER BY rating DESC;

-- 08. 
SELECT CONCAT(c.first_name, " ", c.last_name) AS full_name, c.address, o.order_datetime 
FROM customers AS c 
JOIN orders AS o ON c.id = o.customer_id 
WHERE YEAR(order_datetime) <= 2018
ORDER BY full_name DESC;

-- 09. 
SELECT COUNT(p.id) AS items_count, c.name, SUM(quantity_in_stock) AS total_quantity FROM categories AS c
JOIN products AS p ON c.id = p.category_id 
GROUP BY c.id
ORDER BY items_count DESC, total_quantity ASC LIMIT 5;

-- 10. 
DELIMITER // 
CREATE FUNCTION udf_customer_products_count(name VARCHAR(30)) 
RETURNS INT
DETERMINISTIC
BEGIN 
DECLARE count_products INT;
SELECT COUNT(o.id) INTO count_products FROM customers AS c 
JOIN orders AS o ON c.id = o.customer_id 
JOIN orders_products AS op ON o.id = op.order_id 
WHERE c.first_name = name;
RETURN count_products;
END //
DELIMITER ;

-- TESTS
SELECT c.first_name,c.last_name, udf_customer_products_count('Shirley') as `total_products` FROM customers c
WHERE c.first_name = 'Shirley'; 

-- 11. 
-- Create a stored procedure udp_reduce_price which accepts the following parameters:
-- •	category_name (VARCHAR(50))
-- Extracts data about the products from the given category and reduces the prices by 30% of all products 
-- that have reviews with rating less than 4 and are from the given category. 

DELIMITER // 
CREATE PROCEDURE udp_reduce_price(category_name VARCHAR(50)) 
BEGIN 
DECLARE id_of_category INT;
SELECT id INTO id_of_category FROM categories WHERE name = category_name;
UPDATE products 
SET price = price * 0.7 
WHERE id_of_category = category_id 
AND review_id IN (SELECT id FROM reviews WHERE rating < 4);
END //
DELIMITER ;

-- TESTS 
CALL udp_reduce_price ('Phones and tablets');
