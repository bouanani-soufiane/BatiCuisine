package org.example.mappers.dtomapper;

import org.example.dtos.requests.ClientRequest;
import org.example.dtos.responses.ClientResponse;
import org.example.entities.Client;

import java.util.List;

public class ClientDtoMapper implements EntityDtoMapper<Client, ClientRequest, ClientResponse> {
    @Override
    public Client mapToEntity ( ClientRequest dto ) {
        return new Client(dto.name(), dto.address(), dto.phone(), dto.isProfessional(), List.of());
    }

    @Override
    public ClientResponse mapToDto ( Client client ) {
        return new ClientResponse(client.id(), client.name(), client.address(), client.phone(), client.isProfessional(), client.createdAt(), client.updatedAt());
    }
}
