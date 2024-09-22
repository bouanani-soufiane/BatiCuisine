package org.example.dtos.requests;

import org.example.entities.Project;

public record WorkforceRequest(
        String name,
        Double tva,
        Double price_per_hour,
        Double working_hours,
        Double productivity_factor,
        Project project
) {
}
