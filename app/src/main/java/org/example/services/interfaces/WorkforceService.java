package org.example.services.interfaces;

import org.example.dtos.requests.WorkforceRequest;
import org.example.entities.Workforce;

public interface WorkforceService {
    Workforce create ( WorkforceRequest workforceRequest );

}



modified:   app/src/main/java/org/example/GUI/ClientUI.java
modified:   app/src/main/java/org/example/GUI/MainGUI.java
modified:   app/src/main/java/org/example/GUI/ProjectUI.java
modified:   app/src/main/java/org/example/dtos/responses/ProjectResponse.java
modified:   app/src/main/java/org/example/entities/Component.java
modified:   app/src/main/java/org/example/entities/Material.java
modified:   app/src/main/java/org/example/entities/Workforce.java
modified:   app/src/main/java/org/example/mappers/dtomapper/ClientDtoMapper.java
modified:   app/src/main/java/org/example/mappers/dtomapper/ProjectDtoMapper.java
modified:   app/src/main/java/org/example/mappers/rowmapper/ClientRowMapper.java
modified:   app/src/main/java/org/example/mappers/rowmapper/ProjectRowMapper.java
modified:   app/src/main/java/org/example/reposiroties/impl/ClientRepositoryImpl.java
modified:   app/src/main/java/org/example/reposiroties/impl/ProjectRepositoryImpl.java
modified:   app/src/main/java/org/example/reposiroties/interfaces/BaseRepository.java
modified:   app/src/main/java/org/example/reposiroties/interfaces/ClientRepository.java
modified:   app/src/main/java/org/example/reposiroties/interfaces/ProjectRepository.java
modified:   app/src/main/java/org/example/services/impl/ClientServiceImpl.java
modified:   app/src/main/java/org/example/services/impl/ProjectServiceImpl.java
modified:   app/src/main/java/org/example/services/interfaces/ClientService.java

Untracked files:
        (use "git add <file>..." to include in what will be committed)
app/src/main/java/org/example/GUI/MaterialUI.java
app/src/main/java/org/example/dtos/requests/MaterialRequest.java
app/src/main/java/org/example/dtos/requests/WorkforceRequest.java
app/src/main/java/org/example/dtos/responses/MaterialResponse.java
app/src/main/java/org/example/dtos/responses/WorkforceResponse.java
app/src/main/java/org/example/mappers/dtomapper/MaterialDtoMapper.java
app/src/main/java/org/example/mappers/dtomapper/WorkforceDtoMapper.java
app/src/main/java/org/example/mappers/rowmapper/MaterialRowMapper.java
app/src/main/java/org/example/mappers/rowmapper/WorkforceRowMapper.java
app/src/main/java/org/example/reposiroties/impl/MaterialRepositoryImpl.java
app/src/main/java/org/example/reposiroties/impl/WorkforceRepositoryImpl.java
app/src/main/java/org/example/reposiroties/interfaces/MaterialRepository.java
app/src/main/java/org/example/reposiroties/interfaces/WorkforceRepository.java
app/src/main/java/org/example/services/impl/MaterialServiceImpl.java
app/src/main/java/org/example/services/impl/WorkforceServiceImpl.java
app/src/main/java/org/example/services/interfaces/MaterialService.java
app/src/main/java/org/example/services/interfaces/WorkforceService.java

fala7on@archlinux  ~/Desktop/BatiCuisine   BAT-2-Project-Management ✚ ● ? 