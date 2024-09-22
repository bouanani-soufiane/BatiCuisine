package org.example.reposiroties.impl;

import org.example.entities.Material;
import org.example.mappers.rowmapper.EntityRowMapper;
import org.example.reposiroties.BaseRepositoryImpl;
import org.example.reposiroties.interfaces.MaterialRepository;

import java.sql.ResultSet;
import java.util.UUID;

import static org.example.utils.persistence.QueryExecutor.executeQuery;

public class MaterialRepositoryImpl extends BaseRepositoryImpl<Material , UUID>  implements MaterialRepository {

    public MaterialRepositoryImpl (  EntityRowMapper<Material> entityRowMapper ) {
        super("materials", entityRowMapper);
    }

    @Override
    public Material create ( Material material ) {
        String query = "WITH inserted_material AS ( " +
                "    INSERT INTO materials (name, quantity, unit_price, tva, transport_cost, coefficient, project_id) " +
                "    VALUES (?, ?, ?, ?, ?, ?, ?) " +
                "    RETURNING * " +
                ") " +
                "SELECT im.*, p.*, c.* " +
                "FROM inserted_material im " +
                "JOIN projects p ON im.project_id = p.id " +
                "JOIN clients c ON p.client_id = c.id";


        return executeQuery(query, stmt -> {
            entityRowMapper.map(material,stmt);
            try (ResultSet resultSet = stmt.executeQuery()) {
                return resultSet.next() ? entityRowMapper.map(resultSet) : null;
            }
        });
    }

    @Override
    public Material update ( UUID uuid, Material material ) {
        return null;
    }
}
