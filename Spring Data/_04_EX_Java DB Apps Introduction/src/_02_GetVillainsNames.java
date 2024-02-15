import java.sql.*;
import java.util.Properties;

public class _02_GetVillainsNames {
    public static void main(String[] args) throws SQLException {
        Properties props = new Properties();
        props.setProperty("user", "rootuser");
        props.setProperty("password", "12341234");
        Connection connection = DriverManager.getConnection("jdbc:mysql://109.160.80.16:3306/minions_db", props);

        /*
        2.	Get Villains’ Names
Write a program that prints on the console all villains’ names and their number of minions.
Get only the villains who have more than 15 minions.
Order them by a number of minions in descending order.
         */
        PreparedStatement stmt = connection.prepareStatement("SELECT v.name, COUNT(mv.minion_id) AS minion_count " +
                "FROM villains AS v " +
                "JOIN minions_villains AS mv ON v.id = mv.villain_id " +
                "JOIN minions AS m ON m.id = mv.minion_id " +
                "GROUP BY v.name " +
                "HAVING COUNT(mv.minion_id) > 15 " +
                "ORDER BY minion_count DESC;");

        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            String villainName = rs.getString("name");
            int minionsCount = rs.getInt("minion_count");
            System.out.println(villainName + " " + minionsCount);
        }
        rs.close();
        stmt.close();
        connection.close();
    }
}
