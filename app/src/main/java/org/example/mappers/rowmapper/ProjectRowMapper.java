package org.example.mappers.rowmapper;

import org.example.entities.Client;
import org.example.entities.Material;
import org.example.entities.Project;
import org.example.entities.Workforce;
import org.example.enums.ProjectStatus;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProjectRowMapper implements EntityRowMapper<Project>, ProjectAllRowsMapper {

    private final EntityRowMapper<Client> clientRowMapper;

    public ProjectRowMapper ( EntityRowMapper<Client> clientRowMapper ) {
        this.clientRowMapper = clientRowMapper;
    }

    @Override
    public Project map ( ResultSet resultSet ) throws SQLException {

        Project project = new Project();

        project.setId(UUID.fromString(resultSet.getString("id")));
        project.setName(resultSet.getString("name"));
        project.setSurface(resultSet.getDouble("surface"));
        project.setTotalCost(resultSet.getDouble("total_cost"));
        project.setProfitMargin(resultSet.getDouble("profit_margin"));
        project.setProjectStatus(ProjectStatus.valueOf(resultSet.getString("project_status")));
        project.setClient(clientRowMapper.map(resultSet));

        return project;
    }

    @Override
    public Project mapWithRelations ( ResultSet resultSet ) throws SQLException {
        Project project = new Project();
        List<Material> materials = new ArrayList<>();
        List<Workforce> workforces = new ArrayList<>();

        do {
            // project
            if (resultSet.isFirst()) {
                project.setId(UUID.fromString(resultSet.getString(1)));
                project.setName(resultSet.getString(2));
                project.setSurface(resultSet.getDouble(3));
                project.setProjectStatus(ProjectStatus.valueOf(resultSet.getString(4)));
                project.setTotalCost(resultSet.getDouble(5));
                project.setProfitMargin(resultSet.getDouble(6));

                // Client
                Client client = new Client();
                client.setId(UUID.fromString(resultSet.getString(11)));
                client.setName(resultSet.getString(12));
                client.setAddress(resultSet.getString(13));
                client.setPhone(resultSet.getString(14));
                client.setProfessional(resultSet.getBoolean(15));
                project.setClient(client);
            }

            // materials
            if (isMaterialRowValid(resultSet)) {
                Material material = new Material();
                material.setName(resultSet.getString(20));
                material.setTva(resultSet.getDouble(21));
                material.setQuantity(resultSet.getDouble(26));
                material.setUnitPrice(resultSet.getDouble(27));
                material.setTransportCost(resultSet.getDouble(28));
                material.setCoefficient(resultSet.getDouble(29));
                materials.add(material);
            }

            // workfoces
            if (isWorkforceRowValid(resultSet)) {
                Workforce workforce = new Workforce();
                workforce.setName(resultSet.getString(20));
                workforce.setTva(resultSet.getDouble(21));
                workforce.setPricePerHour(resultSet.getDouble(30));
                workforce.setWorkingHours(resultSet.getDouble(31));
                workforce.setProductivityFactor(resultSet.getDouble(32));
                workforces.add(workforce);
            }

        } while (resultSet.next());

        project.setMaterials(materials);
        project.setWorkforces(workforces);

        return project;
    }

    private boolean isMaterialRowValid ( ResultSet resultSet ) throws SQLException {
        return resultSet.getString(20) != null && resultSet.getDouble(26) > 0 && resultSet.getDouble(27) > 0 && resultSet.getDouble(28) > 0 && resultSet.getDouble(29) > 0;
    }


    private boolean isWorkforceRowValid ( ResultSet resultSet ) throws SQLException {
        return resultSet.getString(20) != null && resultSet.getDouble(30) > 0 && resultSet.getDouble(31) > 0 && resultSet.getDouble(32) > 0;
    }


    @Override
    public void map ( Project project, PreparedStatement stmt ) throws SQLException {
        int counter = 1;
        stmt.setString(counter++, project.name());
        stmt.setDouble(counter++, project.surface());
        stmt.setObject(counter++, project.projectStatus().toString());
        stmt.setObject(counter++, project.client().id());

    }
}
