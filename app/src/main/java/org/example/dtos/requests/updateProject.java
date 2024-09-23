package org.example.dtos.requests;

import java.util.UUID;

public record updateProject(UUID projectId, double profitMargin, double totalCost) {

}
