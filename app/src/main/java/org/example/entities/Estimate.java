package org.example.entities;

import java.time.LocalDateTime;
import java.util.UUID;

public class Estimate extends AbstractEntity {
    private UUID id;
    private LocalDateTime issuedDate;
    private LocalDateTime validUntilDate;

    public Estimate () {
    }

    public Estimate (  LocalDateTime issuedDate, LocalDateTime validUntilDate ) {
        this.issuedDate = issuedDate;
        this.validUntilDate = validUntilDate;
    }

    public Estimate (  LocalDateTime issuedDate, LocalDateTime validUntilDate  , LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt) {
        this(issuedDate, validUntilDate);
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
}
