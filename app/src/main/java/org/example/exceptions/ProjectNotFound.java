package org.example.exceptions;

import java.util.UUID;

public class ProjectNotFound extends RuntimeException{

    private final UUID projectId;
    private final  String projectName;
    public ProjectNotFound(String projectName) {
        super("project with name (" + projectName + ") not found");
        this.projectId = null;
        this.projectName = projectName;
    }
    public ProjectNotFound(UUID id) {
        super("project with ID (" + id + ") not found");
        this.projectId = id;
        this.projectName = null;
    }
}
