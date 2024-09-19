package org.example.reposiroties.impl;

import org.example.entities.Client;
import org.example.mappers.rowmapper.ClientRowMapper;
import org.example.mappers.rowmapper.EntityRowMapper;
import org.example.reposiroties.BaseRepositoryImpl;
import org.example.reposiroties.interfaces.ClientRepository;

import java.sql.ResultSet;
import java.util.UUID;
import static org.example.utils.persistence.QueryExecutor.executeQuery;

public class ClientRepositoryImpl extends BaseRepositoryImpl<Client , UUID> {

    public ClientRepositoryImpl (  ClientRowMapper entityRowMapper ) {
        super("clients", entityRowMapper);
    }

    @Override
    public Client create ( Client client ) {
        final String query = "INSERT INTO clients (name, address, phone, is_professional) VALUES(?,?,?,?) RETURNING *";
        return executeQuery(query, stmt -> {
            int count = 1;
            stmt.setString(count++, client.name());
            stmt.setString(count++, client.address());
            stmt.setString(count++, client.phone());
            stmt.setBoolean(count++, client.isProfessional());
            try (ResultSet resultSet = stmt.executeQuery()) {
               if(resultSet.next()) {
                   return entityRowMapper.map(resultSet);
               }
            }
            return null;
        });
    }


    @Override
    public Client update ( UUID uuid, Client client ) {
        return null;
    }
}
