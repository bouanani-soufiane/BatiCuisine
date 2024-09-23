package org.example.services.interfaces;

import org.example.dtos.requests.ProjectRequest;
import org.example.entities.Project;

import java.util.UUID;

public interface ProjectService {
    Project create ( ProjectRequest project );

    Project findById ( UUID id );

    Project update ( UUID id, Project project );

}
