package org.example.dtos.responses;

import org.example.enums.ProjectStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record ProjectResponse(
        UUID id,
        String name,
        Double surface,
        ProjectStatus projectStatus,
        Double totalCost,
        Double profitMargin,
        Double tva,
        ClientResponse client,
        LocalDateTime createdAt,
        LocalDateTime updatedAt

) {
}
