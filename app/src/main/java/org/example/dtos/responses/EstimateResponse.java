package org.example.dtos.responses;

import org.example.entities.Project;

import java.time.LocalDateTime;
import java.util.UUID;

public record EstimateResponse(
        UUID id,
        LocalDateTime issuedDate,
        LocalDateTime validUntilDate,
        Boolean isAccepted,
        Double estimateAmount,
        Project project,
        LocalDateTime createdAt,
        LocalDateTime updatedAt) {
}
