package org.example.services.interfaces;

import org.example.dtos.requests.ClientRequest;
import org.example.dtos.responses.ClientResponse;
import org.example.entities.Client;
import org.example.exceptions.ClientNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClientService {
    Client create ( ClientRequest client);
    List<ClientResponse> findAll();
    Client update( UUID id, ClientRequest entity);
    Optional<List<ClientResponse>> findByName( String value) throws ClientNotFoundException;


}
