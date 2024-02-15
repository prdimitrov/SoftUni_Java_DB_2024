import DBConn.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class _08_IncreaseMinionsAge {
    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);
        Connection conn = DatabaseConnector.datataseConnection("minions_db");
        List<String> inputMinionsIds = Arrays.asList(sc.nextLine().split("\\s+"));

        String sql = "UPDATE minions SET name = LOWER(name), age = age + 1 WHERE id IN (" +
                String.join(",", inputMinionsIds.stream().map(id -> "?").collect(Collectors.toList())) + ")";

        PreparedStatement updateMinions = conn.prepareStatement(sql);
        for (int i = 0; i < inputMinionsIds.size(); i++) {
            updateMinions.setInt(i + 1, Integer.parseInt(inputMinionsIds.get(i)));
        }
        updateMinions.executeUpdate();

        PreparedStatement selectMinions = conn.prepareStatement("SELECT name, age FROM minions");
        ResultSet minionSet = selectMinions.executeQuery();
        while (minionSet.next()) {
            System.out.println(minionSet.getString("name") + " " + minionSet.getInt("age"));
        }
    }
}
