package sample;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnectionClass {
    public Connection databaseLink;
    public Connection getConnection() {
        String dbName = "travel_reservation_service";
        String dbEmail = "root";
        String dbPassword = "Bitswiz1297!";
        String url = "jdbc:mysql://localhost:3306/" + dbName;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaseLink = DriverManager.getConnection(url, dbEmail, dbPassword);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return databaseLink;
    }
}
