package org.example.entities;

import org.example.enums.ProjectStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public class Project  extends AbstractEntity{
    private UUID id;
    private String name;
    private Double surface;
    private ProjectStatus projectStatus;
    private Double totalCost;
    private Double profitMargin;
    private Double tva;
    private Client client;

    public Project (){
    }

    public Project ( String name, Double surface, ProjectStatus projectStatus, Double totalCost, Double profitMargin, Double tva, Client client ) {
        this.name = name;
        this.surface = surface;
        this.projectStatus = projectStatus;
        this.totalCost = totalCost;
        this.profitMargin = profitMargin;
        this.tva = tva;
        this.client = client;
    }

    public Project ( String name, Double surface, ProjectStatus projectStatus, Double totalCost, Double profitMargin, Double tva, Client client, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt ) {
       this(name,surface,projectStatus,totalCost,profitMargin,tva,client);
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    @Override
    public UUID id () {
        return id;
    }

    public Project setId ( UUID id ) {
        this.id = id;
        return this;
    }

    public String name () {
        return name;
    }

    public Project setName ( String name ) {
        this.name = name;
        return this;
    }

    public Double surface () {
        return surface;
    }

    public Project setSurface ( Double surface ) {
        this.surface = surface;
        return this;
    }

    public ProjectStatus projectStatus () {
        return projectStatus;
    }

    public Project setProjectStatus ( ProjectStatus projectStatus ) {
        this.projectStatus = projectStatus;
        return this;
    }

    public Double totalCost () {
        return totalCost;
    }

    public Project setTotalCost ( Double totalCost ) {
        this.totalCost = totalCost;
        return this;
    }

    public Double profitMargin () {
        return profitMargin;
    }

    public Project setProfitMargin ( Double profitMargin ) {
        this.profitMargin = profitMargin;
        return this;
    }

    public Double tva () {
        return tva;
    }

    public Project setTva ( Double tva ) {
        this.tva = tva;
        return this;
    }

    public Client client () {
        return client;
    }

    public Project setClient ( Client client ) {
        this.client = client;
        return this;
    }
}
