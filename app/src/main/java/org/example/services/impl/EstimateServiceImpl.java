package org.example.services.impl;

import org.example.dtos.requests.EstimateRequest;
import org.example.dtos.requests.MaterialRequest;
import org.example.dtos.requests.WorkforceRequest;
import org.example.dtos.responses.EstimateResponse;
import org.example.entities.Estimate;
import org.example.mappers.dtomapper.EntityDtoMapper;
import org.example.reposiroties.interfaces.EstimateRepository;
import org.example.services.interfaces.EstimateService;

import java.util.List;

public class EstimateServiceImpl implements EstimateService {

    private final EstimateRepository repository;
    private final EntityDtoMapper<Estimate, EstimateRequest, EstimateResponse> estimateDtoMapper;

    public EstimateServiceImpl ( EstimateRepository repository, EntityDtoMapper<Estimate, EstimateRequest, EstimateResponse> estimateDtoMapper ) {
        this.repository = repository;
        this.estimateDtoMapper = estimateDtoMapper;
    }

    @Override
    public Estimate create ( EstimateRequest estimateRequest ) {
        return repository.create(estimateDtoMapper.mapToEntity(estimateRequest));
    }

    @Override
    public Double calcCostMaterial ( MaterialRequest material ) {

        return material.quantity() * material.coefficient() * material.unitPrice() + material.transportCost();
    }

    @Override
    public Double calcCostMaterialWithTva ( MaterialRequest material ) {

        return (material.quantity() * material.coefficient() * material.unitPrice() + material.transportCost()) * (1 + material.tva() / 100);
    }

    @Override
    public Double calcCostWorkforce ( WorkforceRequest workforce ) {
        return workforce.price_per_hour() * workforce.working_hours() * workforce.productivity_factor();
    }

    @Override
    public Double calcCostWorkforceWithTva ( WorkforceRequest workforce ) {
        return (workforce.price_per_hour() * workforce.working_hours() * workforce.productivity_factor()) * (1 + workforce.tva() / 100);
    }

    @Override
    public Double calcCostMaterials ( List<MaterialRequest> materials ) {
        return materials.stream().mapToDouble(this::calcCostMaterial).sum();
    }

    @Override
    public Double calcCostMaterialsWithTva ( List<MaterialRequest> materials ) {
        return materials.stream().mapToDouble(this::calcCostMaterialWithTva).sum();
    }

    @Override
    public Double calcCostWorkforces ( List<WorkforceRequest> workforces ) {
        return workforces.stream().mapToDouble(this::calcCostWorkforce).sum();
    }

    @Override
    public Double calcCostWorkforcesWithTva ( List<WorkforceRequest> workforces ) {
        return workforces.stream().mapToDouble(this::calcCostWorkforceWithTva).sum();
    }

    @Override
    public Double calcProfitMargin ( List<MaterialRequest> materials, List<WorkforceRequest> workforces, Double profitMargin ) {
        return (this.calcCostMaterialsWithTva(materials) + this.calcCostWorkforcesWithTva(workforces)) * profitMargin / 100;
    }

    @Override
    public Double calcTotalCost ( List<MaterialRequest> materials, List<WorkforceRequest> workforces ) {
        return (this.calcCostMaterialsWithTva(materials) + this.calcCostWorkforcesWithTva(workforces));
    }

    @Override
    public Double calcTotalCost ( List<MaterialRequest> materials, List<WorkforceRequest> workforces, Double profitMargin ) {
        return (this.calcCostMaterialsWithTva(materials) + this.calcCostWorkforcesWithTva(workforces)) + this.calcProfitMargin(materials, workforces, profitMargin);
    }




}
