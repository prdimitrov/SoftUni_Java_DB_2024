--
CREATE DATABASE exten_db;
USE exten_db;
--
-- 1.	One-To-One Relationship
CREATE TABLE people ( 
person_id INT UNSIGNED UNIQUE NOT NULL AUTO_INCREMENT,
first_name VARCHAR(50) NOT NULL, 
salary DECIMAL(10, 2) NOT NULL DEFAULT 0, 
passport_id INT UNSIGNED UNIQUE NOT NULL);

CREATE TABLE passports ( 
passport_id INT UNSIGNED UNIQUE AUTO_INCREMENT PRIMARY KEY, 
passport_number VARCHAR(10) UNIQUE NOT NULL) AUTO_INCREMENT = 101;

ALTER TABLE people 
ADD CONSTRAINT pk_person 
PRIMARY KEY (person_id), 
ADD CONSTRAINT fk_persons_passports 
FOREIGN KEY(passport_id) 
REFERENCES passports(passport_id);

-- Трябва първо passports, след това people .. 
INSERT INTO passports(passport_id, passport_number) VALUES (101, 'N34FG21B'), (102, 'K65LO4R7'), (103, 'ZE657QP2');
INSERT INTO people(first_name, salary, passport_id) VALUES ('Roberto', 43300, 102), ('Tom', 56100, 103), ('Yana', 60200, 101);

-- 2.	One-To-Many Relationship
CREATE TABLE manufacturers (
manufacturer_id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY, 
name VARCHAR(50) UNIQUE NOT NULL, 
established_on DATE NOT NULL);

CREATE TABLE models (
model_id INT UNSIGNED UNIQUE NOT NULL AUTO_INCREMENT,
name VARCHAR(50) UNIQUE NOT NULL,
manufacturer_id INT UNSIGNED NOT NULL);

ALTER TABLE manufacturers
DROP PRIMARY KEY,
ADD CONSTRAINT pk_manufacturers 
PRIMARY KEY (manufacturer_id);

ALTER TABLE models 
ADD CONSTRAINT pk_models
PRIMARY KEY (model_id),
ADD CONSTRAINT fk_models_manufacturers 
FOREIGN KEY (manufacturer_id) 
REFERENCES manufacturers(manufacturer_id);


INSERT INTO manufacturers (manufacturer_id, name, established_on) 
VALUES 
(1, "BMW", '1916-03-01'), 
(2, "Tesla", '2003-01-01'), 
(3, "Lada", '1966-05-01');

INSERT INTO models (model_id, name, manufacturer_id)
VALUES
(101, "X1", 1), 
(102, "i6", 1), 
(103, "Model S", 2), 
(104, "Model X", 2), 
(105, "Model 3", 2), 
(106, "Nova", 3);


-- 3.	Many-To-Many Relationship
CREATE TABLE  exams(
exam_id INT UNSIGNED UNIQUE AUTO_INCREMENT, 
name VARCHAR(100) NOT NULL);

CREATE TABLE students(
student_id INT UNSIGNED UNIQUE AUTO_INCREMENT, 
name VARCHAR(50) NOT NULL);

CREATE TABLE students_exams(
student_id INT UNSIGNED, 
exam_id INT UNSIGNED NOT NULL);

ALTER TABLE exams
ADD CONSTRAINT pk_exams 
PRIMARY KEY (exam_id);

ALTER TABLE students
ADD CONSTRAINT pk_students
PRIMARY KEY (student_id);

ALTER TABLE students_exams
ADD CONSTRAINT pk_students_exams
PRIMARY KEY (student_id, exam_id),
ADD CONSTRAINT fk_students_exams_students
FOREIGN KEY (student_id) 
REFERENCES students(student_id),
ADD CONSTRAINT fk_students_exams_exams
FOREIGN KEY (exam_id)
REFERENCES exams(exam_id);

INSERT INTO exams (exam_id, name) VALUES 
(101, "Spring MVC"), 
(102, "Neo4j"), 
(103, "Oracle 11g");

INSERT INTO students (student_id, name) VALUES 
(1, "Mila"),
(2, "Toni"),
(3, "Ron");

INSERT INTO students_exams (student_id, exam_id) VALUES 
(1, 101),
(1, 102),
(2, 101),
(3, 103),
(2, 102),
(2, 103);

-- 4.	Self-Referencing
CREATE TABLE teachers (
teacher_id INT UNSIGNED NOT NULL UNIQUE AUTO_INCREMENT, 
name VARCHAR(50) NOT NULL, 
manager_id INT UNSIGNED DEFAULT NULL) AUTO_INCREMENT = 101;
-- Първо Insert'а..
INSERT INTO teachers(name, manager_id) 
VALUES 
("John", NULL), 
("Maya", 106), 
("Silvia", 106), 
("Ted", 105), 
("Mark", 101), 
("Greta", 101);

ALTER TABLE teachers 
ADD CONSTRAINT pk_teachers
PRIMARY KEY (teacher_id), 
ADD CONSTRAINT pk_teacher_manager_id
FOREIGN KEY (manager_id) 
REFERENCES teachers(teacher_id);

-- 5.	Online Store Database
--
CREATE DATABASE online_store_db;
USE online_store_db;
--


CREATE TABLE item_types (
item_type_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT, 
name VARCHAR(50) NOT NULL
);

CREATE TABLE items (
item_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT, 
name VARCHAR(50) NOT NULL, 
item_type_id INT NOT NULL, 
CONSTRAINT fk_items_item_types 
FOREIGN KEY (item_type_id) 
REFERENCES item_types (item_type_id) 
);

CREATE TABLE cities (
city_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT, 
name VARCHAR(50) NOT NULL
);

CREATE TABLE customers (
customer_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
name VARCHAR(50) NOT NULL, 
birthday DATE, 
city_id INT NOT NULL, 
CONSTRAINT fk_customers_cities 
FOREIGN KEY (city_id) 
REFERENCES cities(city_id)
);

CREATE TABLE orders (
order_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT, 
customer_id INT NOT NULL, 
CONSTRAINT fk_orders_customers 
FOREIGN KEY (customer_id) 
REFERENCES customers(customer_id)
);

CREATE TABLE order_items (
order_id INT NOT NULL, 
item_id INT NOT NULL, 
CONSTRAINT pk_order_items 
PRIMARY KEY (order_id, item_id), 
CONSTRAINT fk_order_items_orders 
FOREIGN KEY (order_id) 
REFERENCES orders(order_id), 
CONSTRAINT fk_order_items_items 
FOREIGN KEY (item_id) 
REFERENCES items(item_id)
);

-- 6.	University Database
--
CREATE DATABASE university_db;
USE university_db;
--

CREATE TABLE subjects(
subject_id INT(11) NOT NULL AUTO_INCREMENT,
subject_name VARCHAR(50) NOT NULL, 
CONSTRAINT pk_subjects
PRIMARY KEY (subject_id)
);

CREATE TABLE majors(
major_id INT(11) NOT NULL AUTO_INCREMENT,
name VARCHAR(50) NOT NULL, 
CONSTRAINT pk_majors 
PRIMARY KEY (major_id)
);

CREATE TABLE students(
student_id INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT, 
student_number VARCHAR(12) NOT NULL, 
student_name VARCHAR(50) NOT NULL, 
major_id INT(11) NOT NULL, 
CONSTRAINT fk_students_majors 
FOREIGN KEY (major_id) 
REFERENCES majors(major_id)
);

CREATE TABLE payments(
payment_id INT(11) NOT NULL AUTO_INCREMENT, 
payment_date DATE NOT NULL, 
payment_amount DECIMAL(8, 2) NOT NULL, 
student_id INT(11) NOT NULL, 
CONSTRAINT pk_payments 
PRIMARY KEY (payment_id), 
CONSTRAINT fk_payments_students 
FOREIGN KEY (student_id) 
REFERENCES students(student_id)
);

CREATE TABLE agenda(
student_id INT(11) NOT NULL, 
subject_id INT(11) NOT NULL, 
CONSTRAINT pk_agenda 
PRIMARY KEY (student_id, subject_id), 
CONSTRAINT fk_agendas_students 
FOREIGN KEY (student_id) 
REFERENCES students(student_id), 
CONSTRAINT fk_agendas_subjects 
FOREIGN KEY (subject_id) 
REFERENCES subjects(subject_id)
);

-- 7.	SoftUni Design


-- 8.	Geography Design


-- 9.	Peaks in Rila
--
USE geography;
--

SELECT m.mountain_range, p.peak_name, p.elevation 
FROM mountains AS m JOIN 
peaks as p on m.id = p.mountain_id 
WHERE m.mountain_range = "Rila" 
ORDER BY p.elevation DESC;
