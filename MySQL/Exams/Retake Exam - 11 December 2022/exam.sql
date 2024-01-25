CREATE DATABASE airlines_db;
USE airlines_db;

-- 01. 
CREATE TABLE countries(
id INT PRIMARY KEY AUTO_INCREMENT, 
name VARCHAR(30) NOT NULL UNIQUE, 
description TEXT, 
currency VARCHAR(5) NOT NULL
);

CREATE TABLE airplanes(
id INT Primary Key AUTO_INCREMENT, 
model VARCHAR(50) NOT NULL UNIQUE, 
passengers_capacity INT NOT NULL, 
tank_capacity DECIMAL(19, 2) NOT NULL, 
cost DECIMAL(19, 2) NOT NULL
);

CREATE TABLE passengers(
id INT PRIMARY KEY AUTO_INCREMENT, 
first_name VARCHAR(30) NOT NULL, 
last_name VARCHAR(30) NOT NULL, 
country_id INT NOT NULL, 
CONSTRAINT fk_passengers_countries 
FOREIGN KEY (country_id) 
REFERENCES countries(id)
);

CREATE TABLE flights(
id INT PRIMARY KEY AUTO_INCREMENT, 
flight_code VARCHAR(30) NOT NULL UNIQUE, 
departure_country INT NOT NULL, 
destination_country INT NOT NULL, 
airplane_id INT NOT NULL, 
has_delay BOOLEAN, 
departure DATETIME, 
CONSTRAINT fk_flights_departure_countries 
FOREIGN KEY (departure_country) 
REFERENCES countries(id), 
CONSTRAINT fk_flights_destination_countries 
FOREIGN KEY (destination_country)
REFERENCES countries(id), 
CONSTRAINT fk_flights_airplanes 
FOREIGN KEY (airplane_id) 
REFERENCES airplanes(id)
);

CREATE TABLE flights_passengers(
flight_id INT, 
passenger_id INT, 
CONSTRAINT fk_flights_passengers_flights 
FOREIGN KEY (flight_id) 
REFERENCES flights(id), 
CONSTRAINT fk_flights_passengers_passengers 
FOREIGN KEY (passenger_id) 
REFERENCES passengers(id)
);

-- 02. 
INSERT INTO airplanes(model, passengers_capacity, tank_capacity, cost) 
SELECT 
CONCAT(REVERSE(p.first_name), "797") AS model, 
LENGTH(p.last_name) * 17 AS passengers_capacity, 
p.id * 790 AS tank_capacity, 
LENGTH(p.first_name) * 50.6 AS cost FROM passengers AS p WHERE id <= 5;


-- 03. 
UPDATE flights 
SET airplane_id = airplane_id + 1 
WHERE departure_country LIKE (SELECT id FROM countries WHERE name = "Armenia");

-- 04. 
DELETE FROM flights 
WHERE id NOT IN (SELECT flight_id FROM flights_passengers);



-- 05. 
-- DELETE TABLES VALUES!! 
SELECT id, model, passengers_capacity, tank_capacity, cost FROM airplanes 
ORDER BY cost DESC, id DESC;

-- 06. 
SELECT flight_code, departure_country, airplane_id, departure FROM flights 
WHERE YEAR(departure) = 2022 
ORDER BY airplane_id, flight_code LIMIT 20;

-- 07. 
SELECT 
CONCAT(UPPER(SUBSTRING(p.last_name, 1, 2)), p.country_id) AS flight_code,
CONCAT(p.first_name, " ", p.last_name) AS full_name, p.country_id 
FROM passengers AS p 
WHERE 
p.id NOT IN (SELECT fp.passenger_id FROM flights_passengers AS fp) ORDER BY p.country_id ASC;

-- 08. 
SELECT c.name, c.currency, COUNT(f.destination_country) AS booked_tickets FROM countries AS c 
JOIN flights AS f ON c.id = f.destination_country 
LEFT JOIN flights_passengers AS fp ON f.id = fp.flight_id 
GROUP BY c.id 
HAVING booked_tickets >= 20 ORDER BY booked_tickets DESC;

-- 09. 
SELECT flight_code, departure, 
CASE 
WHEN TIME(departure) >= "05:00:00" AND TIME(departure) <= "11:59:00" THEN "Morning" 
WHEN TIME(departure) >= "12:00:00" AND TIME(departure) <= "16:59:00" THEN "Afternoon" 
WHEN TIME(departure) >= "17:00:00" AND TIME(departure) <= "20:59:00" THEN "Evening" 
WHEN TIME(departure) >= "21:00:00" AND TIME(departure) <= "23:59:00" OR 
(TIME(departure) >= "00:00:00" AND TIME(departure) <= "04:59:00") THEN "Night"
END AS day_part 
FROM flights 
ORDER BY flight_code DESC; 

-- 10. 
DELIMITER // 
CREATE FUNCTION udf_count_flights_from_country(country VARCHAR(50)) 
RETURNS INT 
DETERMINISTIC 
BEGIN 
DECLARE flights_count INT;
SELECT COUNT(f.id) INTO flights_count FROM flights AS f 
JOIN countries AS c ON f.departure_country = c.id 
WHERE c.name = country;
RETURN flights_count;
END //
DELIMITER ;

-- TESTS 
SELECT udf_count_flights_from_country("Brazil");

-- 11. 
DELIMITER // 
CREATE PROCEDURE udp_delay_flight(code VARCHAR(50))
BEGIN 
UPDATE flights 
SET departure = ADDTIME(departure, "00:30:00"), has_delay = TRUE 
WHERE flight_code = code;
END // 
DELIMITER ;

-- TESTS 
CALL udp_delay_flight("ZP-782");

SELECT * FROM flights WHERE flight_code = "ZP-782";
