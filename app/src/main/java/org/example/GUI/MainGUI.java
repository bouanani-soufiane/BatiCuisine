package org.example.GUI;

import org.example.dtos.requests.EstimateRequest;
import org.example.dtos.requests.ProjectRequest;
import org.example.dtos.requests.WorkforceRequest;
import org.example.dtos.responses.EstimateResponse;
import org.example.dtos.responses.ProjectResponse;
import org.example.dtos.responses.WorkforceResponse;
import org.example.entities.Estimate;
import org.example.entities.Material;
import org.example.entities.Project;
import org.example.entities.Workforce;
import org.example.mappers.dtomapper.*;
import org.example.mappers.rowmapper.*;
import org.example.reposiroties.impl.*;
import org.example.reposiroties.interfaces.EstimateRepository;
import org.example.reposiroties.interfaces.MaterialRepository;
import org.example.reposiroties.interfaces.ProjectRepository;
import org.example.reposiroties.interfaces.WorkforceRepository;
import org.example.services.impl.*;
import org.example.services.interfaces.*;

import java.util.Scanner;

import static org.example.utils.Print.title;
import static org.example.utils.ValidatedInputReader.scanInt;
import static org.example.utils.ValidationCriteria.POSITIVE_INT;

public class MainGUI {

    private final ClientUI clientUI;
    private final ProjectUI projectUI;
    private final EstimateUI estimateUI;
    private final ClientService clientService;
    private final ClientDtoMapper clientDtoMapper;
    private final ProjectService projectService;
    private final MaterialService materialService;
    private final WorkforceService workforceService;
    private final EstimateService estimateService;


    public MainGUI ( Scanner scanner ) {
        ClientRowMapper clientRowMapper = new ClientRowMapper();
        this.clientDtoMapper = new ClientDtoMapper();
        ClientRepositoryImpl clientRepository = new ClientRepositoryImpl(clientRowMapper);
        this.clientService = new ClientServiceImpl(clientRepository, clientDtoMapper);

        ProjectRowMapper projectRowMapper = new ProjectRowMapper(clientRowMapper);
        ProjectRepository projectRepository = new ProjectRepositoryImpl(projectRowMapper);
        EntityDtoMapper<Project, ProjectRequest, ProjectResponse> projectDtoMapper = new ProjectDtoMapper(clientDtoMapper);
        this.projectService = new ProjectServiceImpl(projectRepository, projectDtoMapper);

        this.clientUI = new ClientUI(clientService, clientDtoMapper, this);

        EntityRowMapper<Material> materialRowMapper = new MaterialRowMapper(projectRowMapper);
        MaterialRepository materialRepository = new MaterialRepositoryImpl(materialRowMapper);
        MaterialDtoMapper materialDtoMapper = new MaterialDtoMapper();
        this.materialService = new MaterialServiceImpl(materialRepository, materialDtoMapper);

        EntityRowMapper<Workforce> workforceRowMapper = new WorkforceRowMapper(projectRowMapper);
        WorkforceRepository workforceRepository = new WorkforceRepositoryImpl(workforceRowMapper);
        EntityDtoMapper<Workforce, WorkforceRequest, WorkforceResponse> workforceDtoMapper = new WorkforceDtoMapper();
        this.workforceService = new WorkforceServiceImpl(workforceRepository, workforceDtoMapper);

        EntityDtoMapper<Estimate, EstimateRequest, EstimateResponse> estimateDtoMapper = new EstimateDtoMapper();
        EntityRowMapper<Estimate> estimateRowMapper = new EstimateRowMapper(projectRowMapper);
        EstimateRepository estimateRepository = new EstimateRepositoryImpl(estimateRowMapper);
        this.estimateService = new EstimateServiceImpl(estimateRepository, estimateDtoMapper);
        WorkforceDtoMapper workforceDtoMappers = new WorkforceDtoMapper();

        this.projectUI = new ProjectUI(projectService, clientUI, clientDtoMapper, this, materialService, workforceService, estimateService, materialDtoMapper, workforceDtoMappers);

        EstimateService estimateService = new EstimateServiceImpl(estimateRepository, estimateDtoMapper);
        this.estimateUI = new EstimateUI(projectService, projectUI, this, estimateService);
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
            case 3 -> estimateUI.showMenu();
            case 4 -> {
                System.out.println("Good bye");
                System.exit(0);
            }
            default -> throw new IllegalArgumentException("Invalid choice");
        }
    }

}




