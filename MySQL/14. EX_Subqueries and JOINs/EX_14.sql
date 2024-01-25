-- 1.	Employee Address
SELECT e.employee_id, e.job_title, e.address_id, a.address_text 
FROM employees AS e 
JOIN addresses AS a ON e.address_id = a.address_id 
ORDER BY e.address_id 
LIMIT 5;

-- 2.	Addresses with Towns
SELECT e.first_name, e.last_name, t.name, a.address_text 
FROM employees AS e 
JOIN addresses AS a ON e.address_id = a.address_id 
JOIN towns AS t ON a.town_id = t.town_id 
ORDER BY first_name, last_name LIMIT 5;

-- 3.	Sales Employee
SELECT e.employee_id, e.first_name, e.last_name, d.name 
FROM employees AS e 
JOIN departments AS d ON e.department_id = d.department_id 
WHERE e.department_id = 3 
ORDER BY e.employee_id DESC;

-- 4.	Employee Departments
SELECT e.employee_id, e.first_name, e.salary, d.name 
FROM employees AS e 
JOIN departments AS d ON e.department_id = d.department_id 
WHERE e.salary > 15000 
ORDER BY d.department_id DESC 
LIMIT 5;

-- 5.	Employees Without Project
SELECT e.employee_id, e.first_name 
FROM employees AS e 
LEFT JOIN employees_projects AS ep ON e.employee_id = ep.employee_id 
WHERE ep.employee_id IS NULL 
ORDER BY employee_id DESC LIMIT 3;

-- 6.	Employees Hired After
SELECT e.first_name, e.last_name, e.hire_date, d.name AS dept_name
FROM employees AS e 
JOIN departments AS d ON e.department_id = d.department_id 
WHERE  e.hire_date > 1999-01-01 AND d.name IN ("Sales", "Finance")
ORDER BY hire_date;

-- 7.	Employees with Project
SELECT e.employee_id, e.first_name, p.name AS project_name 
FROM employees AS e 
JOIN employees_projects AS ep ON ep.employee_id = e.employee_id 
JOIN projects AS p ON ep.project_id = p.project_id 
WHERE DATE(p.start_date) > '2002-08-13' AND p.end_date IS NULL 
ORDER BY e.first_name, p.name LIMIT 5;

-- 8.	Employee 24
SELECT e.employee_id, e.first_name, IF(YEAR(p.start_date) >= 2005, NULL, p.name) AS project_name 
FROM employees AS e 
JOIN employees_projects AS ep ON ep.employee_id = e.employee_id 
JOIN projects AS p ON ep.project_id = p.project_id 
WHERE e.employee_id = 24 ORDER BY p.name;

-- 9.	Employee Manager
SELECT e.employee_id, e.first_name, e.manager_id, em.first_name AS manager_name
FROM employees AS e
JOIN employees AS em ON e.manager_id = em.employee_id
WHERE e.manager_id IN (3, 7)
ORDER BY e.first_name;

-- 10.	Employee Summary
SELECT e.employee_id, 
CONCAT(e.first_name, " ", e.last_name), 
CONCAT(em.first_name, " ", em.last_name) AS manager_name, 
d.name AS department_name FROM employees AS e 
JOIN departments AS d ON e.department_id = d.department_id 
JOIN employees AS em ON e.manager_id = em.employee_id 
ORDER BY employee_id LIMIT 5;

-- 11.	Min Average Salary
SELECT AVG(e.salary) AS min_average_salary 
FROM employees AS e 
GROUP BY e.department_id 
ORDER BY min_average_salary LIMIT 1;

-- 12.	Highest Peaks in Bulgaria
--
USE geography;
--

SELECT c.country_code, mountain_range, p.peak_name, p.elevation 
FROM countries AS c 
JOIN mountains_countries AS mc ON c.country_code = mc.country_code 
JOIN peaks AS p ON mc.mountain_id = p.mountain_id 
JOIN mountains AS m ON mc.mountain_id = m.id 
WHERE c.country_name = "Bulgaria" AND elevation > 2835 
ORDER BY elevation DESC;

-- 13.	Count Mountain Ranges
-- Write a query that selects:
SELECT c.country_code, COUNT(mc.mountain_id) AS mountain_range 
FROM countries AS c 
JOIN mountains_countries AS mc ON c.country_code = mc.country_code 
JOIN mountains AS m ON mc.mountain_id = m.id 
WHERE c.country_name IN ("United States", "Russia", "Bulgaria")
GROUP BY c.country_code 
ORDER BY mountain_range DESC;

-- 14.	Countries with Rivers
-- Write a query that selects:
SELECT c.country_name, r.river_name 
FROM countries AS c 
LEFT JOIN countries_rivers AS cr 
ON c.country_code = cr.country_code 
LEFT JOIN rivers AS r 
ON cr.river_id = r.id 
WHERE c.continent_code = "AF"
ORDER BY country_name LIMIT 5;

-- 15.	*Continents and Currencies
SELECT c.continent_code, currency_code, COUNT(*) AS currency_usage 
FROM countries AS c 
GROUP BY c.continent_code, c.currency_code 
HAVING currency_usage > 1 
AND currency_usage = (SELECT COUNT(*) AS cu FROM countries AS c2 
WHERE c2.continent_code = c.continent_code 
GROUP BY c2.currency_code ORDER BY cu DESC LIMIT 1) 
ORDER BY c.continent_code, c.continent_code;

-- 16.  Countries Without Any Mountains
SELECT COUNT(*) AS country_count 
FROM (SELECT mc.country_code AS mc_country_code FROM mountains_countries AS mc GROUP BY mc.country_code) AS mcc 
RIGHT JOIN countries AS c ON mcc.mc_country_code = c.country_code WHERE 
mcc.mc_country_code IS NULL;

-- 17.  Highest Peak and Longest River by Country
SELECT c.country_name, MAX(p.elevation) AS highest_peak_elevation, MAX(r.length) AS longest_river_length FROM countries AS c 
JOIN countries_rivers AS cr ON c.country_code = cr.country_code 
RIGHT JOIN rivers AS r ON cr.river_id = r.id 
JOIN mountains_countries AS mc ON c.country_code = mc.country_code 
JOIN mountains AS m ON mc.mountain_id = m.id
RIGHT JOIN peaks AS p ON m.id = p.mountain_id 
GROUP BY c.country_code 
ORDER BY highest_peak_elevation DESC, longest_river_length DESC, c.country_name ASC 
LIMIT 5;
