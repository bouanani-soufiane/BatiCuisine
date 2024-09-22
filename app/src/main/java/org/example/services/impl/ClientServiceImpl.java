package org.example.services.impl;

import org.example.dtos.requests.ClientRequest;
import org.example.dtos.responses.ClientResponse;
import org.example.entities.Client;
import org.example.exceptions.ClientNotFoundException;
import org.example.mappers.dtomapper.ClientDtoMapper;
import org.example.reposiroties.impl.ClientRepositoryImpl;
import org.example.services.interfaces.ClientService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ClientServiceImpl implements ClientService {


    private final ClientRepositoryImpl repository;
    private final ClientDtoMapper mapper;
    public ClientServiceImpl(ClientRepositoryImpl repository , ClientDtoMapper mapper ) {
        this.repository = repository;
        this.mapper = mapper;
    }


    @Override
    public Client create ( ClientRequest dto ) {
        return repository.create(mapper.mapToEntity(dto));

    }

    @Override
    public List<ClientResponse> findAll () {
        return repository.findAll().stream().map(mapper::mapToDto).toList();

    }

    @Override
    public Client update ( UUID id, ClientRequest dto ) {
        return repository.update(id, mapper.mapToEntity(dto));
    }

    @Override
    public Optional<List<ClientResponse>> findByName(String value) throws ClientNotFoundException {
        List<Client> clients = repository.getByName(value)
                .orElseThrow(() -> new ClientNotFoundException("Client " + value + " was not found"));

        List<ClientResponse> clientResponses = clients.stream()
                .map(mapper::mapToDto).toList();

        return Optional.of(clientResponses);
    }



}
