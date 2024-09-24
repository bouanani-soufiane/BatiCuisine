package org.example.services.interfaces;

import org.example.dtos.requests.EstimateRequest;
import org.example.dtos.requests.MaterialRequest;
import org.example.dtos.requests.WorkforceRequest;
import org.example.entities.Estimate;

import java.util.List;

public interface EstimateService {

    Estimate create(EstimateRequest estimateRequest);

    Double calcCostMaterial(MaterialRequest material);

    Double calcCostMaterialWithTva(MaterialRequest material);

    Double calcCostWorkforce(WorkforceRequest workforce);

    Double calcCostWorkforceWithTva(WorkforceRequest workforce);

    Double calcCostMaterials(List<MaterialRequest> materials);

    Double calcCostMaterialsWithTva(List<MaterialRequest> materials);

    Double calcCostWorkforces(List<WorkforceRequest> workforces);

    Double calcCostWorkforcesWithTva(List<WorkforceRequest> workforces);

    Double calcProfitMargin(List<MaterialRequest> materials, List<WorkforceRequest> workforces, Double profitMargin);

    Double calcTotalCost(List<MaterialRequest> materials, List<WorkforceRequest> workforces, Double profitMargin);

    Double calcTotalCost(List<MaterialRequest> materials, List<WorkforceRequest> workforces);
}
