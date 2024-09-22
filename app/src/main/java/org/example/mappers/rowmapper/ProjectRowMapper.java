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

public class ProjectRowMapper implements  EntityRowMapper<Project> , ProjectAllRowsMapper{

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
    public Project mapWithRelations ( ResultSet resultSet ) throws SQLException {
        Client client = new Client();
        client.setId(UUID.fromString(resultSet.getString(12)));
        client.setName(resultSet.getString(13));
        client.setAddress(resultSet.getString(14));
        client.setPhone(resultSet.getString(15));
        client.setProfessional(resultSet.getBoolean(16));

        Material material = new Material();
        material.setName(resultSet.getString(20));
        material.setQuantity(resultSet.getDouble(21));
        material.setCoefficient(resultSet.getDouble(22));
        material.setTva(resultSet.getDouble(23));
        material.setTransportCost(resultSet.getDouble(24));
        material.setUnitPrice(resultSet.getDouble(25));

        Workforce workforce = new Workforce();
        workforce.setName(resultSet.getString(26));
        workforce.setTva(resultSet.getDouble(27));
        workforce.setPricePerHour(resultSet.getDouble(28));
        workforce.setWorkingHours(resultSet.getDouble(29));
        workforce.setProductivityFactor(resultSet.getDouble(30));

        Project project = new Project();
        project.setId(UUID.fromString(resultSet.getString(1)));
        project.setName(resultSet.getString(2));
        project.setSurface(resultSet.getDouble(3));
        project.setProjectStatus(ProjectStatus.valueOf(resultSet.getString(4)));
        project.setTotalCost(resultSet.getDouble(5));
        project.setProfitMargin(resultSet.getDouble(6));
        project.setTva(resultSet.getDouble(7));
        project.setClient(client);
        List<Material> materials = new ArrayList<>();
        materials.add(material);
        project.setMaterials(materials);

        List<Workforce> workforces = new ArrayList<>();
        workforces.add(workforce);
        project.setWorkforces(workforces);

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
