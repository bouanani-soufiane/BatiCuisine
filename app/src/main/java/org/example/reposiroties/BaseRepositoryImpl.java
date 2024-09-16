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
import java.util.concurrent.atomic.AtomicReference;

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
            final String query = "SELECT * FROM " + this._tableName;
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

    @Override
    public Optional<Entity> findById( final ID id){
        AtomicReference<Optional<Entity>> entity = new AtomicReference<>(Optional.empty());

        try{
            final String query = "SELECT * FROM " + this._tableName + " WHERE id =  ?";
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setObject(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                entity.set(Optional.of(entityRowMapper.map(resultSet)));
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return entity.get();
    }

    public Optional<Entity> findByColumn(final String column , final String value){
        final AtomicReference<Optional<Entity>> entity = new AtomicReference<>(Optional.empty());
        try {
            final String query = "SELECT * FROM " + this._tableName + " WHERE "+column+" = ?";
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setObject(1, value);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                entity.set(Optional.of(entityRowMapper.map(resultSet)));
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return entity.get();
    }

    @Override
    public boolean existsById(final ID id) {
        try{
            final String query = "SELECT 1 FROM " + this._tableName + " WHERE id =  ?";
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setObject(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override

    public boolean existsByColumn(final String columnName, final String value){
        try{
            final String query = "SELECT 1 FROM " + this._tableName + " WHERE "+columnName+" = ?";
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setObject(1, value);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(ID id){
        try {
            final String query = "DELETE FROM " + this._tableName + " WHERE id = ?  ";
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setObject(1, id);
            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }


}
