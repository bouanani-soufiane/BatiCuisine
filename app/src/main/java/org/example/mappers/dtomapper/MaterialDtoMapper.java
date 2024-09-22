package org.example.mappers.dtomapper;

import org.example.dtos.requests.MaterialRequest;
import org.example.dtos.responses.MaterialResponse;
import org.example.entities.Material;

public class MaterialDtoMapper implements EntityDtoMapper<Material, MaterialRequest, MaterialResponse> {

    @Override
    public Material mapToEntity ( MaterialRequest dto ) {
        return new Material(dto.name(), dto.project(), dto.tva(), dto.quantity(), dto.unitPrice(), dto.transportCost(), dto.coefficient());
    }

    @Override
    public MaterialResponse mapToDto ( Material material ) {
        return new MaterialResponse(material.id(), material.name(), material.tva(), material.quantity(), material.unitPrice(), material.transportCost(), material.coefficient(), material.project(), material.createdAt(), material.updatedAt());
    }
}
