import java.sql.*;

public class DataBaseInitializer {
    public static void initializeCustomerDataBase(String connectionUrl) {
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(connectionUrl);
            connection.setAutoCommit(false);

            try (Statement statement = connection.createStatement()) {
                final int rowsAffected = statement.executeUpdate(
                    """
                        CREATE TABLE IF NOT EXISTS Customer (
                            id INTEGER PRIMARY KEY AUTOINCREMENT,
                            name TEXT NOT NULL,
                            email TEXT NOT NULL UNIQUE,
                            password TEXT NOT NULL
                        )
                    """
                );
            }

            connection.commit();
        } catch (SQLException sqlException) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException rollbackException) {
                    System.err.println(rollbackException.getMessage());
                }
            }

            throw new RuntimeException(sqlException);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException sqlException) {
                    System.err.println(sqlException.getMessage());
                }
            }
        }
    }
}