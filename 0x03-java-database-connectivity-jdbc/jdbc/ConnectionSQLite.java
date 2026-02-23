// javac -cp "sqlite-jdbc-3.51.1.0.jar" -d target ConnectionSQLite.java
// java -cp "target:sqlite-jdbc-3.51.1.0.jar" ConnectionSQLite

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionSQLite {
    public static void main(String[] args) {
        try (Connection connection = initConnection();) {
        } catch (SQLException exception) {
            System.err.println(exception.getMessage());
        }
    }

    public static Connection initConnection() {
        Connection connection = null;

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:sqlite_database_2022.db");
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }

        return connection;
    }
}