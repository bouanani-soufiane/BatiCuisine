package org.example.dtos.requests ;

import org.example.entities.Project;

public record MaterialRequest(
        String name,
        Double tva,
        Double quantity,
        Double unitPrice,
        Double transportCost,
        Double coefficient,
        Project project
) {
}
