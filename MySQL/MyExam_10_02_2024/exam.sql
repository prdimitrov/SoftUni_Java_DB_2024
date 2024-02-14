-- 00. CREATE AND USE DATABASE 
CREATE DATABASE preserve;
USE preserve;

-- 01. Table Designnnn
CREATE TABLE continents(
id INT PRIMARY KEY AUTO_INCREMENT, 
name VARCHAR(40) NOT NULL UNIQUE
);

CREATE TABLE countries(
id INT PRIMARY KEY AUTO_INCREMENT, 
name VARCHAR(40) NOT NULL UNIQUE, 
country_code VARCHAR(10) UNIQUE NOT NULL, 
continent_id INT NOT NULL, 
CONSTRAINT foreign_k_ey_countries_continents 
FOREIGN KEY (continent_id) 
REFERENCES continents(id)
);

CREATE TABLE preserves(
id INT PRIMARY KEY AUTO_INCREMENT, 
name VARCHAR(255) NOT NULL UNIQUE, 
latitude DECIMAL(9, 6), 
longitude DECIMAL(9, 6), 
area INT, 
type VARCHAR(20), 
established_on DATE
);

CREATE TABLE positions(
id INT PRIMARY KEY AUTO_INCREMENT, 
name VARCHAR(40) UNIQUE NOT NULL, 
description TEXT, 
is_dangerous BOOLEAN NOT NULL
);

CREATE TABLE workers(
id INT PRIMARY KEY AUTO_INCREMENT, 
first_name VARCHAR(40) NOT NULL, 
last_name VARCHAR(40) NOT NULL, 
age INT, 
personal_number VARCHAR(20) NOT NULL UNIQUE, 
salary DECIMAL(19, 2), 
is_armed BOOLEAN NOT NULL, 
start_date DATE, 
preserve_id INT, 
position_id INT, 
CONSTRAINT foreign_k_ey_workers_preserves 
FOREIGN KEY (preserve_id) 
REFERENCES preserves(id), 
CONSTRAINT foreign_k_ey_workers_positions 
FOREIGN KEY (position_id) 
REFERENCES positions(id)
);

CREATE TABLE countries_preserves(
country_id INT, 
preserve_id INT, 
CONSTRAINT foreign_k_ey_countries_preserves_preserves 
FOREIGN KEY (country_id) 
REFERENCES countries(id), 
CONSTRAINT foreign_k_ey_countries_preserves_countries 
FOREIGN KEY (preserve_id) 
REFERENCES preserves(id)
);

-- 02. Insert 
INSERT INTO preserves (name, latitude, longitude, area, type, established_on) 
SELECT  
CONCAT(name, " ", "is in South Hemisphere") AS name, 
latitude AS latitude, 
longitude AS longitude, 
area * id AS area, 
LOWER(type) AS `type`, 
established_on AS established_on 
FROM preserves WHERE latitude < 0;

-- 03. Update 
UPDATE workers 
SET salary = salary + 500 WHERE position_id IN (5, 8, 11, 13);

-- 04. Delete
DELETE FROM countries_preserves
WHERE preserve_id IN (SELECT p.id FROM preserves AS p WHERE p.established_on IS NULL);
DELETE FROM preserves
WHERE established_on IS NULL;

-- 05. Most experienced workers 
SELECT CONCAT(w.first_name, " ", w.last_name) AS full_name, 
DATEDIFF("2024-01-01", w.start_date) AS days_of_experience FROM workers AS w ORDER BY days_of_experience DESC 
LIMIT 10;

-- 06. Workers salary 
SELECT w.id, w.first_name, w.last_name, p.name, c.country_code FROM workers AS w 
JOIN preserves AS p ON w.preserve_id = p.id 
JOIN countries_preserves AS cp ON p.id = cp.preserve_id 
JOIN countries AS c ON cp.country_id = c.id 
WHERE w.salary > 5000 AND w.age < 50 ORDER BY country_code;

-- 07. Armed workers count men!
SELECT p.name, SUM(w.is_armed) AS armed_workers FROM preserves AS p 
JOIN workers AS w ON p.id = w.preserve_id 
GROUP BY p.name 
HAVING armed_workers > 0
ORDER BY armed_workers DESC, p.name; 

-- 08. Oldest preserves 
SELECT p.name, c.country_code, YEAR(p.established_on) AS founded_in FROM preserves AS p 
JOIN countries_preserves AS cp ON p.id = cp.preserve_id 
JOIN countries AS c ON cp.country_id = c.id WHERE MONTH(p.established_on) LIKE 5 
ORDER BY founded_in LIMIT 5; 

-- 09. Preserve categories 
SELECT p.id, p.name, 
CASE 
WHEN p.area <= 100 THEN "very small" 
WHEN p.area > 100 AND area <= 1000 THEN "small" 
WHEN p.area > 1000 AND area <= 10000 THEN "medium" 
WHEN p.area > 10000 AND area <= 50000 THEN "large" 
ELSE "very large" END AS category FROM preserves AS p ORDER BY p.area DESC;
-- Може и ORDER BY category DESC;

-- 10. Extract average salary 
DELIMITER // 
CREATE FUNCTION udf_average_salary_by_position_name (name VARCHAR(40)) 
RETURNS DECIMAL(19, 2) 
DETERMINISTIC 
BEGIN 
DECLARE avgSalaryToReturn DECIMAL(19, 2);
SELECT AVG(w.salary) INTO avgSalaryToReturn FROM workers AS w 
JOIN positions AS pos ON w.position_id = pos.id WHERE pos.name = name;
RETURN  avgSalaryToReturn;
END //
DELIMITER ;

-- TESTS
SELECT p.name, udf_average_salary_by_position_name('Forester') as position_average_salary FROM positions p 
WHERE p.name = 'Forester';

-- 11. Improving the standard of living :D
-- В лаба пише на едното място 
-- increase_salaries_by_country, а на другото udp_increase_salaries_by_country ??
DELIMITER // 
CREATE PROCEDURE udp_increase_salaries_by_country (country_name VARCHAR(40)) 
DETERMINISTIC 
BEGIN 
DECLARE idOfCountry INT; 
SELECT c.id INTO idOfCountry FROM countries AS c WHERE c.name = country_name; 
UPDATE workers AS w 
JOIN preserves AS p ON w.preserve_id = p.id 
JOIN countries_preserves AS cp ON p.id = cp.preserve_id 
SET w.salary = w.salary * 1.05 
WHERE cp.country_id = country_id;
END //
-- Може и с SET w.salary = w.salary + (w.salary * 0.05)
DELIMITER ;

-- TESTS
CALL increase_salaries_by_country("Germany");

SELECT DISTINCT first_name FROM workers;