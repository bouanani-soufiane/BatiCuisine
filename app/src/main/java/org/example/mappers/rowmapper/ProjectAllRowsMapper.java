package org.example.mappers.rowmapper;

import org.example.entities.Project;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ProjectAllRowsMapper{
    Project mapWithRelations ( ResultSet resultSet ) throws SQLException;
}
