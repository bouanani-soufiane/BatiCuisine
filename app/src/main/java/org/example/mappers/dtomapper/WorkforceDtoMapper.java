package org.example.mappers.dtomapper;

import org.example.dtos.requests.WorkforceRequest;
import org.example.dtos.responses.WorkforceResponse;
import org.example.entities.Workforce;

public class WorkforceDtoMapper implements EntityDtoMapper<Workforce, WorkforceRequest, WorkforceResponse> {

    @Override
    public Workforce mapToEntity ( WorkforceRequest dto ) {
        return new Workforce(dto.name(), dto.project(), dto.tva(), dto.price_per_hour(), dto.working_hours(), dto.productivity_factor());
    }

    @Override
    public WorkforceResponse mapToDto ( Workforce workforce ) {
        return new WorkforceResponse(workforce.id(), workforce.name(), workforce.tva(), workforce.pricePerHour(), workforce.workingHours(), workforce.productivityFactor(), workforce.project(), workforce.createdAt(), workforce.updatedAt());
    }
}
