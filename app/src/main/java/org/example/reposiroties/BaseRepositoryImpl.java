package org.example.reposiroties;

import org.example.config.DatabaseConnection;
import org.example.mappers.rowmapper.EntityRowMapper;
import org.example.reposiroties.interfaces.BaseRepository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.example.utils.persistence.QueryExecutor.executeQuery;

public abstract class BaseRepositoryImpl<Entity, ID> implements BaseRepository<Entity, ID> {

    protected String _tableName;
    protected Connection connection;
    protected EntityRowMapper<Entity> entityRowMapper;

    public BaseRepositoryImpl ( String _tableName, EntityRowMapper<Entity> entityRowMapper ) {
        this.connection = DatabaseConnection.getConnection();
        this._tableName = _tableName;
        this.entityRowMapper = entityRowMapper;
    }

    @Override
    public List<Entity> findAll () {
        final String query = "SELECT * FROM " + this._tableName;

        return executeQuery(query, stmt -> {
            List<Entity> entities = new ArrayList<>();
            try (ResultSet resultSet = stmt.executeQuery()) {
                while (resultSet.next()) {
                    entities.add(entityRowMapper.map(resultSet));
                }
            }
            return entities;
        });

    }

    @Override
    public Optional<Entity> findById ( ID id ) {
        String query = "SELECT * FROM " + _tableName + " WHERE id = ?";
        return executeQuery(query, stmt -> {
            stmt.setObject(1, id);
            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(entityRowMapper.map(resultSet));
                }
                return Optional.empty();
            }
        });
    }

    public Optional<Entity> findByColumn ( String column, String value ) {
        String query = "SELECT * FROM " + _tableName + " WHERE " + column + " = ?";
        return executeQuery(query, stmt -> {
            stmt.setObject(1, value);
            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(entityRowMapper.map(resultSet));
                }
                return Optional.empty();
            }
        });
    }


    @Override
    public boolean existsById ( final ID id ) {
        final String query = "SELECT 1 FROM " + this._tableName + " WHERE id =  ?";

        return executeQuery(query, stmt -> {
            stmt.setObject(1, id);
            try (ResultSet resultSet = stmt.executeQuery()) {
                return resultSet.next();
            }
        });

    }

    @Override

    public boolean existsByColumn ( final String columnName, final String value ) {
            final String query = "SELECT 1 FROM " + this._tableName + " WHERE " + columnName + " = ?";
        return executeQuery(query , stmt -> {
            stmt.setObject(1, value);
            try (ResultSet resultSet = stmt.executeQuery()) {
                return resultSet.next();
            }
        });
    }
    @Override
    public boolean delete(ID id) {
        String query = "DELETE FROM " + _tableName + " WHERE id = ?";
        return executeQuery(query, stmt -> {
            stmt.setObject(1, id);
            return stmt.executeUpdate() > 0;
        });
    }


}
