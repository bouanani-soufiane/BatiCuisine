package org.example.services.interfaces;

import org.example.dtos.requests.ProjectRequest;
import org.example.entities.Project;

public interface ProjectService {
    Project create ( ProjectRequest project);

}
