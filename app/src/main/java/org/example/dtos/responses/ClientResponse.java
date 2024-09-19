package org.example.dtos.responses;

import java.time.LocalDateTime;
import java.util.UUID;

public record ClientResponse (
        UUID id,
        String name,
        String address,
        String phone,
        Boolean isProfessional,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}