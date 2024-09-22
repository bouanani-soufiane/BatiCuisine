package org.example.mappers.rowmapper;

import org.example.entities.Project;
import org.example.entities.Workforce;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class WorkforceRowMapper implements EntityRowMapper<Workforce> {

    private final EntityRowMapper<Project> projectRowMapper;

    public WorkforceRowMapper ( EntityRowMapper<Project> projectRowMapper ) {
        this.projectRowMapper = projectRowMapper;
    }

    @Override
    public Workforce map ( ResultSet resultSet ) throws SQLException {

        Workforce workforce = new Workforce();

        workforce.setId((UUID) resultSet.getObject("id"));
        workforce.setName(resultSet.getString("name"));
        workforce.setTva(resultSet.getDouble("tva"));
        workforce.setPricePerHour(resultSet.getDouble("price_per_hour"));
        workforce.setProductivityFactor(resultSet.getDouble("productivity_factor"));
        workforce.setWorkingHours(resultSet.getDouble("working_hours"));
        workforce.setProject(projectRowMapper.map(resultSet));

        return workforce;
    }

    @Override
    public void map ( Workforce workforce, PreparedStatement stmt ) throws SQLException {
        int counter = 1;
        stmt.setString(counter++ , workforce.name());
        stmt.setDouble(counter++ , workforce.tva());
        stmt.setDouble(counter++ , workforce.pricePerHour());
        stmt.setDouble(counter++ , workforce.workingHours());
        stmt.setDouble(counter++ , workforce.productivityFactor());
        stmt.setObject(counter++ , workforce.project().id());
    }
}
