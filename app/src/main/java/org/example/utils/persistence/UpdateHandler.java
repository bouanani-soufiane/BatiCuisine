package org.example.utils.persistence;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface UpdateHandler {
    boolean handle ( PreparedStatement preparedStatement ) throws SQLException;
}
