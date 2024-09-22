package org.example.mappers.rowmapper;

import org.example.entities.Client;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class ClientRowMapper implements EntityRowMapper<Client> {
    @Override
    public Client map ( ResultSet resultSet ) throws SQLException {
        Client client =  new Client();
        client.setId((UUID) resultSet.getObject("id"));
        client.setName(resultSet.getString("client_name"));
        client.setAddress(resultSet.getString("address"));
        client.setPhone(resultSet.getString("phone"));
        client.setProfessional(resultSet.getBoolean("is_professional"));
        return client;
    }

    @Override
    public void map ( Client client, PreparedStatement stmt ) throws SQLException {
        int count = 1;
        stmt.setString(count++, client.name());
        stmt.setString(count++, client.address());
        stmt.setString(count++, client.phone());
        stmt.setBoolean(count++, client.isProfessional());
    }
}
