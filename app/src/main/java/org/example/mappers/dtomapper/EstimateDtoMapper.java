package org.example.mappers.dtomapper;

import org.example.dtos.requests.EstimateRequest;
import org.example.dtos.responses.EstimateResponse;
import org.example.entities.Estimate;

public class EstimateDtoMapper implements EntityDtoMapper<Estimate, EstimateRequest, EstimateResponse> {
    @Override
    public Estimate mapToEntity ( EstimateRequest dto ) {
        return new Estimate(dto.issuedDate(), dto.validUntilDate(), dto.isAccepted(), dto.estimateAmount(), dto.project());

    }

    @Override
    public EstimateResponse mapToDto ( Estimate estimate ) {

        return new EstimateResponse(estimate.id(), estimate.issuedDate(), estimate.validUntilDate(), estimate.isAccepted(), estimate.estimateAmount(), estimate.project(), estimate.createdAt(), estimate.updatedAt());

    }
}
