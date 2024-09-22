package org.example.services.interfaces;

import org.example.dtos.requests.WorkforceRequest;
import org.example.entities.Workforce;

public interface WorkforceService {
    Workforce create ( WorkforceRequest workforceRequest );

}


