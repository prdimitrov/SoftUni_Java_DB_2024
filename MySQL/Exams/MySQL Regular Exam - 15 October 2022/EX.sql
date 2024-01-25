CREATE DATABASE restaurant_db;
USE restaurant_db;

-- 01. 
CREATE TABLE products (
id INT PRIMARY KEY, 
name VARCHAR(30) NOT NULL UNIQUE, 
type VARCHAR(30) NOT NULL, 
price DECIMAL (10, 2) NOT NULL
);
CREATE TABLE clients (
id INT PRIMARY KEY AUTO_INCREMENT, 
first_name VARCHAR(50) NOT NULL, 
last_name VARCHAR(50) NOT NULL, 
birthdate DATE NOT NULL, 
card  VARCHAR(50), 
review TEXT
);
CREATE TABLE tables (
id INT PRIMARY KEY AUTO_INCREMENT, 
floor INT NOT NULL, 
reserved BOOLEAN, 
capacity INT NOT NULL
);
CREATE TABLE waiters (
id INT PRIMARY KEY AUTO_INCREMENT, 
first_name VARCHAR(50) NOT NULL, 
last_name VARCHAR(50) NOT NULL, 
email VARCHAR(50) NOT NULL, 
phone VARCHAR(50), 
salary DECIMAL(10, 2)
);
CREATE TABLE orders (
id INT PRIMARY KEY AUTO_INCREMENT, 
table_id INT NOT NULL, 
waiter_id INT NOT NULL, 
order_time TIME NOT NULL, 
payed_status BOOLEAN, 
CONSTRAINT fk_orders_tables 
FOREIGN KEY (table_id) 
REFERENCES tables(id), 
CONSTRAINT fk_orders_waiters 
FOREIGN KEY (waiter_id) 
REFERENCES waiters(id)
);
CREATE TABLE orders_clients (
order_id INT, 
client_id INT, 
CONSTRAINT fk_orders_clients_orders 
FOREIGN KEY (order_id) 
REFERENCES orders(id), 
CONSTRAINT fk_orders_clients_clients 
FOREIGN KEY (client_id) 
REFERENCES clients(id)
);
CREATE TABLE orders_products (
order_id INT, 
product_id INT, 
CONSTRAINT fk_orders_products_orders 
FOREIGN KEY (order_id) 
REFERENCES orders(id), 
CONSTRAINT fk_orders_products_products 
FOREIGN KEY (product_id) 
REFERENCES products(id)
);

-- 02. 
INSERT INTO products (name, type, price)
SELECT 
CONCAT(w.last_name, " ", "specialty") AS name, 
"Cocktail" AS type, 
CEIL(w.salary * 0.01) AS price 
FROM waiters AS w WHERE 
w.id > 6;

-- 03. 
UPDATE orders 
SET table_id = table_id - 1 WHERE orders.id BETWEEN 12 AND 23;

-- 04. 
DELETE w FROM waiters AS w 
LEFT JOIN orders AS o ON w.id = o.waiter_id 
WHERE o.waiter_id IS NULL;

-- 05. 
SELECT id, first_name, last_name, birthdate, card, review FROM clients 
ORDER BY birthdate DESC, id DESC;

-- 06. 
SELECT c.first_name, c.last_name, c.birthdate, c.review FROM clients AS c
WHERE c.card IS NULL AND YEAR(c.birthdate) BETWEEN 1978 AND 1993 ORDER BY last_name DESC, id LIMIT 5;

-- 07. 
SELECT 
CONCAT(w.last_name, w.first_name, LENGTH(w.first_name), 'Restaurant') AS username, 
REVERSE(SUBSTRING(w.email, 2, 12)) AS password FROM waiters AS w WHERE 
w.salary > 0 
ORDER BY password DESC; 

-- 08. 
SELECT p.id, p.name, COUNT(op.product_id) AS count FROM products AS p 
JOIN orders_products AS op ON p.id = op.product_id 
GROUP BY p.id, p.name 
HAVING count >= 5 
ORDER BY count DESC, p.name ASC;

-- 09. 
SELECT t.id AS table_id, capacity, count_of_clients.clients_count AS count_clients, 
	CASE 
	WHEN capacity > count_of_clients.clients_count THEN "Free seats" 
    WHEN capacity = count_of_clients.clients_count THEN "Full" 
    WHEN capacity < count_of_clients.clients_count THEN "Extra seats" 
    END AS availability 
FROM tables AS t 
JOIN (
    SELECT o.table_id, COUNT(oc.client_id) AS clients_count 
    FROM orders_clients AS oc 
    JOIN orders AS o ON oc.order_id = o.id 
    JOIN tables AS t ON o.table_id = t.id 
    JOIN clients AS c ON oc.client_id = c.id 
    WHERE t.floor = 1 
    GROUP BY o.table_id
) AS count_of_clients ON t.id = count_of_clients.table_id 
ORDER BY table_id DESC;

-- 10. 
DELIMITER // 
CREATE FUNCTION udf_client_bill(full_name VARCHAR(50)) 
RETURNS DECIMAL(10, 2) 
DETERMINISTIC
BEGIN 
DECLARE bill DECIMAL(10, 2);
SELECT SUM(p.price) INTO bill FROM clients AS c 
JOIN orders_clients AS oc ON c.id = oc.client_id 
JOIN orders_products AS op ON oc.order_id = op.order_id 
JOIN products AS p ON op.product_id = p.id 
WHERE CONCAT(c.first_name, " ", c.last_name) = full_name;
RETURN bill;
END //
DELIMITER ;

-- TEST
SELECT c.first_name,c.last_name, udf_client_bill('Silvio Blyth') as 'bill' FROM clients c
WHERE c.first_name = 'Silvio' AND c.last_name= 'Blyth';

-- 11. 
DELIMITER // 
CREATE PROCEDURE udp_happy_hour(type VARCHAR(50)) 
BEGIN 
UPDATE products AS p
SET price = price - (price * 0.2) 
WHERE p.type = type AND price >= 10;
END //
DELIMITER ;

-- TEST 
CALL udp_happy_hour ('Cognac');
