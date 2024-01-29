CREATE DATABASE sgd;
USE sgd;

-- 01. 
CREATE TABLE addresses(
id INT PRIMARY KEY AUTO_INCREMENT, 
name VARCHAR(50) NOT NULL
);

CREATE TABLE categories(
id INT PRIMARY KEY AUTO_INCREMENT, 
name VARCHAR(10) NOT NULL
);

CREATE TABLE offices(
id INT PRIMARY KEY AUTO_INCREMENT, 
workspace_capacity INT NOT NULL, 
website VARCHAR(50) NOT NULL, 
address_id INT, 
CONSTRAINT fk_offices_addresses 
FOREIGN KEY (address_id) 
REFERENCES addresses(id)
);

CREATE TABLE employees(
id INT PRIMARY KEY AUTO_INCREMENT, 
first_name VARCHAR(30) NOT NULL, 
last_name VARCHAR(30) NOT NULL, 
age INT NOT NULL, 
salary DECIMAL(10, 2) NOT NULL, 
job_title VARCHAR(20) NOT NULL, 
happiness_level CHAR(1) NOT NULL
);

CREATE TABLE teams(
id INT PRIMARY KEY AUTO_INCREMENT, 
name VARCHAR(40) NOT NULL, 
office_id INT NOT NULL, 
leader_id INT NOT NULL UNIQUE, 
CONSTRAINT fk_teams_offices 
FOREIGN KEY (office_id) 
REFERENCES offices(id), 
CONSTRAINT fk_teams_leaders 
FOREIGN KEY (leader_id) 
REFERENCES employees(id)
);

CREATE TABLE games(
id INT PRIMARY KEY AUTO_INCREMENT, 
name VARCHAR(50) NOT NULL UNIQUE, 
description TEXT, 
rating FLOAT NOT NULL DEFAULT(5.5), 
budget DECIMAL(10, 2) NOT NULL, 
release_date DATE, 
team_id INT NOT NULL, 
CONSTRAINT fk_games_teams 
FOREIGN KEY (team_id) 
REFERENCES teams(id)
);

CREATE TABLE games_categories(
game_id INT NOT NULL, 
category_id INT NOT NULL, 
PRIMARY KEY (game_id, category_id), 
CONSTRAINT fk_games_categories_games 
FOREIGN KEY (game_id) 
REFERENCES games(id), 
CONSTRAINT fk_games_categories_categories 
FOREIGN KEY (category_id) 
REFERENCES categories(id)
);

-- 02. 
INSERT INTO games (name, rating, budget, team_id) 
SELECT 
REVERSE(SUBSTRING(LOWER(t.name), 2)) AS name, 
t.id AS rating, 
t.leader_id * 1000 AS budget, 
t.id AS team_id 
FROM teams AS t 
WHERE t.id BETWEEN 1 AND 9;

-- 03. 
UPDATE employees AS e 
JOIN teams AS t ON e.id = t.leader_id 
SET e.salary = e.salary + 1000 
WHERE e.age < 40 AND e.salary < 5000;

-- 04. 
DELETE FROM games 
WHERE id NOT IN (SELECT DISTINCT game_id FROM games_categories) AND release_date IS NULL;

-- 05. 
SELECT first_name, last_name, age, salary, happiness_level FROM employees 
ORDER BY salary, id;

-- 06. 
SELECT t.name AS team_name, a.name AS address_name, LENGTH(a.name) AS count_of_characters FROM teams AS t 
JOIN offices AS o ON t.office_id = o.id 
JOIN addresses AS a ON o.address_id = a.id 
WHERE o.website IS NOT NULL 
ORDER BY team_name, address_name;

-- 07. 
SELECT c.name, COUNT(gc.game_id) AS games_count, ROUND(AVG(g.budget), 2) AS avg_budget, MAX(g.rating) AS max_rating 
FROM categories AS c 
JOIN games_categories AS gc ON c.id = gc.category_id 
JOIN games AS g ON gc.game_id = g.id 
GROUP BY c.name 
HAVING MAX(g.rating) >= 9.5 
ORDER BY COUNT(gc.game_id) DESC, c.name;

-- 08. 
SELECT 
CASE
    WHEN RIGHT(g.name, 2) = ' 2' THEN g.name
    ELSE LEFT(g.name, CHAR_LENGTH(g.name) - 2)
END AS name,
g.release_date, 
CONCAT(SUBSTRING(g.description, 1, 10), '...') AS summary, 
	CASE 
	WHEN MONTH(g.release_date) IN (1, 2, 3) THEN 'Q1' 
	WHEN MONTH(g.release_date) IN (4, 5, 6) THEN 'Q2' 
	WHEN MONTH(g.release_date) IN (7, 8, 9) THEN 'Q3' 
	WHEN MONTH(g.release_date) IN (10, 11, 12) THEN 'Q4' 
END AS quarter , 
t.name AS team_name 
FROM games g 
JOIN teams t ON g.team_id = t.id 
WHERE 
YEAR(g.release_date) = 2022 
AND MONTH(g.release_date) % 2 = 0 
AND g.name LIKE '%2%' AND g.name NOT LIKE '%…2%' 
ORDER BY quarter;

-- 09. 
SELECT g.name, CASE 
WHEN g.budget < 50000 THEN "Normal budget" 
WHEN g.budget >= 50000 THEN "Insufficient budget"
END AS budget_level, t.name AS team_name, a.name AS address_name FROM games AS g 
JOIN teams AS t ON g.team_id = t.id 
JOIN offices AS o ON t.office_id = o.id 
JOIN addresses AS a ON o.address_id = a.id 
LEFT JOIN games_categories AS gc ON g.id = gc.game_id 
WHERE g.release_date IS NULL AND gc.game_id IS NULl 
ORDER BY g.name; 

-- 10. 
DELIMITER // 
CREATE FUNCTION udf_game_info_by_name (game_name VARCHAR(20)) 
RETURNS TEXT 
DETERMINISTIC 
BEGIN 
DECLARE game_info VARCHAR(150);
SELECT CONCAT("The ", g.name, " is developed by a ", t.name, " in an office with an address ", a.name) 
INTO game_info FROM games AS g 
JOIN teams AS t ON g.team_id = t.id 
JOIN offices AS o ON t.office_id = o.id 
JOIN addresses AS a ON o.address_id = a.id 
WHERE game_name = g.name; 
RETURN game_info;
END // 
DELIMITER ;

-- TESTS 
SELECT udf_game_info_by_name('Bitwolf') AS info; 
SELECT udf_game_info_by_name('Fix San') AS info;
SELECT udf_game_info_by_name('Job') AS info;

-- 11.
DELIMITER // 
CREATE PROCEDURE udp_update_budget(min_game_rating FLOAT(10, 2))
DETERMINISTIC 
BEGIN 
UPDATE games AS g 
INNER JOIN ( 
SELECT g.id FROM games AS g 
LEFT JOIN games_categories AS gc ON g.id = gc.game_id 
WHERE gc.game_id IS NULL) 
AS no_categories_games ON g.id = no_categories_games.id 
SET g.budget = g.budget + 100000, g.release_date = DATE_ADD(g.release_date, INTERVAL 1 YEAR) 
WHERE g.rating > min_game_rating 
AND g.release_date IS NOT NULL;
END //
DELIMITER ;

-- TESTS 
CALL udp_update_budget (8);
-- 3 row(s) affected !!!!! 
