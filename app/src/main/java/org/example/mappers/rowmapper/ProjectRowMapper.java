package org.example.mappers.rowmapper;

import org.example.entities.Client;
import org.example.entities.Project;
import org.example.enums.ProjectStatus;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class ProjectRowMapper implements  EntityRowMapper<Project>{

    private final EntityRowMapper<Client>  clientRowMapper;
    public ProjectRowMapper(EntityRowMapper<Client>  clientRowMapper){
        this.clientRowMapper = clientRowMapper;
    }

    @Override
    public Project map ( ResultSet resultSet ) throws SQLException {
        Project project = new Project();
        project.setId(UUID.fromString(resultSet.getString("id")));
        project.setName(resultSet.getString("name"));
        project.setSurface(resultSet.getDouble("surface"));
        project.setTva(resultSet.getDouble("tva"));
        project.setProfitMargin(resultSet.getDouble("profit_margin"));
        project.setProjectStatus(ProjectStatus.valueOf(resultSet.getString("project_status")));
        project.setTotalCost(resultSet.getDouble("total_cost"));
        project.setClient(clientRowMapper.map(resultSet));
        return project;
    }

    @Override
    public void map ( Project project, PreparedStatement stmt ) throws SQLException {
        int counter = 1;
        stmt.setString(counter++ , project.name());
        stmt.setDouble(counter++ , project.surface());
        stmt.setObject(counter++ , project.projectStatus().toString());
        stmt.setDouble(counter++ , project.totalCost());
        stmt.setDouble(counter++ , project.profitMargin());
        stmt.setDouble(counter++ , project.tva());
        stmt.setObject(counter++ , project.client().id());

    }
}
