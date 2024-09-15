package org.example.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * The Database class provides a centralized connection to the database using JDBC.
 * It ensures that only one connection is created and reused throughout the application.
 */
public class DatabaseConnection {
    private static volatile Connection connection = null;

    private DatabaseConnection() {
    }

    /**
     * Initializes the database connection.
     */
    public static void init() {
        if (connection == null) {
            synchronized (DatabaseConnection.class) {
                if (connection == null) {
                    try {
                        Class.forName(DotenvReader.get("DB_DRIVER"));
                        connection = DriverManager.getConnection(
                                DotenvReader.get("DB_URL"),
                                DotenvReader.get("DB_USERNAME"),
                                DotenvReader.get("DB_PASSWORD")
                        );
                        System.out.println("connection established");
                    } catch (ClassNotFoundException | SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }


    static {
        init();
    }

    /**
     * Closes the database connection.
     *
     * @return The closed connection, or null if an error occurs during the closing process.
     */
    public static Connection closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
                return connection;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    /**
     * Retrieves the database connection. If the connection is null, it initializes it first.
     *
     * @return The database connection.
     */
    public static Connection getConnection() {
        if (connection == null) {
            init();
        }
        return connection;
    }
}