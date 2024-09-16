package org.example.mappers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface EntityRowMapper<Entity> {
    Entity map(ResultSet resultSet) throws SQLException;

    void map(Entity entity, PreparedStatement stmt) throws SQLException;
}
