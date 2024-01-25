- 1.	Employees with Salary Above 35000
DELIMITER $$
CREATE PROCEDURE usp_get_employees_salary_above_35000()
BEGIN 
	SELECT e.first_name, e.last_name 
    FROM employees AS e 
    WHERE e.salary > 35000
    ORDER BY first_name, last_name, e.employee_id;
END $$
DELIMITER ;

CALL usp_get_employees_salary_above_35000;

-- 2.	Employees with Salary Above Number
DELIMITER $$
CREATE PROCEDURE usp_get_employees_salary_above(max_salary DECIMAL(19, 4))
DETERMINISTIC
BEGIN 
SELECT e.first_name, e.last_name FROM employees AS e 
WHERE e.salary >= max_salary 
ORDER BY e.first_name, e.last_name, e.employee_id;
END $$
DELIMITER ;

CALL usp_get_employees_salary_above(45000);

-- 3.	Town Names Starting With
DELIMITER $$
CREATE PROCEDURE usp_get_towns_starting_with(town_start TEXT) 
BEGIN 
SELECT t.name AS town_name FROM towns AS t
WHERE t.name LIKE CONCAT(town_start, '%') 
ORDER BY town_name;
END $$
DELIMITER ;

CALL usp_get_towns_starting_with('b');

DROP PROCEDURE IF EXISTS usp_get_towns_starting_with;

-- 4.	Employees from Town
DELIMITER //
CREATE PROCEDURE usp_get_employees_from_town (town_name TEXT) 
BEGIN 
SELECT e.first_name, e.last_name FROM employees AS e 
JOIN addresses AS a ON e.address_id = a.address_id 
JOIN towns AS t ON a.town_id = t.town_id 
WHERE town_name = t.name 
ORDER BY first_name, last_name;
END //
DELIMITER ;

CALL usp_get_employees_from_town("Sofia");

-- 5.	Salary Level Function
DELIMITER //
CREATE FUNCTION ufn_get_salary_level (salary DOUBLE) 
RETURNS VARCHAR(10)
DETERMINISTIC
BEGIN
	DECLARE salary_level VARCHAR(10);
    IF salary < 30000 THEN 
    SET salary_level = "Low";
    ELSEIF salary >= 30000 AND salary <= 50000 THEN 
    SET salary_level = "Average";
    ELSE SET salary_level = "High";
    END IF;
    RETURN salary_level;
END //
DELIMITER ;

-- ВТОРИ НАЧИН!!!!!!!!!!!!
DELIMITER //
CREATE FUNCTION ufn_get_salary_level(salary DOUBLE) 
RETURNS VARCHAR(10)
DETERMINISTIC
BEGIN 
DECLARE salary_level VARCHAR(10);
	CASE 
    WHEN salary < 30000 THEN SET salary_level = "Low"; 
    WHEN salary >= 30000 AND salary <= 50000 THEN SET salary_level = "Average";
    WHEN salary > 50000 THEN SET salary_level = "High";
    END CASE;
    RETURN salary_level;
END //
DELIMITER ;

-- ТРЕТИ НАЧИН
DELIMITER // 
CREATE FUNCTION ufn_get_salary_level(salary DOUBLE) 
RETURNS VARCHAR(10)
DETERMINISTIC
BEGIN 
DECLARE salary_level VARCHAR(10);
    IF 
    salary < 30000 THEN SET salary_level := "Low"; 
    ELSEIF 
    salary >= 30000 AND salary <= 50000 THEN SET salary_level := "Average"; 
    ELSE 
    SET salary_level := "High";
    END IF;
    RETURN salary_level;
END // 
DELIMITER ; 

-- Some tests 
SELECT e.salary(ufn_get_salary_level);
SELECT e.employee_id, e.salary, ufn_get_salary_level(e.salary) AS salary_level FROM employees AS e 
WHERE employee_id = 150;

SELECT salary, ufn_get_salary_level(salary) AS salary_level 
FROM (SELECT 13500.00 AS salary 
UNION SELECT 43300.00 
UNION SELECT 125500.00) AS salary_values;

-- 6.	Employees by Salary Level
DELIMITER // 
CREATE PROCEDURE usp_get_employees_by_salary_level(salary_level VARCHAR(10)) 
BEGIN 
SELECT e.first_name, e.last_name FROM employees AS e 
	WHERE e.salary < 30000 AND salary_level = "Low" OR
    e.salary >= 30000 AND e.salary <= 50000 AND salary_level = "Average" OR 
    e.salary > 50000 AND salary_level = "High"
ORDER BY first_name DESC, last_name DESC;
END //
DELIMITER ;

-- TEST PROCEDURE
CALL usp_get_employees_by_salary_level("High");

-- 7.	Define Function
DELIMITER //
CREATE FUNCTION ufn_is_word_comprised(set_of_letters varchar(50), word varchar(50)) 
RETURNS BIT
DETERMINISTIC
BEGIN
RETURN word REGEXP CONCAT("^[", set_of_letters, "]+$");
END //
DELIMITER ;

SELECT ufn_is_word_comprised("bobr", "Rob");

-- 8.	Find Full Name
DELIMITER // 
CREATE PROCEDURE usp_get_holders_full_name()
BEGIN 
SELECT CONCAT(first_name, " ", last_name) AS full_name FROM account_holders
ORDER BY full_name ASC, id DESC;
END //
DELIMITER ;

CALL usp_get_holders_full_name();


-- 9.	People with Balance Higher Than
DELIMITER // 
CREATE PROCEDURE usp_get_holders_with_balance_higher_than(input_balance DECIMAL(19, 4)) 
BEGIN 
    SELECT ah.first_name, ah.last_name
    FROM account_holders AS ah 
    JOIN accounts AS a ON ah.id = a.account_holder_id 
    GROUP BY ah.id, ah.first_name, ah.last_name
    HAVING SUM(a.balance) > input_balance
    ORDER BY ah.id;
END //
DELIMITER ;

CALL usp_get_holders_with_balance_higher_than(7000);
-- 10.	Future Value Function
DELIMITER // 
CREATE FUNCTION ufn_calculate_future_value(initial_sum DECIMAL(19, 4), yir_input DOUBLE, noy_input INT) 
RETURNS DECIMAL(19, 4)
DETERMINISTIC
BEGIN 
RETURN initial_sum * (POW(1 + yir_input, noy_input));
END //
DELIMITER ;

-- TEST VALUE!!
SELECT ufn_calculate_future_value(1000, 0.5, 5);


-- 11.	Calculating Interest
DELIMITER // 
CREATE PROCEDURE usp_calculate_future_value_for_account(account_id INT, interest_rate DECIMAL(19, 4)) 
BEGIN 
	SELECT 
    a.id AS account_id, ah.first_name, ah.last_name, a.balance AS current_balance, 
    CAST(balance * (POW(1 + interest_rate, 5)) AS DECIMAL(19, 4)) AS balance_in_5_years FROM account_holders AS ah 
    JOIN accounts AS a ON ah.id = a.account_holder_id 
    WHERE a.id = account_id;
END // 
DELIMITER ;

CALL usp_calculate_future_value_for_account(1, 0.1);

-- 12.	Deposit Money
DELIMITER // 
CREATE PROCEDURE usp_deposit_money(account_id INT, money_amount DECIMAL(19, 4)) 
BEGIN 
IF money_amount > 0 THEN
START TRANSACTION;
UPDATE accounts AS a 
SET a.balance = a.balance + money_amount WHERE 
a.id = account_id;
	IF (SELECT a.balance FROM accounts AS a WHERE a.id = account_id) < 0 THEN ROLLBACK;
ELSE COMMIT;
END IF;
END IF;
END // 
DELIMITER ;

CALL usp_deposit_money(1, 10);
SELECT * FROM accounts WHERE id = 1;

-- 13.	Withdraw Money
DELIMITER // 
CREATE PROCEDURE usp_withdraw_money(account_id INT, money_amount DECIMAL(19, 4)) 
BEGIN 
IF money_amount > 0 THEN START TRANSACTION;
UPDATE accounts AS a 
SET a.balance = a.balance - money_amount 
WHERE a.id = account_id;
IF (SELECT a.balance FROM accounts AS a WHERE a.id = account_id) < 0 THEN ROLLBACK;
ELSE COMMIT;
END IF;
END IF;
END //
DELIMITER ;

CALL usp_withdraw_money(1, 10);
SELECT * FROM accounts WHERE id = 1;

-- 14.	Money Transfer
DELIMITER // 
CREATE PROCEDURE usp_transfer_money(from_account_id INT, to_account_id INT, money_amount DECIMAL(19, 4)) 
BEGIN 
IF money_amount > 0 AND from_account_id != to_account_id AND 
(SELECT a.id FROM accounts AS a WHERE a.id = from_account_id) IS NOT NULL AND 
(SELECT a.id FROM accounts AS a WHERE a.id = to_account_id) IS NOT NULL AND 
(SELECT a.balance FROM accounts AS a WHERE a.id = from_account_id) >= money_amount THEN START TRANSACTION;

UPDATE accounts AS a 
SET a.balance = a.balance + money_amount 
WHERE a.id = to_account_id; 
UPDATE accounts AS a 
SET a.balance = a.balance - money_amount 
WHERE a.id = from_account_id;

IF (SELECT a.balance FROM accounts AS a WHERE a.id = from_account_id) < 0 THEN ROLLBACK;
ELSE COMMIT;

END IF;
END IF;
END //
DELIMITER ;

-- TEST
CALL usp_transfer_money(1, 2, 10);
SELECT id, account_holder_id, balance FROM accounts WHERE id IN (1, 2);
