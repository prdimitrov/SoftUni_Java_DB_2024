import DBConn.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class _05_Change_Town_Names_Casing {
    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);
        Connection conn = DatabaseConnector.datataseConnection("minions_db");

        /*
        Write a program that changes all town names to uppercase for a given country.
        Print the number of towns that were changed in the format provided in the examples.
        On the next line print the names that were changed, separated by a coma and a space.
Example
Input:	    Output:
Bulgaria	3 town names were affected.
            [SOFIA, PLOVDIV, BURGAS]
Input:	    Output:
Italy	    No town names were affected.

         */
        String inputCountryName = sc.nextLine();

        List<String> townsList = new ArrayList<>();
        int countOfAffectedTowns = 0;
        PreparedStatement selectTowns = conn.prepareStatement("SELECT name FROM towns WHERE country = ?;");
        selectTowns.setString(1, inputCountryName);
        ResultSet townsSet = selectTowns.executeQuery();
        while (townsSet.next()) {
                townsList.add(townsSet.getString("name"));
            }
            for (String town : townsList) {
                String currentTown = town;
                if (!currentTown.equals(currentTown.toUpperCase())) {
                    PreparedStatement updateTown = conn.prepareStatement("UPDATE towns " +
                            "SET name = ? WHERE name = ? AND country = ?");
                    updateTown.setString(1, currentTown.toUpperCase());
                    updateTown.setString(2, currentTown);
                    updateTown.setString(3, inputCountryName);
                    updateTown.executeUpdate();
                    townsList.set(countOfAffectedTowns, town.toUpperCase());
                    countOfAffectedTowns++;
                }
            }
            if (countOfAffectedTowns == 0) {

                System.out.println("No town names were affected.");
            } else {
        System.out.println(countOfAffectedTowns + " town names were affected.");
        System.out.println("[" + String.join(", ", townsList) + "]");
        }
    }
}
