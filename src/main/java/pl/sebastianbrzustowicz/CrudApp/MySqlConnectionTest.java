package pl.sebastianbrzustowicz.CrudApp;

import java.sql.Connection;
import java.sql.DriverManager;

public class MySqlConnectionTest {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/robotTaskerApi?useSSL=false&allowPublicKeyRetrieval=true";
        String user = "root";
        String password = "sebastian";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            System.out.println("Connected to the database!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}