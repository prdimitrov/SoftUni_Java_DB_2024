import DBConn.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class _04_AddMinion {

    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);
        /*
        Write a program that reads information about a minion and its villain and adds it to the database.
        In case the town of the minion is not in the database, insert it as well.
        In case the villain is not present in the database, add him too with the default evilness factor of “evil”.
        Finally, set the new minion to be a servant of the villain.
        Print appropriate messages after each operation – see the examples.
         */
        /*
Input:
Minion: Robert 14 Berlin
Villain: Gru
    Villain Gru was added to the database.
Successfully added Robert to be minion of Gru.
Minion: Cathleen 20 Liverpool
Villain: Gru
    Output:
	Town Liverpool was added to the database.
    Successfully added Cathleen to be minion of Gru.
Input:
Minion: Mars 23 Sofia
Villain: Poppy
	Output:
	Villain Poppy was added to the database.
    Successfully added Mars to be minion of Poppy
Input:
Minion: Carry 20 Eindhoven
Villain: Jimmy
	Output:
	Town Eindhoven was added to the database.
    Villain Jimmy was added to the database.
    Successfully added Carry to be minion of Jimmy
         */
        Connection conn = DatabaseConnector.datataseConnection("minions_db");
        String minionInput = sc.nextLine();
        String villainInput = sc.nextLine();
        String minionName = minionInput.split("\\s+")[1];
        int minionAge = Integer.parseInt(minionInput.split("\\s+")[2]);
        String minionTown = minionInput.split("\\s+")[3];
        String villainName = villainInput.split("\\s+")[1];
        int townId = 0;
        int villainId = 0;

        PreparedStatement selectTown = conn.prepareStatement("SELECT id FROM towns WHERE name = ?");
        selectTown.setString(1, minionTown);
        ResultSet townSet = selectTown.executeQuery();
        if (!townSet.next()) {
            PreparedStatement insertTown = conn.prepareStatement("INSERT INTO towns (name) VALUES (?);");
            insertTown.setString(1, minionTown);
            insertTown.executeUpdate();
            System.out.printf("Town %s was added to the database.", minionTown);
            ResultSet newTownSet = selectTown.executeQuery();
            townId = newTownSet.getInt("id");
        } else {
            townId = townSet.getInt("id");
        }
        PreparedStatement selectVillain = conn.prepareStatement("SELECT id FROM villains WHERE name = ?");
        selectVillain.setString(1, villainName);
        ResultSet villainSet = selectVillain.executeQuery();
        if (!villainSet.next()) {
            PreparedStatement insertVillain = conn.prepareStatement("INSERT INTO villains (name, evilness_factor) " +
                    "VALUES (?, ?);");
            insertVillain.setString(1, villainName);
            insertVillain.setString(2, "evil");
            insertVillain.executeUpdate();
            System.out.printf("Villain %s was added to the database.", villainName);
            ResultSet newVillainSet = selectVillain.executeQuery();
            villainId = newVillainSet.getInt("id");
        } else {
            villainId = villainSet.getInt("id");
        }
        PreparedStatement insertMinion = conn.prepareStatement("INSERT INTO minions (name, age, town_id) " +
                "VALUES (?, ?, ?);");
        insertMinion.setString(1, minionName);
        insertMinion.setInt(2, minionAge);
        insertMinion.setInt(3, townId);
        insertMinion.executeUpdate();
        PreparedStatement lastInsertedMinion = conn.prepareStatement("SELECT id FROM minions ORDER BY id DESC;");
        ResultSet minionSet = lastInsertedMinion.executeQuery();
        minionSet.next();
        int minionId = minionSet.getInt("id");
        PreparedStatement insertMinionsVillains = conn.prepareStatement("INSERT INTO minions_villains (minion_id, villain_id) " +
                "VALUES (?, ?);");
        insertMinionsVillains.setInt(1, minionId);
        insertMinionsVillains.setInt(2, villainId);
        insertMinionsVillains.executeUpdate();
        System.out.printf("Successfully added %s to be minion of %s.", minionName, villainName);
    }
}
