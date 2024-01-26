CREATE DATABASE softuni_imdb;
USE softuni_imdb;

-- 01. Table Design 
CREATE TABLE countries(
id INT PRIMARY KEY AUTO_INCREMENT, 
name VARCHAR(30) NOT NULL UNIQUE, 
continent VARCHAR(30) NOT NULL, 
currency VARCHAR(5) NOT NULL
);

CREATE TABLE genres(
id INT PRIMARY KEY AUTO_INCREMENT, 
name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE actors(
id INT PRIMARY KEY AUTO_INCREMENT, 
first_name VARCHAR(50) NOT NULL, 
last_name VARCHAR(50) NOT NULL, 
birthdate DATE NOT NULL, 
height INT, 
awards INT, 
country_id INT NOT NULL, 
CONSTRAINT fk_actors_countries 
FOREIGN KEY (country_id) 
REFERENCES countries(id)
);

CREATE TABLE movies_additional_info(
id INT PRIMARY KEY AUTO_INCREMENT, 
rating DECIMAL(10, 2) NOT NULL, 
runtime INT NOT NULL, 
picture_url VARCHAR(80) NOT NULL, 
budget DECIMAL(10, 2), 
release_date DATE NOT NULL, 
has_subtitles BOOLEAN, 
description TEXT
);

CREATE TABLE movies(
id INT PRIMARY KEY AUTO_INCREMENT, 
title VARCHAR(70) NOT NULL UNIQUE, 
country_id INT, 
movie_info_id INT NOT NULL UNIQUE, 
CONSTRAINT fk_movies_countries 
FOREIGN KEY (country_id) 
REFERENCES countries(id), 
CONSTRAINT fk_movies_movies_additional_info 
FOREIGN KEY (movie_info_id) 
REFERENCES movies_additional_info(id)
);

CREATE TABLE movies_actors(
movie_id INT, 
actor_id INT, 
CONSTRAINT fk_movies_actors_movies 
FOREIGN KEY (movie_id) 
REFERENCES movies(id), 
CONSTRAINT fk_movies_actors_actors 
FOREIGN KEY (actor_id) 
REFERENCES actors(id)
);

CREATE TABLE genres_movies(
genre_id INT, 
movie_id INT, 
CONSTRAINT fk_genres_movies_genres 
FOREIGN KEY (genre_id) 
REFERENCES genres(id), 
CONSTRAINT fk_genres_movies_movies 
FOREIGN KEY (movie_id) 
REFERENCES movies(id)
);

-- 02. Insert 
INSERT INTO actors (first_name, last_name, birthdate, height, awards, country_id) 
SELECT 
REVERSE(a.first_name) AS first_name, 
REVERSE(a.last_name) AS last_name, 
DATE_SUB(a.birthdate, INTERVAL 2 DAY) AS birthdate, 
a.height + 10 AS height, 
a.country_id AS awards, 
(SELECT id FROM countries WHERE name = "Armenia")  AS country_id FROM actors AS a 
WHERE a.id <= 10;

-- 03. Update
UPDATE movies_additional_info 
SET runtime = runtime - 10 
WHERE id BETWEEN 15 AND 25;

-- 04. Delete 
DELETE FROM countries 
WHERE id NOT IN (SELECT DISTINCT country_id FROM movies);

-- 05.	Countries
SELECT id, name, continent, currency FROM countries 
ORDER BY currency DESC, id;

-- 06. Old movies 
SELECT mai.id, m.title, mai.runtime, mai.budget, mai.release_date FROM movies_additional_info AS mai 
JOIN movies AS m ON mai.id = m.id 
WHERE YEAR(mai.release_date) BETWEEN 1996 AND 1999 
ORDER BY mai.runtime, mai.id LIMIT 20;

-- 07. Movie casting 
SELECT CONCAT(a.first_name, " ", a.last_name) AS full_name, 
CONCAT(REVERSE(a.last_name), LENGTH(a.last_name), "@cast.com") AS email, 
TIMESTAMPDIFF(YEAR, a.birthdate, '2023-01-01') AS age, 
height 
FROM actors AS a 
LEFT JOIN 
movies_actors AS ma ON a.id = ma.actor_id 
WHERE ma.actor_id IS NULL
ORDER BY height ASC; 

-- 08. International Festival 
SELECT c.name, COUNT(m.id) AS movies_count FROM movies AS m 
JOIN countries AS c ON m.country_id = c.id 
GROUP BY c.name 
HAVING movies_count >= 7 
ORDER BY c.name DESC;

-- 09. Rating system 
SELECT m.title, 
	CASE 
    WHEN rating <= 4 THEN "poor" 
    WHEN rating > 4 AND rating <= 7 THEN "good" 
    WHEN rating > 7 THEN "excellent" 
    END AS rating, 
		CASE 
        WHEN has_subtitles IS TRUE THEN "english" 
        WHEN has_subtitles IS FALSE THEN "-" 
        END AS subtitles , 
		mai.budget 
FROM movies_additional_info AS mai 
JOIN movies AS m ON mai.id = m.id 
ORDER BY budget DESC;

-- 10. History movies
DELIMITER //
CREATE FUNCTION udf_actor_history_movies_count(full_name VARCHAR(50)) 
RETURNS INT 
DETERMINISTIC 
BEGIN 
DECLARE movies_count INT;
SELECT COUNT(*) INTO movies_count
FROM actors AS a 
JOIN movies_actors AS ma ON a.id = ma.actor_id 
JOIN movies AS m ON ma.movie_id = m.id 
JOIN genres_movies AS gm ON ma.movie_id = gm.movie_id 
WHERE CONCAT(a.first_name, " ", a.last_name) = full_name AND gm.genre_id = 12;
  RETURN movies_count;
END //
DELIMITER ;

-- TEST 
SELECT udf_actor_history_movies_count('Stephan Lundberg')  AS 'history_movies';

-- 11. Movie awards 
DELIMITER // 
CREATE PROCEDURE udp_award_movie(movie_title VARCHAR(50)) 
DETERMINISTIC
BEGIN 
UPDATE actors a 
JOIN movies_actors AS ma ON a.id = ma.actor_id 
JOIN movies AS m ON ma.movie_id = m.id 
SET a.awards = awards + 1 
WHERE m.title = movie_title;
END //
DELIMITER ;

-- TEST 
CALL udp_award_movie('Tea For Two');
-- This execution will update 3 actors – Vanna Bilborough, Armando Cabrera, Ingrid Ackenhead
-- Awards before: 20, 18, 24. Awards after: 21, 19, 25.
