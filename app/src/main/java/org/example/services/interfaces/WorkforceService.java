package org.example.services.interfaces;

import org.example.dtos.requests.WorkforceRequest;
import org.example.entities.Workforce;

import java.util.List;

public interface WorkforceService {
    List<Workforce> create ( List<WorkforceRequest> workforceRequests );

}


