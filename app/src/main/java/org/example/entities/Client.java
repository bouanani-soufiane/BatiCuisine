package org.example.entities;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class Client extends AbstractEntity{
    private UUID id;
    private String name;
    private String address;
    private String phone;
    private Boolean isProfessional;
    private List<Project> projects;

    public Client() {
    }

    public Client ( UUID id, String name, String address, String phone, Boolean isProfessional, List<Project> projects ) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.isProfessional = isProfessional;
        this.projects = projects;
    }
    public Client ( String name, String address, String phone, Boolean isProfessional, List<Project> projects ) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.isProfessional = isProfessional;
        this.projects = projects;
    }


    public Client ( String name, String address, String phone, Boolean isProfessional, List<Project> projects ,LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt) {
        this(name , address, phone, isProfessional, projects);
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    @Override
    public UUID id () {
        return id;
    }

    public Client setId ( UUID id ) {
        this.id = id;
        return this;
    }

    public String name () {
        return name;
    }

    public Client setName ( String name ) {
        this.name = name;
        return this;
    }

    public String address () {
        return address;
    }

    public Client setAddress ( String address ) {
        this.address = address;
        return this;
    }

    public String phone () {
        return phone;
    }

    public Client setPhone ( String phone ) {
        this.phone = phone;
        return this;
    }

    public Boolean isProfessional () {
        return isProfessional;
    }

    public Client setProfessional ( Boolean professional ) {
        isProfessional = professional;
        return this;
    }

    public List<Project> projects () {
        return projects;
    }

    public Client setProjects ( List<Project> projects ) {
        this.projects = projects;
        return this;
    }

    @Override
    public String toString () {
        return "Client{" +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", isProfessional=" + isProfessional +
                '}';
    }
}
