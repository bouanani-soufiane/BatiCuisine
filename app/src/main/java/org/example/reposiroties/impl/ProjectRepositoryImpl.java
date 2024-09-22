package org.example.reposiroties.impl;

import org.example.entities.Project;
import org.example.mappers.rowmapper.EntityRowMapper;
import org.example.mappers.rowmapper.ProjectAllRowsMapper;
import org.example.reposiroties.BaseRepositoryImpl;
import org.example.reposiroties.interfaces.ProjectRepository;
import java.sql.ResultSet;
import java.util.Optional;
import java.util.UUID;
import static org.example.utils.persistence.QueryExecutor.executeQuery;

public class ProjectRepositoryImpl extends BaseRepositoryImpl<Project, UUID> implements ProjectRepository {

    public ProjectRepositoryImpl ( EntityRowMapper<Project> entityRowMapper ) {
        super("projects", entityRowMapper);
    }


    @Override
    public Project create ( Project project ) {
        final String query = "WITH inserted_project AS (INSERT INTO projects (name, surface, project_status, total_cost, profit_margin, tva, client_id) VALUES (?,?,?::project_status ,?,?,?,?) RETURNING *) SELECT ip.*, c.* FROM inserted_project ip JOIN clients c ON ip.client_id = c.id;";

        return executeQuery(query, stmt -> {

            entityRowMapper.map(project, stmt);

            try (ResultSet resultSet = stmt.executeQuery()) {
                return resultSet.next() ? entityRowMapper.map(resultSet) : null;
            }
        });
    }


    @Override
    public Optional<Project> findById(UUID id) {
        String query = "SELECT p.*, c.*, m.name AS material_name, m.quantity, m.coefficient, m.tva, m.transport_cost, m.unit_price, w.name AS workforce_name, w.tva, w.price_per_hour, w.working_hours, w.productivity_factor " +
                "FROM projects p " +
                "LEFT JOIN clients c ON p.client_id = c.id " +
                "LEFT JOIN materials m ON m.project_id = p.id " +
                "LEFT JOIN workforces w ON w.project_id = p.id " +
                "WHERE p.id = ?;";

        return executeQuery(query, stmt -> {
            stmt.setObject(1, id);
            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    ProjectAllRowsMapper projectAllRowsMapper = (ProjectAllRowsMapper) entityRowMapper;
                    Project project = projectAllRowsMapper.mapWithRelations(resultSet);
                    return Optional.of(project);
                } else {
                    return Optional.empty();
                }
            }
        });
    }


    @Override
    public Project update ( UUID uuid, Project project ) {
        return null;
    }

}
