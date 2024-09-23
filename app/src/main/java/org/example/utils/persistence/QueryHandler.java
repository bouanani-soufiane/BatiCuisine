package org.example.utils.persistence;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface QueryHandler<T> {
    T handle ( PreparedStatement preparedStatement ) throws SQLException;
}

