package org.example.GUI;

import org.example.dtos.requests.ProjectRequest;
import org.example.dtos.responses.ProjectResponse;
import org.example.entities.Project;
import org.example.mappers.dtomapper.ClientDtoMapper;
import org.example.mappers.dtomapper.EntityDtoMapper;
import org.example.mappers.dtomapper.ProjectDtoMapper;
import org.example.mappers.rowmapper.ClientRowMapper;
import org.example.mappers.rowmapper.EntityRowMapper;
import org.example.mappers.rowmapper.ProjectRowMapper;
import org.example.reposiroties.impl.ClientRepositoryImpl;
import org.example.reposiroties.impl.ProjectRepositoryImpl;
import org.example.reposiroties.interfaces.ClientRepository;
import org.example.reposiroties.interfaces.ProjectRepository;
import org.example.services.impl.ClientServiceImpl;
import org.example.services.impl.ProjectServiceImpl;
import org.example.services.interfaces.ClientService;
import org.example.services.interfaces.ProjectService;

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
        ClientService clientService = new ClientServiceImpl(clientRepository , clientDtoMapper);
        EntityRowMapper<Project> projectRowMapper = new ProjectRowMapper(clientRowMapper);
        ProjectRepository projectRepository = new ProjectRepositoryImpl(projectRowMapper);
        EntityDtoMapper<Project, ProjectRequest, ProjectResponse> projectDtoMapper = new ProjectDtoMapper(clientDtoMapper);
        ProjectService projectService = new ProjectServiceImpl(projectRepository,projectDtoMapper );
        this.clientUI = new ClientUI(clientService ,clientDtoMapper);

        this.projectUI = new ProjectUI(projectService, clientService);
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
            case 4 -> { System.out.println("Good bye") ;System.exit(0);}
            default -> throw new IllegalArgumentException("Invalid choice");
        }
    }

}




