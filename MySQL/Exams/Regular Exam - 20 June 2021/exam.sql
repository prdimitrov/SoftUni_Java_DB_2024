CREATE DATABASE stc;
USE stc;

-- 01. Table Design 
CREATE TABLE addresses(
id INT PRIMARY KEY AUTO_INCREMENT, 
name VARCHAR(100) NOT NULL
); 

CREATE TABLE categories(
id INT PRIMARY KEY AUTO_INCREMENT, 
name VARCHAR(10) NOT NULL
); 

CREATE TABLE clients(
id INT PRIMARY KEY AUTO_INCREMENT, 
full_name VARCHAR(50) NOT NULL, 
phone_number VARCHAR(20) NOT NULL
); 

CREATE TABLE drivers(
id INT PRIMARY KEY AUTO_INCREMENT, 
first_name VARCHAR(30) NOT NULL, 
last_name VARCHAR(30) NOT NULL, 
age INT NOT NULL, 
rating FLOAT DEFAULT 5.5 NOT NULL
); 

CREATE TABLE cars(
id INT PRIMARY KEY AUTO_INCREMENT, 
make VARCHAR(20) NOT NULL, 
model VARCHAR(20), 
year INT DEFAULT 0 NOT NULL, 
mileage INT DEFAULT 0, 
`condition` CHAR(1) NOT NULL, 
category_id INT NOT NULL, 
CONSTRAINT fk_cars_categories 
FOREIGN KEY (category_id) 
REFERENCES categories(id)
); 

CREATE TABLE courses(
id INT PRIMARY KEY AUTO_INCREMENT, 
from_address_id INT NOT NULL, 
start DATETIME NOT NULL, 
bill DECIMAL(10, 2) DEFAULT 10, 
car_id INT NOT NULL, 
client_id INT NOT NULL, 
CONSTRAINT fk_courses_addresses 
FOREIGN KEY (from_address_id)
REFERENCES addresses(id), 
CONSTRAINT fk_courses_cars 
FOREIGN KEY (car_id) 
REFERENCES cars(id), 
CONSTRAINT fk_courses_clients 
FOREIGN KEY (client_id) 
REFERENCES clients(id)
); 

CREATE TABLE cars_drivers(
car_id INT NOT NULL, 
driver_id INT NOT NULL, 
PRIMARY KEY (car_id, driver_id), 
CONSTRAINT fk_cars_drivers_cars 
FOREIGN KEY (car_id) 
REFERENCES cars(id), 
CONSTRAINT fk_cars_drivers_drivers 
FOREIGN KEY (driver_id) 
REFERENCES drivers(id)
); 

-- 02. Insert 
INSERT INTO clients (full_name, phone_number) 
SELECT 
CONCAT(d.first_name, " ", d.last_name) AS full_name, 
CONCAT("(088) 9999", d.id * 2) AS phone_number FROM drivers AS d 
WHERE d.id BETWEEN 10 AND 20; 

-- 03. Update 
UPDATE cars 
SET `condition` = "C" 
WHERE (mileage > 800000 OR mileage IS NULL) AND year <= 2010 
AND make != "Mercedes-Benz";

-- 04. Delete 
DELETE FROM clients AS c 
WHERE c.id NOT IN (SELECT client_id FROM courses) 
AND LENGTH(c.full_name) > 3;

-- 05. Cars
SELECT make, model, `condition` FROM cars 
ORDER BY id;

-- 06. Drivers and Cars 
SELECT d.first_name, d.last_name, c.make, c.model, c.mileage FROM cars AS c 
JOIN cars_drivers AS cd ON c.id = cd.car_id 
JOIN drivers AS d ON cd.driver_id = d.id 
WHERE c.mileage IS NOT NULL 
ORDER BY c.mileage DESC, first_name; 

-- 07. Number of courses for each car 
SELECT c.id AS car_id, c.make, c.mileage, COUNT(co.car_id) AS count_of_courses, ROUND(AVG(co.bill), 2) AS avg_bill 
FROM cars AS c 
LEFT JOIN courses AS co ON c.id = co.car_id 
GROUP BY c.id, c.make 
HAVING count_of_courses != 2
ORDER BY count_of_courses DESC, c.id; 

-- 08. Regular clients 
SELECT c.full_name, COUNT(co.car_id) AS count_of_cars, SUM(co.bill) AS total_sum FROM clients AS c 
JOIN courses AS co ON c.id = co.client_id 
WHERE SUBSTRING(c.full_name, 2, 1) = "a" 
GROUP BY c.full_name 
HAVING count_of_cars > 1 
ORDER BY c.full_name; 

-- 09. Full information on corses 
SELECT a.name, CASE 
WHEN HOUR(c.start) BETWEEN 6 AND 20 THEN "Day" 
WHEN HOUR(c.start) BETWEEN 21 AND 23 OR HOUR(c.start) BETWEEN 0 AND 5 THEN "Night" 
END AS day_time, 
co.bill, cl.full_name, ca.make, ca.model, cat.name FROM courses AS c 
JOIN addresses AS a ON c.from_address_id = a.id 
JOIN courses AS co ON c.id = co.id 
JOIN clients AS cl ON co.client_id = cl.id 
JOIN cars AS ca ON c.car_id = ca.id 
JOIN categories AS cat ON ca.category_id = cat.id 
ORDER BY c.id; 

-- 10. Find all courses by client's phone number 
DELIMITER // 
CREATE FUNCTION udf_courses_by_client (phone_num VARCHAR(20)) 
RETURNS INT 
DETERMINISTIC 
BEGIN 
DECLARE count INT; 
SELECT COUNT(c.phone_number) INTO count FROM clients AS c 
JOIN courses AS co ON c.id = co.client_id 
WHERE c.phone_number = phone_num;
RETURN count;
END //
DELIMITER ;

-- TESTS 
SELECT udf_courses_by_client ('(803) 6386812') as `count`; 
-- count = 5;
SELECT udf_courses_by_client ('(831) 1391236') as `count`;
-- count = 3;
SELECT udf_courses_by_client ('(704) 2502909') as `count`;
-- count = 0;

-- 11. Full info for address 
DELIMITER // 
CREATE PROCEDURE udp_courses_by_address(address_name VARCHAR(100)) 
BEGIN 
SELECT a.name, c.full_name AS full_names, CASE 
WHEN co.bill <= 20 THEN "Low" 
WHEN co.bill BETWEEN 21 AND 30 THEN "Medium" 
ELSE "High" 
END AS level_of_bill, 
ca.make, ca.condition, cat.name AS cat_name 
FROM addresses AS a 
JOIN courses AS co ON a.id = co.from_address_id 
JOIN clients AS c ON co.client_id = c.id 
JOIN cars AS ca ON co.car_id = ca.id 
JOIN categories AS cat ON ca.category_id = cat.id 
WHERE a.name LIKE address_name 
ORDER BY ca.make, c.full_name;
END //
DELIMITER ;

-- TESTS 
CALL udp_courses_by_address('700 Monterey Avenue');
CALL udp_courses_by_address('66 Thompson Drive');
