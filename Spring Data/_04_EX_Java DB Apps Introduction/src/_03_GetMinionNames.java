import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class _03_GetMinionNames {
    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);

        Properties props = new Properties();
        props.setProperty("user", "rootuser");
        props.setProperty("password", "12341234");

        Connection connection = DriverManager.getConnection("jdbc:mysql://109.160.80.16:3306/minions_db", props);

        int inputVillainId = Integer.parseInt(sc.nextLine());

//        3.	Get Minion Names
//        Write a program that prints on the console all minion names and their age for a given villain id.
//        For the output, use the formats given in the examples.
        PreparedStatement getVillainName = connection.prepareStatement("SELECT name FROM villains WHERE id = ?;");
        getVillainName.setInt(1, inputVillainId);
        ResultSet villainSet = getVillainName.executeQuery();
        if (!villainSet.next()) {
            System.out.printf("No villain with ID %d exists in the database.", inputVillainId);
            getVillainName.close();
        } else {
            System.out.println("Villain: " + villainSet.getString("name"));
            PreparedStatement minionsNamesAndAges = connection.prepareStatement("SELECT m.name, m.age FROM minions AS m " +
                    "JOIN minions_villains AS mv ON m.id = mv.minion_id " +
                    "WHERE mv.villain_id = ?;");
            minionsNamesAndAges.setInt(1, inputVillainId);
            ResultSet minionsSet = minionsNamesAndAges.executeQuery();

            int count = 0;
            while (minionsSet.next()) {
                String minionName = minionsSet.getString("name");
                int minionAge = minionsSet.getInt("age");
                count++;
                System.out.printf("%d. %s %d%n", count, minionName, minionAge);
            }
            getVillainName.close();
        }
        connection.close();
    }
}
