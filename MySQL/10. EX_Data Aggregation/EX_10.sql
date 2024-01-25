--
USE gringotts;
--

-- 1. Records' Count
SELECT COUNT(id) AS count FROM wizzard_deposits;

-- 2. Longest Magic Wand
SELECT magic_wand_size AS longest_magic_wand
FROM wizzard_deposits ORDER BY longest_magic_wand DESC LIMIT 1;

-- 3. Longest Magic Wand Per Deposit Groups
SELECT deposit_group,
 MAX(magic_wand_size) AS longest_magic_wand
 FROM wizzard_deposits GROUP BY deposit_group
 ORDER BY longest_magic_wand ASC, deposit_group ASC;

-- 4. Smallest Deposit Group Per Magic Wand Size*
SELECT deposit_group FROM wizzard_deposits
GROUP BY deposit_group LIMIT 1;

-- 5. Deposits Sum
SELECT deposit_group, SUM(deposit_amount) AS "total_sum" FROM wizzard_deposits
GROUP BY deposit_group ORDER BY total_sum ASC;

-- 6. Deposits Sum for Ollivander Family
SELECT deposit_group, SUM(deposit_amount) AS "total_sum" FROM wizzard_deposits
WHERE magic_wand_creator = "Ollivander family"
GROUP BY deposit_group ORDER BY deposit_group;

-- 7. Deposits Filter
SELECT deposit_group, SUM(deposit_amount) AS total_sum
FROM wizzard_deposits
WHERE magic_wand_creator = "Ollivander family"
GROUP BY deposit_group 
HAVING total_sum < 150000
ORDER BY total_sum DESC;

-- 8. Deposit Charge
SELECT deposit_group, magic_wand_creator, MIN(deposit_charge) AS min_deposit_charge
 FROM wizzard_deposits
 GROUP BY deposit_group, magic_wand_creator
 ORDER BY magic_wand_creator, deposit_group;
 
-- 9. Age Groups
SELECT 
CASE
WHEN age BETWEEN 0 AND 10 THEN "[0-10]"
WHEN age BETWEEN 11 AND 20 THEN "[11-20]"
WHEN age BETWEEN 21 AND 30 THEN "[21-30]"
WHEN age BETWEEN 31 AND 40 THEN "[31-40]"
WHEN age BETWEEN 41 AND 50 THEN "[41-50]"
WHEN age BETWEEN 51 AND 60 THEN "[51-60]"
ELSE "[61+]"
END AS age_group,
COUNT(*) AS wizard_count
FROM wizzard_deposits
GROUP BY
CASE
WHEN age BETWEEN 0 AND 10 THEN "[0-10]"
WHEN age BETWEEN 11 AND 20 THEN "[11-20]"
WHEN age BETWEEN 21 AND 30 THEN "[21-30]"
WHEN age BETWEEN 31 AND 40 THEN "[31-40]"
WHEN age BETWEEN 41 AND 50 THEN "[41-50]"
WHEN age BETWEEN 51 AND 60 THEN "[51-60]"
ELSE "[61+]"
END
ORDER BY wizard_count;
-- 10. First Letter
SELECT DISTINCT SUBSTRING(first_name, 1, 1) FROM wizzard_deposits
WHERE deposit_group = "Troll Chest" GROUP BY first_name;

-- 11. Average Interest 
SELECT 
deposit_group,
is_deposit_expired,
AVG(deposit_interest) AS deposit_interest
FROM
wizzard_deposits
WHERE
deposit_start_date > '1985-01-01'
GROUP BY deposit_group, is_deposit_expired
ORDER BY deposit_group DESC , is_deposit_expired;

-- 12. Employees Minimum Salaries
--
USE soft_uni;
--
SELECT department_id, MIN(salary) AS minimmum_salary 
FROM employees
WHERE department_id IN (2, 5, 7) 
AND 
hire_date > 2000-01-01
GROUP BY department_id;

-- 13. Employees Average Salaries
SELECT department_id, 
CASE
WHEN department_id = 1 THEN AVG(salary) + 5000
ELSE AVG(salary)
END AS avg_salary
FROM employees
WHERE salary > 30000 AND manager_id != 42
GROUP BY department_id
ORDER BY department_id;

-- 14. Employees Maximum Salaries
SELECT department_id, MAX(salary) AS max_salary
FROM employees
GROUP BY department_id
HAVING 
MAX(salary) NOT BETWEEN 30000 AND 70000
ORDER BY department_id;

-- 15. Employees Count Salaries
SELECT COUNT(*) FROM employees WHERE manager_id IS NULL;

-- 16. 3rd Highest Salary*
SELECT department_id, MAX(salary) AS third_highest_salary
FROM employees e1
WHERE(SELECT COUNT(DISTINCT salary) FROM employees e2
WHERE e2.department_id = e1.department_id AND e2.salary > e1.salary) = 2
GROUP BY department_id
ORDER BY department_id;

-- 17. Salary Challenge**
SELECT first_name, last_name, department_id
FROM employees e
WHERE e.salary > (SELECT AVG(e2.salary) FROM employees e2
WHERE e2.department_id = e.department_id)
ORDER BY department_id, employee_id LIMIT 10;

-- 18. Departments Total Salaries
SELECT department_id, SUM(salary) AS total_salary FROM employees
GROUP BY department_id
ORDER BY department_id;
