package org.example.reposiroties.impl;

import org.example.entities.Estimate;
import org.example.mappers.rowmapper.EntityRowMapper;
import org.example.reposiroties.BaseRepositoryImpl;
import org.example.reposiroties.interfaces.EstimateRepository;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.example.utils.persistence.QueryExecutor.executeQuery;

public class EstimateRepositoryImpl extends BaseRepositoryImpl<Estimate, UUID> implements EstimateRepository {
    public EstimateRepositoryImpl ( EntityRowMapper<Estimate> entityRowMapper ) {
        super("estimates", entityRowMapper);
    }

    @Override
    public Estimate create ( Estimate estimate ) {
        final String query = "insert into estimates (issued_date,valid_until_date, is_accepted , estimate_amount, project_id) values (?,?,?,?,?) RETURNING *";

        return executeQuery(query, stmt -> {
            entityRowMapper.map(estimate, stmt);
            try (ResultSet resultSet = stmt.executeQuery()) {
                return estimate;

            }
        });
    }

    @Override
    public Estimate update ( UUID uuid, Estimate estimate ) {
        return null;
    }


    @Override
    public List<Estimate> findAll () {
        final String query = "SELECT * FROM estimates as e , projects as p where e.project_id = p.id ;" ;

        return executeQuery(query, stmt -> {
            List<Estimate> entities = new ArrayList<>();
            try (ResultSet resultSet = stmt.executeQuery()) {
                while (resultSet.next()) {
                    entities.add(entityRowMapper.map(resultSet));
                }
            }
            return entities;
        });

    }

    @Override
    public Optional<Estimate> findById ( UUID id ) {
        String query = "SELECT * FROM estimates as e , projects as p where e.project_id = p.id  and e.id = ?";
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



}
