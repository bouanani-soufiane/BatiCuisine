package org.example.entities;

import org.example.enums.ComponentType;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class Component  extends AbstractEntity{
    private UUID id;
    private String name;
    private ComponentType componentType;
    private Project project;

    public Component (){

    }

    public Component ( String name, ComponentType componentType, Project project ) {
        this.name = name;
        this.componentType = componentType;
        this.project = project;
    }
    public Component ( String name, ComponentType componentType, Project project , LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt) {
        this(name , componentType, project);
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }


    @Override
    public UUID id () {
        return id;
    }

    public Component setId ( UUID id ) {
        this.id = id;
        return this;
    }

    public String name () {
        return name;
    }

    public Component setName ( String name ) {
        this.name = name;
        return this;
    }

    public ComponentType componentType () {
        return componentType;
    }

    public Component setComponentType ( ComponentType componentType ) {
        this.componentType = componentType;
        return this;
    }

    public Project project () {
        return project;
    }

    public Component setProject ( Project project ) {
        this.project = project;
        return this;
    }
}
