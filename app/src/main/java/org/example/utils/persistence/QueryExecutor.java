package org.example.utils.persistence;

import org.example.config.DatabaseConnection;
import org.example.exceptions.RepositoryException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class QueryExecutor {

    private final static Connection connection = DatabaseConnection.getConnection();

    public static  <T> T executeQuery(final String query, QueryHandler<T> handler) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            return handler.handle(preparedStatement);
        } catch (SQLException e) {
            throw new RepositoryException("Error executing query: " + query, e);
        }
    }


}
