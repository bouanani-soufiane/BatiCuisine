package org.example.services.interfaces;

import org.example.dtos.requests.MaterialRequest;
import org.example.entities.Material;

import java.util.List;

public interface MaterialService {

    List<Material> create ( List<MaterialRequest> materials );
}
