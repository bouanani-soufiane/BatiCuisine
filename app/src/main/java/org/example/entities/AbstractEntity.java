package org.example.entities;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class AbstractEntity{

    protected LocalDateTime createdAt;
    protected LocalDateTime updatedAt;
    protected LocalDateTime deletedAt;

    public abstract UUID id();



    public LocalDateTime createdAt() {
        return createdAt;
    }

    public AbstractEntity setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public LocalDateTime updatedAt() {
        return updatedAt;
    }

    public AbstractEntity setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public LocalDateTime deletedAt() {
        return deletedAt;
    }

    public AbstractEntity setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
        return this;
    }
}
