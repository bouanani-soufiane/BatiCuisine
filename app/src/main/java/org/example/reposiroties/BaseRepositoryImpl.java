package org.example.reposiroties;

import org.example.config.DatabaseConnection;
import org.example.mappers.EntityRowMapper;
import org.example.reposiroties.interfaces.BaseRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class BaseRepositoryImpl<Entity , ID > implements BaseRepository <Entity, ID> {

    protected String _tableName;
    protected Connection connection;
    protected EntityRowMapper<Entity> entityRowMapper;

    public BaseRepositoryImpl(String _tableName, EntityRowMapper<Entity> entityRowMapper ) {
        this.connection = DatabaseConnection.getConnection();
        this._tableName = _tableName;
        this.entityRowMapper = entityRowMapper;
    }

    @Override
    public List<Entity> findAll () {
        List<Entity> entities = new ArrayList<>();
        try {
            String query = "SELECT * FROM " + this._tableName;
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                entities.add(entityRowMapper.map(resultSet));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return entities;
    }

    public Optional<Entity> findById( final ID id){
        Optional<Entity> entity = Optional.empty();
        try{
            String query = "SELECT * FROM " + this._tableName + " WHERE id =  CAST  (? AS UUID) ";
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setObject(1, id.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                entity = Optional.of(entityRowMapper.map(resultSet));
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return entity;
    }




}
