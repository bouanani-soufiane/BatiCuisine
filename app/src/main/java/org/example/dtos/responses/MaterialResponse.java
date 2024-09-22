package org.example.dtos.responses;

import org.example.entities.Project;
import java.time.LocalDateTime;
import java.util.UUID;

public record MaterialResponse(
        UUID id,
        String name,
        Double tva,
        Double quantity,
        Double unitPrice,
        Double transportCost,
        Double coefficient,
        Project project,
        LocalDateTime createdAt,
        LocalDateTime updatedAt

) {
}
