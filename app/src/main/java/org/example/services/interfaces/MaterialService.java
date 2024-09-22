package org.example.services.interfaces;

import org.example.dtos.requests.MaterialRequest;
import org.example.entities.Material;

public interface MaterialService {

    Material create ( MaterialRequest material );
}
