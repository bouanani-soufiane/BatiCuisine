package org.example.reposiroties.impl;

import org.example.entities.Project;
import org.example.exceptions.ProjectNotFound;
import org.example.mappers.rowmapper.EntityRowMapper;
import org.example.mappers.rowmapper.ProjectAllRowsMapper;
import org.example.reposiroties.BaseRepositoryImpl;
import org.example.reposiroties.interfaces.ProjectRepository;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.example.utils.persistence.QueryExecutor.executeQuery;

public class ProjectRepositoryImpl extends BaseRepositoryImpl<Project, UUID> implements ProjectRepository {

    public ProjectRepositoryImpl ( EntityRowMapper<Project> entityRowMapper ) {
        super("projects", entityRowMapper);
    }


    @Override
    public Project create ( Project project ) {
        final String query = "WITH inserted_project AS (INSERT INTO projects (name, surface, project_status, client_id) VALUES (?,?,?::project_status ,?) RETURNING *) SELECT ip.*, c.* FROM inserted_project ip JOIN clients c ON ip.client_id = c.id;";

        return executeQuery(query, stmt -> {

            entityRowMapper.map(project, stmt);

            try (ResultSet resultSet = stmt.executeQuery()) {
                return resultSet.next() ? entityRowMapper.map(resultSet) : null;
            }
        });
    }


    @Override
    public Optional<Project> findById ( UUID id ) {
        final String query = "SELECT p.*, c.*, com.*, mat.quantity AS material_quantity, mat.unit_price AS material_unit_price, mat.transport_cost AS material_transport_cost, mat.coefficient AS material_coefficient, wf.price_per_hour AS workforce_price_per_hour, wf.working_hours AS workforce_working_hours, wf.productivity_factor AS workforce_productivity_factor FROM projects p LEFT JOIN clients c ON p.client_id = c.id LEFT JOIN components com ON com.project_id = p.id LEFT JOIN materials mat ON mat.id = com.id LEFT JOIN workforces wf ON wf.id = com.id WHERE p.id = ? GROUP BY p.id, c.id, com.id, mat.quantity, mat.unit_price, mat.transport_cost, mat.coefficient, wf.price_per_hour, wf.working_hours, wf.productivity_factor";

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
    public Project update ( UUID id, Project project ) {
        final String query = "UPDATE public.projects " + "SET profit_margin = ?, total_cost = ?, updated_at = CURRENT_TIMESTAMP " + "WHERE id = ?;";

        executeQuery(query, stmt -> {
            stmt.setObject(1, project.profitMargin());
            stmt.setObject(2, project.totalCost());
            stmt.setObject(3, id);
            return stmt.executeUpdate();
        });

        return findById(id).orElseThrow(() -> new ProjectNotFound(id));
    }

    @Override
    public List<Project> findAll () {
        String query = "SELECT * FROM projects INNER JOIN clients ON projects.client_id = clients.id;";

        return executeQuery(query, stmt -> {
            List<Project> entities = new ArrayList<>();
            try (ResultSet resultSet = stmt.executeQuery()) {
                while (resultSet.next()) {
                    entities.add(entityRowMapper.map(resultSet));
                }
            }
            return entities;
        });

    }

}
