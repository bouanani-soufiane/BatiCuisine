package org.example.dtos.requests;

import org.example.entities.Client;
import org.example.enums.ProjectStatus;

public record ProjectRequest (
        String name,
        Double surface,
        ProjectStatus projectStatus,
        Client client
){
}
