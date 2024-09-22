package org.example.GUI;

import org.example.dtos.requests.MaterialRequest;
import org.example.dtos.requests.ProjectRequest;
import org.example.dtos.requests.WorkforceRequest;
import org.example.dtos.responses.ClientResponse;
import org.example.entities.Project;
import org.example.enums.ProjectStatus;
import org.example.mappers.dtomapper.ClientDtoMapper;
import org.example.services.interfaces.ClientService;
import org.example.services.interfaces.MaterialService;
import org.example.services.interfaces.ProjectService;
import org.example.services.interfaces.WorkforceService;

import static org.example.utils.Print.*;
import static org.example.utils.ValidatedInputReader.*;
import static org.example.utils.ValidationCriteria.*;

public class ProjectUI {

    private final ProjectService service;
    private final ClientUI clientUI;
    private final ClientDtoMapper clientMapper;
    private final MainGUI mainGUI;
    private final MaterialService materialService;
    private final WorkforceService workforceService;

    public ProjectUI ( ProjectService service, ClientUI clientUI, ClientDtoMapper clientMapper, MainGUI mainGUI, MaterialService materialService, WorkforceService workforceService ) {
        this.service = service;
        this.clientUI = clientUI;
        this.clientMapper = clientMapper;
        this.mainGUI = mainGUI;
        this.materialService = materialService;
        this.workforceService = workforceService;
    }

    public void showMenu () {
        int userChoice;
        do {

            title("Welcome to Project menu \uD83D\uDCC4 ");
            System.out.println("1. Create new project");
            System.out.println("2. Show all projects");
            System.out.println("3. Update an existing project");

            userChoice = scanInt("Please to enter you choice: ", POSITIVE_INT);

            switch (userChoice) {
                case 1 -> this.create();
                case 2 -> this.create();
                case 3 -> this.create();
                case 4 -> this.mainGUI.menu();
                default -> throw new IllegalArgumentException("Invalid choice");
            }
        } while (userChoice != 0);
    }


    private void create () {
        ClientResponse choosedClient = clientUI.chooseOrCreateClient();
        createProject(choosedClient);
    }


    private void createProject ( ClientResponse choosedClient ) {
        if (choosedClient == null) {
            System.out.println("No client selected. Aborting project creation.");
            return;
        }

        final String name = scanString("Enter project name: ", NOT_BLANK);
        final double surface = scanDouble("Enter the project surface: ", POSITIVE_DOUBLE);
        final ProjectStatus status = ProjectStatus.PENDING;
        final double totalCost = scanDouble("Enter the total cost: ", POSITIVE_DOUBLE);
        final double profitMargin = scanDouble("Enter the profit margin: ", POSITIVE_DOUBLE);
        final double tva = scanDouble("Enter the TVA: ", POSITIVE_DOUBLE);

        ProjectRequest projectRequest = new ProjectRequest(name, surface, status, totalCost, profitMargin, tva, clientMapper.mapToEntity(choosedClient));
        Project project = this.service.create(projectRequest);

        // Material
        MaterialUI materialUI = new MaterialUI();
        MaterialRequest materialRequest = materialUI.display(project);
        this.materialService.create(materialRequest);

        // Workforce
        WorkforceUI workforceUI = new WorkforceUI();
        WorkforceRequest workforceRequest = workforceUI.displayWorkforce(project);
        this.workforceService.create(workforceRequest);


        System.out.println("here" + this.service.findById(project.id()));


    }


}
