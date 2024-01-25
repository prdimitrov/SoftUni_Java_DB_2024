CREATE DATABASE real_estate_db;
USE real_estate_db;

-- 01. 
CREATE TABLE cities(
id INT PRIMARY KEY AUTO_INCREMENT, 
name VARCHAR(60) NOT NULL UNIQUE
);

CREATE TABLE property_types( 
id INT PRIMARY KEY AUTO_INCREMENT, 
type VARCHAR(40) NOT NULL UNIQUE, 
description TEXT
);

CREATE TABLE properties(
id INT PRIMARY KEY AUTO_INCREMENT, 
address VARCHAR(80) NOT NULL UNIQUE, 
price DECIMAL(19, 2) NOT NULL, 
area DECIMAL(19, 2), 
property_type_id INT, 
city_id INT, 
CONSTRAINT fk_properties_property_types 
FOREIGN KEY (property_type_id) 
REFERENCES property_types(id), 
CONSTRAINT fk_properties_cities 
FOREIGN KEY (city_id) 
REFERENCES cities(id)
);

CREATE TABLE agents(
id INT PRIMARY KEY AUTO_INCREMENT, 
first_name VARCHAR(40) NOT NULL, 
last_name VARCHAR(40) NOT NULL, 
phone VARCHAR(20) NOT NULL UNIQUE, 
email VARCHAR(50) NOT NULL UNIQUE, 
city_id INT, 
CONSTRAINT fk_agents_cities 
FOREIGN KEY (city_id) 
REFERENCES cities(id)
);

CREATE TABLE buyers(
id INT Primary Key AUTO_INCREMENT, 
first_name VARCHAR(40) NOT NULL, 
last_name VARCHAR(40) NOT NULL, 
phone VARCHAR(20) NOT NULL UNIQUE, 
email VARCHAR(50) NOT NULL UNIQUE, 
city_id INT, 
CONSTRAINT fk_buyers_cities 
FOREIGN KEY (city_id) 
REFERENCES cities(id)
);

CREATE TABLE property_offers(
property_id INT NOT NULL, 
agent_id INT NOT NULL, 
price DECIMAL(19, 2) NOT NULL, 
offer_datetime DATETIME, 
CONSTRAINT fk_properties_offers_properties 
FOREIGN KEY (property_id) 
REFERENCES properties(id), 
CONSTRAINT fk_property_offers_agents 
FOREIGN KEY (agent_id) 
REFERENCES agents(id)
);

CREATE TABLE property_transactions(
id INT PRIMARY KEY AUTO_INCREMENT, 
property_id INT NOT NULL, 
buyer_id INT NOT NULL, 
transaction_date DATE, 
bank_name VARCHAR(30), 
iban VARCHAR(40) UNIQUE, 
is_successful BOOLEAN, 
CONSTRAINT fk_property_transactions_properties 
FOREIGN KEY (property_id) 
REFERENCES properties(id), 
CONSTRAINT fk_property_transactions_buyers 
FOREIGN KEY (buyer_id) 
REFERENCES buyers(id)
);

-- 02. 
INSERT INTO property_transactions (property_id, buyer_id, transaction_date, bank_name, 
iban, is_successful) 
SELECT 

agent_id + DAY(offer_datetime) AS property_id, 
agent_id + MONTH(offer_datetime) AS buyer_id, 
DATE(offer_datetime) AS transaction_date, 
CONCAT("Bank ", agent_id) AS bank_name, 
CONCAT("BG", price, agent_id) AS iban, 
TRUE AS is_successful 
FROM property_offers 
WHERE agent_id <= 2;

-- 03. 
UPDATE properties 
SET price = price - 50000 WHERE price >= 800000;

-- 04. 
DELETE FROM property_transactions 
WHERE is_successful IS FALSE;

-- 05. 
SELECT id, first_name, last_name, phone, email, city_id FROM agents 
ORDER BY city_id DESC, phone DESC;

-- 06. 
SELECT property_id, agent_id, price, offer_datetime FROM property_offers 
WHERE YEAR(offer_datetime) = 2021 
ORDER BY price LIMIT 10;

-- 07. 
SELECT SUBSTRING(p.address, 1, 6) AS agent_name, LENGTH(p.address) * 5430 AS price 
FROM properties AS p 
LEFT JOIN property_offers AS po ON p.id = po.property_id 
WHERE po.property_id IS NULL
ORDER BY agent_name DESC, price DESC;

-- 08. 
SELECT pt.bank_name AS bank_name, COUNT(pt.iban) AS count 
FROM property_transactions AS pt 
GROUP BY pt.bank_name 
HAVING count >= 9
ORDER BY count DESC, bank_name;

-- 09. 
SELECT p.address, p.area, CASE 
WHEN area <= 100 THEN "small" 
WHEN area <= 200 THEN "medium" 
WHEN area <= 500 THEN "large" 
ELSE "extra large"
END AS size 
FROM properties AS p
ORDER BY area, address DESC;

-- 10. 
-- Create a user defined function with the name 
-- udf_offers_from_city_name (cityName VARCHAR(50)) 
-- that receives a city name and returns the total number of offers from that city.
-- Required Columns
-- •	offers_count (udf_offers_from_city_name)

DELIMITER // 
CREATE FUNCTION udf_offers_from_city_name (cityName VARCHAR(50)) 
RETURNS INT
DETERMINISTIC
BEGIN 
DECLARE count INT; 
-- INTO count трябва да е, а не AS count 
SELECT COUNT(po.property_id) INTO count
FROM property_offers AS po 
JOIN properties AS p ON po.property_id = p.id 
JOIN cities AS c ON p.city_id = c.id 
WHERE c.name = cityName;
RETURN count;
END //
DELIMITER ;

-- TEST
SELECT udf_offers_from_city_name('Amsterdam') AS 'offers_count';

-- 11. 
-- The real estate agents want to make special offers for their loyal clients. 
-- Your task is to find all offers from an agent and reduce the prices by 10%.
-- Create a stored procedure udp_special_offer which accepts the following parameters:
-- •	first_name VARCHAR(50)

DELIMITER // 
CREATE PROCEDURE udp_special_offer(first_name VARCHAR(50))
BEGIN 
UPDATE property_offers AS po 
JOIN agents AS a ON po.agent_id = a.id 
SET po.price = po.price - (po.price * 0.10) 
WHERE a.first_name = first_name;
END // 
DELIMITER ;

-- TESTS
CALL udp_special_offer("Hans");
