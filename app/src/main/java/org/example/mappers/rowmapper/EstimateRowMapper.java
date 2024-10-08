package org.example.mappers.rowmapper;

import org.example.entities.Estimate;
import org.example.entities.Project;
import org.example.enums.ProjectStatus;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

public class EstimateRowMapper implements EntityRowMapper<Estimate> {

    private final EntityRowMapper<Project> projectRowMapper;

    public EstimateRowMapper ( EntityRowMapper<Project> projectRowMapper ) {
        this.projectRowMapper = projectRowMapper;
    }

    @Override
    public Estimate map ( ResultSet resultSet ) throws SQLException {
        Estimate estimate = new Estimate();
        estimate.setId((UUID) resultSet.getObject("id"));
        estimate.setIssuedDate(resultSet.getTimestamp("issued_date").toLocalDateTime());
        estimate.setValidUntilDate(resultSet.getTimestamp("valid_until_date").toLocalDateTime());
        estimate.setAccepted(resultSet.getBoolean("is_accepted"));
        estimate.setEstimateAmount(resultSet.getDouble("estimate_amount"));

        Project project = new Project();
        project.setId((UUID) resultSet.getObject("project_id"));
        project.setName(resultSet.getString("name"));
        project.setSurface(resultSet.getDouble("surface"));
        project.setProjectStatus(ProjectStatus.valueOf(resultSet.getString("project_status")));
        project.setProfitMargin(resultSet.getDouble("profit_margin"));

        estimate.setProject(project);

        return estimate;
    }

    @Override
    public void map ( Estimate estimate, PreparedStatement stmt ) throws SQLException {
        int counter = 1;
        stmt.setTimestamp(counter++, Timestamp.valueOf((LocalDateTime.from(estimate.issuedDate()))));
        stmt.setTimestamp(counter++, Timestamp.valueOf(LocalDateTime.from(estimate.validUntilDate())));
        stmt.setBoolean(counter++, estimate.isAccepted());
        stmt.setDouble(counter++, estimate.estimateAmount());
        stmt.setObject(counter++, estimate.project().id());
    }
}
