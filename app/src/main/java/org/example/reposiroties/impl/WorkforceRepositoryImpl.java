package org.example.reposiroties.impl;

import org.example.entities.Workforce;
import org.example.mappers.rowmapper.EntityRowMapper;
import org.example.reposiroties.BaseRepositoryImpl;
import org.example.reposiroties.interfaces.WorkforceRepository;

import java.sql.ResultSet;
import java.util.UUID;

import static org.example.utils.persistence.QueryExecutor.executeQuery;

public class WorkforceRepositoryImpl extends BaseRepositoryImpl<Workforce, UUID> implements WorkforceRepository {
    public WorkforceRepositoryImpl ( EntityRowMapper<Workforce> entityRowMapper ) {
        super("workforces", entityRowMapper);
    }

    @Override
    public Workforce create ( Workforce workforce ) {
        String query = "WITH inserted_workforce AS (INSERT INTO workforces (name, price_per_hour, working_hours, tva, productivity_factor, project_id) VALUES (?, ?, ?, ?, ?, ?) RETURNING *) SELECT iw.*, p.*, c.* FROM inserted_workforce iw JOIN projects p ON iw.project_id = p.id JOIN clients c ON p.client_id = c.id";

        return executeQuery(query, stmt -> {
            entityRowMapper.map(workforce, stmt);
            try (ResultSet resultSet = stmt.executeQuery()) {
                return resultSet.next() ? entityRowMapper.map(resultSet) : null;
            }
        });

    }

    @Override
    public Workforce update ( UUID uuid, Workforce workforce ) {
        return null;
    }
}
