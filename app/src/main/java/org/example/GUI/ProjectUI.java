package org.example.GUI;

import org.example.dtos.requests.MaterialRequest;
import org.example.dtos.requests.ProjectRequest;
import org.example.dtos.requests.WorkforceRequest;
import org.example.dtos.responses.ClientResponse;
import org.example.entities.Project;
import org.example.enums.ProjectStatus;
import org.example.mappers.dtomapper.ClientDtoMapper;
import org.example.services.interfaces.MaterialService;
import org.example.services.interfaces.ProjectService;
import org.example.services.interfaces.WorkforceService;
import java.util.List;
import static org.example.GUI.ClientUI.showClientTable;
import static org.example.GUI.MaterialUI.showMaterialTable;
import static org.example.GUI.WorkforceUI.showWorkforceTable;
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

        ProjectRequest projectRequest = new ProjectRequest(name, surface, ProjectStatus.PENDING, clientMapper.mapToEntity(choosedClient));
        Project project = this.service.create(projectRequest);

        // Material need to do inject it latter
        MaterialUI materialUI = new MaterialUI();
        List<MaterialRequest> materialRequests = materialUI.display(project);

        // Workforce need to do inject it latter
        WorkforceUI workforceUI = new WorkforceUI();
        List<WorkforceRequest> workforceRequests = workforceUI.displayWorkforce(project);

        title(" ---- Total cost calculation ---- ");

        if (scanBoolean("Do you wish to apply a profit margin to the project ? (yes/no): ")) {
            final double profitMargin = scanDouble("Enter profit margin percentage %: ", POSITIVE_DOUBLE);
        }
        project.setProfitMargin(12.2);
        project.setTotalCost(12.2);

        System.out.println(this.service.update(project.id() , project));
        materialRequests.forEach(this.materialService::create);
        workforceRequests.forEach(this.workforceService::create);
        printProjectDetails(this.service.findById(project.id()));

    }


    public void printProjectDetails ( Project project ) {

        System.out.println("project with name" + project.name() + "and surface " + project.surface());
        System.out.println("created successfully");
        showClientTable(List.of(clientMapper.mapToDto(project.client())));
        System.out.println("workforce list : ");
        showWorkforceTable(project.workforces());
        System.out.println("materials list : ");
        showMaterialTable(project.materials());
    }


}
