package org.example.mappers.rowmapper;

import org.example.entities.Material;
import org.example.entities.Project;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class MaterialRowMapper implements EntityRowMapper<Material>{
    private final EntityRowMapper<Project> projectRowMapper;

    public MaterialRowMapper(EntityRowMapper<Project> projectRowMapper) {
        this.projectRowMapper = projectRowMapper;
    }

    @Override
    public Material map ( ResultSet resultSet ) throws SQLException {
        Material material = new Material();

        material.setId((UUID) resultSet.getObject("id"));
        material.setName(resultSet.getString("name"));
        material.setTva(resultSet.getDouble("tva"));
        material.setCoefficient(resultSet.getDouble("coefficient"));
        material.setQuantity(resultSet.getDouble("quantity"));
        material.setUnitPrice(resultSet.getDouble("unit_price"));
        material.setTransportCost(resultSet.getDouble("transport_cost"));
        material.setProject(projectRowMapper.map (resultSet));

        return material;
    }

    @Override
    public void map ( Material material, PreparedStatement stmt ) throws SQLException {
        int counter = 1;
        stmt.setString(counter++ , material.name());
        stmt.setDouble(counter++ , material.quantity());
        stmt.setDouble(counter++ , material.unitPrice());
        stmt.setDouble(counter++ , material.tva());
        stmt.setDouble(counter++ , material.transportCost());
        stmt.setDouble(counter++ , material.coefficient());
        stmt.setObject(counter++ , material.project().id());
    }
}
