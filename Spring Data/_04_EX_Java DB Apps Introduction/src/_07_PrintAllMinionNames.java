import DBConn.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class _07_PrintAllMinionNames {
    public static void main(String[] args) throws SQLException {
        /*
        Write a program that prints all minion names from the minion’s table in order:
        first record, last record, first + 1, last – 1, first + 2, last – 2… first + n, last – n.
         */
        Connection conn = DatabaseConnector.datataseConnection("minions_db");
        List<String> minionsList = new ArrayList<>();
        PreparedStatement selectMinions = conn.prepareStatement("SELECT name FROM minions");
        ResultSet minionSet = selectMinions.executeQuery();
        while (minionSet.next()) {
            minionsList.add(minionSet.getString("name"));
        }
        int firstIndex = 0;
        int lastIndex = minionsList.size() - 1;
        while (firstIndex <= lastIndex) {
            System.out.println(minionsList.get(firstIndex));
            if (firstIndex != lastIndex) {
                System.out.println(minionsList.get(lastIndex));
            }
                firstIndex++;
                lastIndex--;
        }
    }
}
