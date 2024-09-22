package org.example.entities;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class Component  extends AbstractEntity{
    private UUID id;
    private String name;
    private Double tva;
    private Project project;

    public Component (){

    }

    public Component ( String name, Project project, Double tva ) {
        this.name = name;
        this.tva = tva ;
        this.project = project;
    }
    public Component ( String name, Project project ,Double tva, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt) {
        this(name, project ,tva);
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

    public Double tva () {
        return tva;
    }

    public Component setTva ( Double tva ) {
        this.tva = tva;
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
