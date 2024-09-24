package org.example.exceptions;

import java.util.UUID;

public class EstimateNotFoundException extends RuntimeException {
    public EstimateNotFoundException ( UUID id ) {
        super("Estimate with id : " + id + " not found");
    }
}
