package org.example.services.impl;

import org.example.dtos.requests.ProjectRequest;
import org.example.dtos.responses.ProjectResponse;
import org.example.entities.Project;
import org.example.exceptions.ProjectNotFound;
import org.example.mappers.dtomapper.EntityDtoMapper;
import org.example.reposiroties.interfaces.ProjectRepository;
import org.example.services.interfaces.ProjectService;

import java.util.List;
import java.util.UUID;

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

    @Override
    public Project findById ( UUID id ) {
        return repository.findById(id).orElseThrow(() -> new ProjectNotFound(id));
    }

    @Override
    public Project update ( UUID id, Project project ) {
        return repository.update(id, project);
    }

    @Override
    public List<Project> findAll () {
        return repository.findAll();
    }
}
