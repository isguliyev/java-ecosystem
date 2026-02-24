import java.sql.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerDAO {
    public Optional<Customer> findById(Connection connection, Long id) throws SQLException {
        try (
            PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM Customer WHERE id = ?"
            )
        ) {
            preparedStatement.setLong(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(
                        new Customer(
                            resultSet.getLong("id"),
                            resultSet.getString("name"),
                            resultSet.getString("email"),
                            resultSet.getString("password")
                        )
                    );
                }
            }
        }

        return Optional.empty();
    }

    public List<Customer> findAll(Connection connection) throws SQLException {
        List<Customer> customers = new ArrayList<Customer>();

        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery("SELECT * FROM Customer")) {
                while (resultSet.next()) {
                    customers.add(
                        new Customer(
                            resultSet.getLong("id"),
                            resultSet.getString("name"),
                            resultSet.getString("email"),
                            resultSet.getString("password")
                        )
                    );
                }
            }
        }

        return customers;
    }

    public void insert(Connection connection, Customer customer) throws SQLException {
        try (
            PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO Customer (name, email, password) VALUES (?, ?, ?)",
                PreparedStatement.RETURN_GENERATED_KEYS
            )
        ) {
            preparedStatement.setString(1, customer.getName());
            preparedStatement.setString(2, customer.getEmail());
            preparedStatement.setString(3, customer.getPassword());

            final int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected == 0) {
                throw new SQLException("Customer not inserted, no rows affected.");
            }

            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    customer.setId(resultSet.getLong(1));
                } else {
                    throw new SQLException("Customer id is not generated.");
                }
            }
        }
    }

    public void update(Connection connection, Customer customer) throws SQLException {
        try (
            PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE Customer SET name=?, email=?, password=? WHERE id=?"
            )
        ) {
            preparedStatement.setString(1, customer.getName());
            preparedStatement.setString(2, customer.getEmail());
            preparedStatement.setString(3, customer.getPassword());
            preparedStatement.setLong(4, customer.getId());

            final int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected == 0) {
                throw new SQLException("Customer not updated, no rows affected.");
            }
        }
    }

    public void deleteById(Connection connection, Long id) throws SQLException {
        try (
            PreparedStatement preparedStatement = connection.prepareStatement(
                "DELETE FROM Customer WHERE id=?"
            )
        ) {
            preparedStatement.setLong(1, id);

            final int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected == 0) {
                throw new SQLException("Customer not deleted, no rows affected.");
            }
        }
    }
}