package org.example.reposiroties.interfaces;

import org.example.dtos.responses.ClientResponse;
import org.example.entities.Client;
import org.example.exceptions.ClientNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClientRepository extends BaseRepository<Client , UUID> {
    Optional<List<Client> > getByName( String value) throws ClientNotFoundException;
}
