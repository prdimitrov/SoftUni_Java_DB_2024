-- 1.	Mountains and Peaks
CREATE TABLE mountains (
id INT AUTO_INCREMENT,
name VARCHAR(50) NOT NULL, 
CONSTRAINT pk_mountains
PRIMARY KEY (id)
);

create table peaks (
id INT AUTO_INCREMENT PRIMARY KEY, 
name VARCHAR(50) NOT NULL, 
mountain_id INT NOT NULL, 
CONSTRAINT fk_peaks_mountains 
FOREIGN KEY (mountain_id) 
REFERENCES mountains(id)
);

-- 2.	Trip Organization
SELECT driver_id, vehicle_type, CONCAT(first_name, " ", last_name) AS driver_name FROM vehicles
JOIN campers ON 
vehicles.driver_id = campers.id;

-- 3.	SoftUni Hiking
SELECT starting_point AS route_starting_point, end_point AS route_ending_point, 
leader_id, CONCAT(first_name, " ", last_name) AS leader_name 
FROM routes 
JOIN campers ON 
routes.leader_id = campers.id;


-- 4.	Delete Mountains
CREATE TABLE mountains(
id INT PRIMARY KEY AUTO_INCREMENT,
name VARCHAR(20) NOT NULL
);

CREATE TABLE peaks(
id INT PRIMARY KEY AUTO_INCREMENT,
name VARCHAR(50) NOT NULL,
mountain_id INT, 
CONSTRAINT fk_mountains_id 
FOREIGN KEY (mountain_id) 
REFERENCES mountains(id)
ON DELETE CASCADE
);
