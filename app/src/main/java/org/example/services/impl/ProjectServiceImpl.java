package org.example.services.impl;

import org.example.dtos.requests.ProjectRequest;
import org.example.dtos.responses.ProjectResponse;
import org.example.entities.Project;
import org.example.mappers.dtomapper.EntityDtoMapper;
import org.example.reposiroties.interfaces.ProjectRepository;
import org.example.services.interfaces.ProjectService;

public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository repository;
    private EntityDtoMapper<Project, ProjectRequest, ProjectResponse> projectDtoMapper;
     public ProjectServiceImpl ( ProjectRepository repository, EntityDtoMapper<Project, ProjectRequest, ProjectResponse> projectDtoMapper ) {
        this.repository = repository;
        this.projectDtoMapper = projectDtoMapper;
    }

    @Override
    public Project create ( ProjectRequest project ) {
        return repository.create(projectDtoMapper.mapToEntity(project));
    }
}
