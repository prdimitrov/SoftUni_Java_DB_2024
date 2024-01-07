--
USE hotel;
--

-- Problem 1: Select Employee Information
SELECT id, first_name, last_name, job_title FROM employees ORDER BY id ASC;

-- Problem 2: Select Employees with Filter
SELECT id, CONCAT(first_name, ' ', last_name) AS full_name, job_title, salary
FROM employees
WHERE salary > 1000.00
ORDER BY id ASC;

-- Problem 3: Update Employees Salary
UPDATE employees
SET salary = salary + 100
WHERE job_title = "Manager";
SELECT salary FROM employees;

-- Problem 4: Top Paid Employee
SELECT * FROM employees ORDER BY salary DESC LIMIT 1;

-- Problem 5: Select Employees by Multiple Filters
SELECT * FROM employees WHERE department_id = 4 AND salary >= 1000 ORDER BY id;

-- Problem 6: Delete from Table
DELETE FROM employees WHERE department_Id = 1 OR department_id = 2 ORDER BY id;
SELECT * FROM employees ORDER BY id;
