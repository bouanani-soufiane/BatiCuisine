package org.example.GUI;

import org.example.dtos.requests.ProjectRequest;
import org.example.dtos.requests.WorkforceRequest;
import org.example.dtos.responses.ProjectResponse;
import org.example.dtos.responses.WorkforceResponse;
import org.example.entities.Material;
import org.example.entities.Project;
import org.example.entities.Workforce;
import org.example.mappers.dtomapper.*;
import org.example.mappers.rowmapper.*;
import org.example.reposiroties.impl.ClientRepositoryImpl;
import org.example.reposiroties.impl.MaterialRepositoryImpl;
import org.example.reposiroties.impl.ProjectRepositoryImpl;
import org.example.reposiroties.impl.WorkforceRepositoryImpl;
import org.example.reposiroties.interfaces.MaterialRepository;
import org.example.reposiroties.interfaces.ProjectRepository;
import org.example.reposiroties.interfaces.WorkforceRepository;
import org.example.services.impl.ClientServiceImpl;
import org.example.services.impl.MaterialServiceImpl;
import org.example.services.impl.ProjectServiceImpl;
import org.example.services.impl.WorkforceServiceImpl;
import org.example.services.interfaces.ClientService;
import org.example.services.interfaces.MaterialService;
import org.example.services.interfaces.ProjectService;
import org.example.services.interfaces.WorkforceService;

import java.util.Scanner;

import static org.example.utils.Print.title;
import static org.example.utils.ValidatedInputReader.scanInt;
import static org.example.utils.ValidationCriteria.*;

public class MainGUI {

    private final ClientUI clientUI;
    private final ProjectUI projectUI;


    public MainGUI ( Scanner scanner ) {
        ClientRowMapper clientRowMapper = new ClientRowMapper();
        ClientDtoMapper clientDtoMapper = new ClientDtoMapper();
        ClientRepositoryImpl clientRepository = new ClientRepositoryImpl(clientRowMapper);
        ClientService clientService = new ClientServiceImpl(clientRepository, clientDtoMapper);
        EntityRowMapper<Project> projectRowMapper = new ProjectRowMapper(clientRowMapper);
        ProjectRepository projectRepository = new ProjectRepositoryImpl(projectRowMapper);
        EntityDtoMapper<Project, ProjectRequest, ProjectResponse> projectDtoMapper = new ProjectDtoMapper(clientDtoMapper);
        ProjectService projectService = new ProjectServiceImpl(projectRepository, projectDtoMapper);
        this.clientUI = new ClientUI(clientService, clientDtoMapper);
        EntityRowMapper<Material> materialRowMapper = new MaterialRowMapper(projectRowMapper);
        MaterialRepository materialRepository = new MaterialRepositoryImpl(materialRowMapper);
        MaterialDtoMapper materialDtoMapper = new MaterialDtoMapper();
        MaterialService materialService = new MaterialServiceImpl(materialRepository, materialDtoMapper);
        EntityRowMapper<Workforce> workforceRowMapper = new WorkforceRowMapper(projectRowMapper);
        WorkforceRepository workforceRepository = new WorkforceRepositoryImpl(workforceRowMapper);
        EntityDtoMapper<Workforce, WorkforceRequest, WorkforceResponse> workforceDtoMapper = new WorkforceDtoMapper();
        WorkforceService workforceService = new WorkforceServiceImpl(workforceRepository,workforceDtoMapper);

        this.projectUI = new ProjectUI(projectService, clientService, clientUI, clientDtoMapper, this, materialService,workforceService);
    }

    public void menu () {
        title(" ⚙ Welcome to BatiCuisine menu \uD83D\uDC77");
        System.out.println("1. Client Management");
        System.out.println("2. Project Management");
        System.out.println("3. Quotation Management");
        System.out.println("4. Exit");


        Integer userChoice = scanInt("Please to enter you choice: ", POSITIVE_INT);

        switch (userChoice) {
            case 1 -> clientUI.showMenu();
            case 2 -> projectUI.showMenu();
            case 3 -> System.out.println("Quotation management");
            case 4 -> {
                System.out.println("Good bye");
                System.exit(0);
            }
            default -> throw new IllegalArgumentException("Invalid choice");
        }
    }

}




