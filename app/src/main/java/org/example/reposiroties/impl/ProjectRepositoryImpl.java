package org.example.reposiroties.impl;

import org.example.entities.Project;
import org.example.mappers.rowmapper.EntityRowMapper;
import org.example.reposiroties.BaseRepositoryImpl;
import org.example.reposiroties.interfaces.ProjectRepository;

import java.sql.ResultSet;
import java.util.UUID;

import static org.example.utils.persistence.QueryExecutor.executeQuery;

public class ProjectRepositoryImpl extends BaseRepositoryImpl<Project, UUID> implements ProjectRepository {

    public ProjectRepositoryImpl ( EntityRowMapper<Project> entityRowMapper ) {
        super("projects", entityRowMapper);
    }


    @Override
    public Project create ( Project project ) {
        final String query = " " +
                "WITH inserted_project AS (\n" +
                "                   INSERT INTO projects (name, surface, project_status, total_cost, profit_margin, tva, client_id)\n" +
                "                   VALUES (?,?,?::project_status ,?,?,?,?)\n" +
                "                   RETURNING *\n" +
                "               )\n" +
                "               SELECT ip.*, c.*\n" +
                "               FROM inserted_project ip\n" +
                "               JOIN clients c ON ip.client_id = c.id; ";


        return executeQuery(query, stmt -> {

            entityRowMapper.map(project, stmt);

            try (ResultSet resultSet = stmt.executeQuery()) {
                return resultSet.next() ? entityRowMapper.map(resultSet) : null;
            }
        });
    }

    @Override
    public Project update ( UUID uuid, Project project ) {
        return null;
    }
}
