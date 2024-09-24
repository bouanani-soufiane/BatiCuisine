package org.example.entities;

import java.time.LocalDateTime;
import java.util.UUID;

public class Estimate extends AbstractEntity {
    private UUID id;
    private LocalDateTime issuedDate;
    private LocalDateTime validUntilDate;
    private Boolean isAccepted;
    private Double estimateAmount;
    private Project project;


    public Estimate () {
    }

    public Estimate (  LocalDateTime issuedDate, LocalDateTime validUntilDate , Boolean isAccepted , Double estimateAmount,Project project ) {
        this.issuedDate = issuedDate;
        this.validUntilDate = validUntilDate;
        this.isAccepted = isAccepted;
        this.estimateAmount = estimateAmount;
        this.project = project;
    }

    public Estimate (  LocalDateTime issuedDate, LocalDateTime validUntilDate ,Boolean isAccepted , Double estimateAmount ,Project project , LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt) {
        this(issuedDate, validUntilDate , isAccepted , estimateAmount ,  project);
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    @Override
    public UUID id () {
        return id;
    }


    public Estimate setId ( UUID id ) {
        this.id = id;
        return this;
    }

    public LocalDateTime issuedDate () {
        return issuedDate;
    }

    public Estimate setIssuedDate ( LocalDateTime issuedDate ) {
        this.issuedDate = issuedDate;
        return this;
    }

    public LocalDateTime validUntilDate () {
        return validUntilDate;
    }

    public Estimate setValidUntilDate ( LocalDateTime validUntilDate ) {
        this.validUntilDate = validUntilDate;
        return this;
    }
    public Boolean isAccepted () {
        return isAccepted;
    }

    public Estimate setAccepted ( Boolean accepted ) {
        isAccepted = accepted;
        return this;
    }

    public Double estimateAmount () {
        return estimateAmount;
    }

    public Estimate setEstimateAmount ( Double estimateAmount ) {
        this.estimateAmount = estimateAmount;
        return this;
    }

    public Project project () {
        return project;
    }

    public Estimate setProject ( Project project ) {
        this.project = project;
        return this;
    }
}
