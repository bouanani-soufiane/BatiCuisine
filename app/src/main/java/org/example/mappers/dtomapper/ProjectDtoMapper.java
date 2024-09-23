package org.example.mappers.dtomapper;

import org.example.dtos.requests.ProjectRequest;
import org.example.dtos.responses.ProjectResponse;
import org.example.entities.Project;

public class ProjectDtoMapper implements EntityDtoMapper<Project, ProjectRequest, ProjectResponse> {

    private final ClientDtoMapper clientDtoMapper;

    public ProjectDtoMapper ( ClientDtoMapper clientDtoMapper ) {
        this.clientDtoMapper = clientDtoMapper;
    }

    @Override
    public Project mapToEntity ( ProjectRequest dto ) {
        return new Project(dto.name(), dto.surface(), dto.projectStatus(), dto.client());
    }

    @Override
    public ProjectResponse mapToDto ( Project project ) {
        return new ProjectResponse(project.id(), project.name(), project.surface(), project.projectStatus(), project.totalCost(), project.profitMargin(), clientDtoMapper.mapToDto(project.client()), project.createdAt(), project.updatedAt());
    }
}
