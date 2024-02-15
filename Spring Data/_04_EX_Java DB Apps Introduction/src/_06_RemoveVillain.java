import DBConn.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class _06_RemoveVillain {
    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);
        Connection conn = DatabaseConnector.datataseConnection("minions_db");
        int inputVillainId = Integer.parseInt(sc.nextLine());
        PreparedStatement selectVillain = conn.prepareStatement("SELECT name FROM villains WHERE id = ?;");
        selectVillain.setInt(1, inputVillainId);
        ResultSet villainSet = selectVillain.executeQuery();
        if (!villainSet.next()) {
            System.out.println("No such villain was found");
        } else {
            String villainName = villainSet.getString("name");
            PreparedStatement selectCountOfMinions = conn.prepareStatement("SELECT COUNT(minion_id) " +
                    "FROM minions_villains WHERE villain_id = ?");
            selectCountOfMinions.setInt(1, inputVillainId);
            ResultSet minionsSet = selectCountOfMinions.executeQuery();
            minionsSet.next();
            int countOfDeletedMinions = minionsSet.getInt(1);
            PreparedStatement deleteMinionsVillains = conn.prepareStatement("DELETE FROM minions_villains WHERE villain_id = ?");
            deleteMinionsVillains.setInt(1, inputVillainId);
            deleteMinionsVillains.executeUpdate();
            System.out.println(villainName + " was deleted");
            System.out.println(countOfDeletedMinions + " minions released");
        }
    }
}
