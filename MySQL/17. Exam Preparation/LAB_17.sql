CREATE DATABASE universities_db;
USE universities_db;

-- 01.	
CREATE TABLE countries(
id INT PRIMARY KEY AUTO_INCREMENT, 
name VARCHAR(40) NOT NULL UNIQUE
);

CREATE TABLE cities(
id INT PRIMARY KEY AUTO_INCREMENT, 
name VARCHAR(40) NOT NULL UNIQUE, 
population INT, 
country_id INT NOT NULL, 
CONSTRAINT fk_cities_countries 
FOREIGN KEY (country_id) 
REFERENCES countries(id)
);

CREATE TABLE universities(
id INT PRIMARY KEY AUTO_INCREMENT, 
name VARCHAR(60) NOT NULL UNIQUE, 
address VARCHAR(80) NOT NULL UNIQUE, 
tuition_fee DECIMAL(19, 2) NOT NULL, 
number_of_staff INT, 
city_id INT, 
CONSTRAINT fk_universities_cities 
FOREIGN KEY (city_id) 
REFERENCES cities(id)
);

CREATE TABLE students(
id INT PRIMARY KEY AUTO_INCREMENT, 
first_name VARCHAR(40) NOT NULL, 
last_name VARCHAR(40) NOT NULL, 
age INT, 
phone VARCHAR(20) NOT NULL UNIQUE, 
email VARCHAR(255) NOT NULL UNIQUE, 
is_graduated BOOLEAN, 
city_id INT, 
CONSTRAINT fk_students_cities 
FOREIGN KEY (city_id) 
REFERENCES cities(id)
);

CREATE TABLE courses(
id INT PRIMARY KEY AUTO_INCREMENT, 
name VARCHAR(40) NOT NULL UNIQUE, 
duration_hours DECIMAL(19, 2), 
start_date DATE, 
teacher_name VARCHAR(60) NOT NULL UNIQUE, 
description TEXT, 
university_id INT, 
CONSTRAINT fk_courses_universities 
FOREIGN KEY (university_id) 
REFERENCES universities(id) 
);

CREATE TABLE students_courses(
grade DECIMAL (19, 2), 
student_id INT NOT NULL, 
course_id INT NOT NULL, 
CONSTRAINT fk_students_courses_students 
FOREIGN KEY (student_id) 
REFERENCES students(id), 
CONSTRAINT fk_students_courses_courses 
FOREIGN KEY (course_id) 
REFERENCES courses(id)
);

-- 02.	
INSERT INTO courses (name, duration_hours, start_date, teacher_name, description, university_id) 
SELECT 
CONCAT(teacher_name, ' course') AS name, 
LENGTH(name) / 10 AS duration_hours, 
DATE_ADD(start_date, INTERVAL 5 DAY) AS start_date, 
REVERSE(teacher_name) AS teacher_name, 
CONCAT('Course ', teacher_name, REVERSE(description)) AS description, 
DAY(start_date) AS uviversity_id 
FROM courses
WHERE id <= 5;

-- 03.	
UPDATE universities 
SET tuition_fee = tuition_fee + 300 
WHERE id >= 5 AND id < 13;

-- 04.	
DELETE FROM universities 
WHERE number_of_staff IS NULL OR number_of_staff = 0;

-- 05.	
SELECT id, name, population, country_Id FROM cities 
ORDER BY population DESC;

-- 06.	
SELECT first_name, last_name, age, phone, email FROM students 
WHERE age >= 21 
ORDER BY first_name DESC, email ASC, id ASC 
LIMIT 10;

-- 07. 
SELECT CONCAT(first_name, " ", last_name) AS full_name, 
       SUBSTRING(email, 2, 10) AS username, 
       REVERSE(phone) AS password 
FROM students 
WHERE id NOT IN (SELECT student_id FROM students_courses) 
ORDER BY password DESC;

-- 08. 
SELECT COUNT(sc.student_id) AS students_count, u.name AS university_name 
FROM universities AS u 
LEFT JOIN courses AS cr ON u.id = cr.university_id  
LEFT JOIN students_courses AS sc ON cr.id = sc.course_id 
GROUP BY u.id, u.name 
HAVING 
students_count >= 8 
ORDER BY students_count DESC, university_name DESC;

-- 09. 
SELECT u.name AS university_name, c.name AS city_name, u.address, CASE 
WHEN tuition_fee < 800 THEN "cheap" 
WHEN tuition_fee >= 800 AND tuition_fee < 1200 THEN "normal" 
WHEN tuition_fee >= 1200 AND tuition_fee < 2500 THEN "high" 
ELSE "expensive"
END AS price_rank, 
tuition_fee 
FROM universities AS u 
JOIN cities AS c ON u.city_id = c.id 
ORDER BY tuition_fee;

-- 10. 
DELIMITER //
CREATE FUNCTION udf_average_alumni_grade_by_course_name(course_name VARCHAR(60)) 
RETURNS DECIMAL(19, 2) 
DETERMINISTIC
BEGIN 
DECLARE avg_grade DECIMAL(19, 2); 

SELECT AVG(sc.grade) INTO avg_grade FROM students_courses AS sc 
JOIN courses AS c ON sc.course_id = c.id 
JOIN students AS s ON sc.student_id = s.id
WHERE c.name LIKE course_name AND 
s.is_graduated = 1;
RETURN avg_grade;
END //
DELIMITER ;

-- TESTS 
SELECT c.name, udf_average_alumni_grade_by_course_name('Quantum Physics') as average_alumni_grade FROM courses c 
WHERE c.name = 'Quantum Physics';
DROP FUNCTION udf_average_alumni_grade_by_course_name;

-- 11. 
-- Create a stored procedure udp_graduate_all_students_by_year which accepts the following parameters:
-- •	year_started INT
-- Extracts data about all courses that started in the given year, find the assigned students and change their graduated status to true.
-- Result
-- Query
-- CALL udp_graduate_all_students_by_year(2017); 
DELIMITER // 
CREATE PROCEDURE udp_graduate_all_students_by_year(year_started INT)
BEGIN 
UPDATE students 
SET is_graduated = TRUE 
WHERE id IN ( 
SELECT sc.student_id FROM students_courses AS sc 
JOIN courses AS c ON sc.course_id = c.id 
WHERE 
YEAR(c.start_date) = year_started);
END //
DELIMITER ;
