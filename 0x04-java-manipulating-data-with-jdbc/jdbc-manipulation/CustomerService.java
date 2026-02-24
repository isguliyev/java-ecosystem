import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;

import java.util.List;
import java.util.Optional;

public class CustomerService {
    private final String connectionUrl;
    private final CustomerDAO  customerDAO;

    public CustomerService(CustomerDAO customerDAO, String connectionUrl) {
        this.customerDAO = customerDAO;
        this.connectionUrl = connectionUrl;
    }

    public Optional<Customer> findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Customer id is null.");
        }

        try (Connection connection = DriverManager.getConnection(this.connectionUrl)) {
            return this.customerDAO.findById(connection, id);
        } catch (SQLException sqlException) {
            throw new RuntimeException(sqlException);
        }
    }

    public List<Customer> findAll() {
        try (Connection connection = DriverManager.getConnection(this.connectionUrl)) {
            return this.customerDAO.findAll(connection);
        } catch (SQLException sqlException) {
            throw new RuntimeException(sqlException);
        }
    }

    public void insert(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer is null.");
        }

        Connection connection = null;

        try {
            connection = DriverManager.getConnection(this.connectionUrl);
            connection.setAutoCommit(false);

            this.customerDAO.insert(connection, customer);

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

    public void update(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer is null.");
        }

        Connection connection = null;

        try {
            connection = DriverManager.getConnection(this.connectionUrl);
            connection.setAutoCommit(false);

            this.customerDAO.update(connection, customer);

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

    public void deleteById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Customer id is null.");
        }

        Connection connection = null;

        try {
            connection = DriverManager.getConnection(this.connectionUrl);
            connection.setAutoCommit(false);

            this.customerDAO.deleteById(connection, id);

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