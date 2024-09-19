package org.example.reposiroties.interfaces;

import org.example.dtos.responses.ClientResponse;
import org.example.entities.Client;
import org.example.exceptions.ClientNotFoundException;

import java.util.UUID;

public interface ClientRepository extends BaseRepository<Client , UUID> {
    Client getByName(String value) throws ClientNotFoundException;
}
