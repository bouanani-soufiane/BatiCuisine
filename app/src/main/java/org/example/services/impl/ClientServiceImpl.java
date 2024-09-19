package org.example.services.impl;

import org.example.dtos.requests.ClientRequest;
import org.example.dtos.responses.ClientResponse;
import org.example.entities.Client;
import org.example.mappers.dtomapper.ClientDtoMapper;
import org.example.mappers.dtomapper.EntityDtoMapper;
import org.example.reposiroties.impl.ClientRepositoryImpl;
import org.example.reposiroties.interfaces.ClientRepository;
import org.example.services.interfaces.ClientService;

import java.util.List;

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
}
