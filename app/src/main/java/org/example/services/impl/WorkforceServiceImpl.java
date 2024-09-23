package org.example.services.impl;

import org.example.dtos.requests.WorkforceRequest;
import org.example.dtos.responses.WorkforceResponse;
import org.example.entities.Workforce;
import org.example.mappers.dtomapper.EntityDtoMapper;
import org.example.reposiroties.interfaces.WorkforceRepository;
import org.example.services.interfaces.WorkforceService;

public class WorkforceServiceImpl implements WorkforceService {

    private final WorkforceRepository repository;
    private final EntityDtoMapper<Workforce, WorkforceRequest, WorkforceResponse> mapper;

    public WorkforceServiceImpl ( WorkforceRepository repository, EntityDtoMapper<Workforce, WorkforceRequest, WorkforceResponse> mapper ) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Workforce create ( WorkforceRequest workforceRequest ) {
        return this.repository.create(mapper.mapToEntity(workforceRequest));
    }
}
