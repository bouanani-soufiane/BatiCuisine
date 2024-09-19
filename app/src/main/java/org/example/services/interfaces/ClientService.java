package org.example.services.interfaces;

import org.example.dtos.requests.ClientRequest;
import org.example.dtos.responses.ClientResponse;
import org.example.entities.Client;

import java.util.List;

public interface ClientService {
    Client create ( ClientRequest client);
    List<ClientResponse> findAll();

}
