import DBConn.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class _09_Increase_Age_Stored_Procedure {
    public static void main(String[] args) throws SQLException {
        /*
        Create a stored procedure usp_get_older (directly in the database using MySQL Workbench or any other similar tool)
        that receives a minion_id and increases the minion’s years by 1.
        Write a program that uses that stored procedure to increase the age of a minion,
        whose id will be given as input from the console.
        After that print the name and the age of that minion.
         */

        /*
        MySQL:
DELIMITER //
CREATE PROCEDURE usp_get_older(minion_id INT)
DETERMINISTIC
BEGIN
UPDATE minions
SET age = age + 1 WHERE id = minion_id;
END //
DELIMITER ;
         */
        Scanner sc = new Scanner(System.in);
        int inputMinionId = Integer.parseInt(sc.nextLine());
        Connection conn = DatabaseConnector.datataseConnection("minions_db");
        PreparedStatement callProcedure = conn.prepareStatement("CALL usp_get_older(?);");
        callProcedure.setInt(1, inputMinionId);
        callProcedure.executeUpdate();
        PreparedStatement selectMinion = conn.prepareStatement("SELECT name, age FROM minions where id = ?;");
        selectMinion.setInt(1, inputMinionId);
        ResultSet minionSet = selectMinion.executeQuery();
        if (minionSet.next()) {
            System.out.println(minionSet.getString("name") + " " + minionSet.getInt("age"));
        } else {
            System.out.println("Minion not found!");
        }
    }
}
