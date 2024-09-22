package org.example.dtos.responses;

import org.example.entities.Project;
import java.time.LocalDateTime;
import java.util.UUID;

public record WorkforceResponse(
        UUID id,
        String name,
        Double tva,
        Double price_per_hour,
        Double working_hours,
        Double productivity_factor,
        Project project,
        LocalDateTime createdAt,
        LocalDateTime updatedAt

) {
}
