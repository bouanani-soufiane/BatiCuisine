package org.example.services.impl;

import org.example.dtos.requests.MaterialRequest;
import org.example.dtos.responses.MaterialResponse;
import org.example.entities.Material;
import org.example.mappers.dtomapper.EntityDtoMapper;
import org.example.reposiroties.interfaces.MaterialRepository;
import org.example.services.interfaces.MaterialService;

public class MaterialServiceImpl implements MaterialService {

    private final MaterialRepository repository;
    private final EntityDtoMapper<Material, MaterialRequest, MaterialResponse> mapper;

    public MaterialServiceImpl ( MaterialRepository repository, EntityDtoMapper<Material, MaterialRequest, MaterialResponse> mapper ) {
        this.repository = repository;
        this.mapper = mapper;
    }


    @Override
    public Material create ( MaterialRequest material ) {
        return repository.create(mapper.mapToEntity(material));
    }
}
