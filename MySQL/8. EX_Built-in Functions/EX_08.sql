-- 1.	Find Names of All Employees by First Name
SELECT first_name, last_name FROM employees WHERE SUBSTRING(first_name, 1, 2) = "Sa"
ORDER BY employee_id;

-- 2.	Find Names of All Employees by Last Name
SELECT first_name, last_name FROM employees WHERE last_name LIKE "%ei%";

-- 3.	Find First Names of All Employees
SELECT first_name FROM employees WHERE department_id IN (3, 10)
AND YEAR(hire_date) >= 1995 AND YEAR(hire_date) <= 2005
ORDER BY department_id DESC;

-- 4.	Find All Employees Except Engineers
SELECT first_name, last_name FROM employees WHERE job_title NOT LIKE "%engineer%"
ORDER BY employee_id;

-- 5.	Find Towns with Name Length
SELECT name FROM towns WHERE LENGTH(name) IN (5, 6) ORDER BY name ASC;

-- 6.	 Find Towns Starting With
SELECT town_id, name FROM towns WHERE SUBSTRING(name, 1, 1)
IN ("M", "K", "B", "E") ORDER BY name ASC;

-- 7.	 Find Towns Not Starting With
SELECT town_id, name FROM towns WHERE SUBSTRING(name, 1, 1)
NOT IN ("R", "B", "D") ORDER BY name ASC;

-- 8.	Create View Employees Hired After 2000 Year
CREATE VIEW v_employees_hired_after_2000
AS SELECT first_name, last_name FROM employees WHERE YEAR(hire_date) > 2000;

-- 9.	Length of Last Name
SELECT first_name, last_name FROM employees WHERE LENGTH(last_name) = 5;

-- 10.	Countries Holding 'A' 3 or More Times
--
USE geography;
--

SELECT country_name, iso_code FROM countries
WHERE LENGTH(country_name) - LENGTH(REPLACE(UPPER(country_name), 'A', '')) >= 3
ORDER BY iso_code ASC;
/* ИЛИ
SELECT country_name, iso_code FROM countries
WHERE LENGTH(country_name) - LENGTH(REPLACE(LOWER(country_name), 'a', '')) >= 3
ORDER BY iso_code ASC;
*/

-- 11.	 Mix of Peak and River Names
SELECT peaks.peak_name, rivers.river_name, LOWER(CONCAT(peaks.peak_name, SUBSTRING(rivers.river_name, 2))) AS mix
FROM peaks
JOIN rivers ON SUBSTRING(peaks.peak_name, -1) = SUBSTRING(rivers.river_name, 1, 1)
ORDER BY mix;

-- 12.	Games from 2011 and 2012 Year
--
USE diablo;
--

SELECT name, DATE_FORMAT(start, '%Y-%m-%d') AS start FROM games WHERE YEAR(start) >= 2011 AND YEAR(start) <= 2012
ORDER BY start LIMIT 50;

-- 13.	 User Email Providers
SELECT user_name, SUBSTRING_INDEX(email, '@', -1) AS "email provider" FROM users
ORDER BY SUBSTRING_INDEX(email, '@', -1), user_name;

-- 14.	 Get Users with IP Address Like Pattern
SELECT user_name, ip_address FROM users
WHERE ip_address LIKE "___.1%.%.___"
ORDER BY user_name;

-- 15.	 Show All Games with Duration and Part of the Day
SELECT name,
  CASE
    WHEN HOUR(g.start) BETWEEN 0 AND 11 THEN "Morning"
    WHEN HOUR(g.start) BETWEEN 12 AND 17 THEN "Afternoon"
    WHEN HOUR(g.start) BETWEEN 18 AND 23 THEN "Evening"
  END AS "Part of the Day",
  
  CASE
    WHEN g.duration <= 3 THEN "Extra Short"
    WHEN g.duration >= 3 AND g.duration <= 6 THEN "Short"
    WHEN g.duration >=6 AND g.duration <= 10 THEN "Long"
    ELSE "Extra Long"
  END AS "Duration"
FROM games AS g;

-- 16.	 Orders Table
--
USE orders;
--

SELECT product_name, order_date,
DATE_ADD(order_date, INTERVAL 3 DAY) AS "pay_due",
DATE_ADD(order_date, INTERVAL 1 MONTH) AS "deliver_due"
FROM orders;
