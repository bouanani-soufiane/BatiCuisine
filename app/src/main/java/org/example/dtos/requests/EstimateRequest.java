package org.example.dtos.requests;

import org.example.entities.Project;

import java.time.LocalDateTime;

public record EstimateRequest(
        LocalDateTime issuedDate,
        LocalDateTime validUntilDate,
        Boolean isAccepted,
        Double estimateAmount,
        Project project
) {
}
