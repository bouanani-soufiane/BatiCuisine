package org.example.reposiroties.impl;

import org.example.entities.Client;
import org.example.exceptions.ClientNotFoundException;
import org.example.mappers.rowmapper.ClientRowMapper;
import org.example.reposiroties.BaseRepositoryImpl;
import org.example.reposiroties.interfaces.ClientRepository;
import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static org.example.utils.persistence.QueryExecutor.executeQuery;

public class ClientRepositoryImpl extends BaseRepositoryImpl<Client , UUID> implements ClientRepository {

    public ClientRepositoryImpl (  ClientRowMapper entityRowMapper ) {
        super("clients", entityRowMapper);
    }

    @Override
    public Client create ( Client client ) {
        final String query = "INSERT INTO clients (client_name, address, phone, is_professional) VALUES(?,?,?,?) RETURNING *";

        return executeQuery(query, stmt -> {
            entityRowMapper.map(client,stmt);
            try (ResultSet resultSet = stmt.executeQuery()) {
                return resultSet.next() ? entityRowMapper.map(resultSet) : null;
            }
        });
    }


    @Override
    public Client update(UUID uuid, Client client) {
        final String query = "UPDATE clients SET client_name = ?, address = ?, phone = ?, is_professional = ?, updated_at = now() WHERE id = ? RETURNING *";

         return executeQuery(query, stmt ->{
             entityRowMapper.map(client,stmt);
             try (ResultSet resultSet = stmt.executeQuery()) {
                 if(resultSet.next()) {
                     return entityRowMapper.map(resultSet);
                 }
             }
             return null;

         });
    }

    @Override
    public Optional<List<Client>> getByName(String value) throws ClientNotFoundException {
        List<Client> clients = this.findByColumn("client_name", value)
                .orElseThrow(() -> new ClientNotFoundException("Client with name (" + value + ") was not found"));
        return Optional.of(clients);
    }

}
