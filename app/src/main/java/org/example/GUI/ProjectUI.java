package org.example.GUI;

import org.example.dtos.requests.EstimateRequest;
import org.example.dtos.requests.MaterialRequest;
import org.example.dtos.requests.ProjectRequest;
import org.example.dtos.requests.WorkforceRequest;
import org.example.dtos.responses.ClientResponse;
import org.example.entities.Project;
import org.example.enums.ProjectStatus;
import org.example.exceptions.ProjectNotFound;
import org.example.mappers.dtomapper.ClientDtoMapper;
import org.example.mappers.dtomapper.MaterialDtoMapper;
import org.example.mappers.dtomapper.WorkforceDtoMapper;
import org.example.services.interfaces.EstimateService;
import org.example.services.interfaces.MaterialService;
import org.example.services.interfaces.ProjectService;
import org.example.services.interfaces.WorkforceService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.example.GUI.ClientUI.showClientTable;
import static org.example.GUI.MaterialUI.showMaterialTable;
import static org.example.GUI.WorkforceUI.showWorkforceTable;
import static org.example.utils.Print.title;
import static org.example.utils.ValidatedInputReader.*;
import static org.example.utils.ValidationCriteria.*;

public class ProjectUI {

    private final ProjectService service;
    private final ClientUI clientUI;
    private final ClientDtoMapper clientMapper;
    private final MainGUI mainGUI;
    private final MaterialService materialService;
    private final WorkforceService workforceService;
    private final EstimateService estimateService;
    private final MaterialDtoMapper materialDtoMapper;
    private final WorkforceDtoMapper workforceDtoMapper;

    public ProjectUI ( ProjectService service, ClientUI clientUI, ClientDtoMapper clientMapper, MainGUI mainGUI, MaterialService materialService, WorkforceService workforceService, EstimateService estimateService, MaterialDtoMapper materialDtoMapper, WorkforceDtoMapper workforceDtoMapper ) {
        this.service = service;
        this.clientUI = clientUI;
        this.clientMapper = clientMapper;
        this.mainGUI = mainGUI;
        this.materialService = materialService;
        this.workforceService = workforceService;
        this.estimateService = estimateService;
        this.materialDtoMapper = materialDtoMapper;
        this.workforceDtoMapper = workforceDtoMapper;
    }

    public void showMenu() {
        int userChoice;
        do {
            title("Welcome to Project menu \uD83D\uDCC4 ");
            displayMenuOptions();

            userChoice = scanInt("Please enter your choice: ", POSITIVE_INT);
            handleMenuChoice(userChoice);
        } while (userChoice != 0);
    }

    private void displayMenuOptions() {
        System.out.println("1. Create new project");
        System.out.println("2. Show all projects");
        System.out.println("3. Return to main menu");
    }

    private void handleMenuChoice(int choice) {
        switch (choice) {
            case 1 -> create();
            case 3 -> mainGUI.menu();
            default -> System.err.println("Invalid choice, please try again.");
        }
    }


    private void create () {
        ClientResponse choosedClient = clientUI.chooseOrCreateClient();
        createProject(choosedClient);
    }


    private void createProject ( ClientResponse chosenClient ) {
        if (chosenClient == null) {
            System.out.println("No client selected. Aborting project creation.");
            return;
        }

        ProjectRequest projectRequest = projectDetails(chosenClient);
        Project project = this.service.create(projectRequest);

        List<MaterialRequest> materialRequests = createMaterials(project);
        List<WorkforceRequest> workforceRequests = createWorkforce(project);

        createMaterials(materialRequests);
        createWorkforces(workforceRequests);

        setTotalCost(project, materialRequests, workforceRequests);
        saveProject(project);
        printProjectDetails(this.service.findById(project.id()));
        displayCalculationResults(project);
        saveEstimation(project);
    }

    private ProjectRequest projectDetails ( ClientResponse client ) {
        String name = scanString("Enter project name: ", NOT_BLANK);
        double surface = scanDouble("Enter the project surface: ", POSITIVE_DOUBLE);
        return new ProjectRequest(name, surface, ProjectStatus.PENDING, clientMapper.mapToEntity(client));
    }

    private List<MaterialRequest> createMaterials ( Project project ) {
        MaterialUI materialUI = new MaterialUI();
        return materialUI.display(project);
    }

    private List<WorkforceRequest> createWorkforce ( Project project ) {
        WorkforceUI workforceUI = new WorkforceUI();
        return workforceUI.displayWorkforce(project);
    }

    private void createMaterials ( List<MaterialRequest> materialRequests ) {
        try {
            this.materialService.create(materialRequests);
        } catch (RuntimeException e) {
            System.err.println("Error creating materials: " + e.getMessage());
        }
    }

    private void createWorkforces ( List<WorkforceRequest> workforceRequests ) {
        try {
            this.workforceService.create(workforceRequests);
        } catch (RuntimeException e) {
            System.err.println("Error creating workforces: " + e.getMessage());
        }
    }

    private void setTotalCost ( Project project, List<MaterialRequest> materialRequests, List<WorkforceRequest> workforceRequests ) {
        title(" ---- Total cost calculation ---- ");
        if (scanBoolean("Do you wish to apply a profit margin to the project? (yes/no): ")) {
            double profitMargin = scanDouble("Enter profit margin percentage %: ", POSITIVE_DOUBLE);
            project.setProfitMargin(profitMargin);
        }

        double totalCost = project.profitMargin() == null
                ? estimateService.calcTotalCost(materialRequests, workforceRequests)
                : estimateService.calcTotalCost(materialRequests, workforceRequests, project.profitMargin());
        project.setTotalCost(totalCost);
    }

    private void saveProject ( Project project ) {
        try {
            this.service.update(project.id(), project);
            System.out.println("Project saved successfully!");
        } catch (RuntimeException e) {
            System.err.println("An error occurred while saving the project: " + e.getMessage());
        }
    }

    private void displayCalculationResults ( Project project ) {
        try {
            this.dispalyCalc(this.service.findById(project.id()));
        } catch (ProjectNotFound e) {
            System.err.println(e.getMessage());
        } catch (RuntimeException e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    private void saveEstimation ( Project project ) {
        System.out.println("---  Saving Estimation  ---");


        if (scanBoolean("Do you want to store the estimate? (yes/no): ")) {
            try {
                System.out.println("Estimation start date: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                int validityDays = scanInt("Please enter the number of days for which this estimation will be valid (e.g., 30): ", POSITIVE_INT);
                this.estimateService.create(new EstimateRequest(LocalDateTime.now(), LocalDateTime.now().plusDays(validityDays), false, project.totalCost(), project));
                System.out.println("Estimate registered successfully!");
            } catch (RuntimeException e) {
                System.out.println("Estimation is not registered: " + e.getMessage());
            }
        }
    }


    private void printProjectDetails ( Project project ) {

        System.out.println("project with name (" + project.name() + ") and surface " + project.surface());
        System.out.println("Client details: ");
        showClientTable(List.of(clientMapper.mapToDto(project.client())));
        System.out.println("workforces list : ");
        showWorkforceTable(project.workforces());
        System.out.println("materials list : ");
        showMaterialTable(project.materials());
    }


    private void dispalyCalc ( Project project ) {

        System.out.println("---  Calculation Results ---");
        System.out.println("Project Name: " + project.name());
        System.out.println("Client: " + project.client().name());
        System.out.println("Project Address: " + project.client().address());
        System.out.println("Surface Area: " + project.surface() + " m²");

        System.out.println("\n--- Material Costs ---");

        project.materials().stream().forEach(( material ) -> System.out.printf("%s : %.2f € (quantity: %.1f m², unit cost: %.2f €/m², quality: %.1f, transport: %.2f €)%n", material.name(), estimateService.calcCostMaterialWithTva(materialDtoMapper.mapToEntity(material)), material.quantity(), material.unitPrice(), material.coefficient(), material.transportCost())

        );

        System.out.printf("Total Material Cost Before VAT: %.2f €%n", estimateService.calcCostMaterials(project.materials().stream().map(materialDtoMapper::mapToEntity).toList()));
        System.out.printf("Total Material Cost With VAT : %.2f €%n", estimateService.calcCostMaterialsWithTva(project.materials().stream().map(materialDtoMapper::mapToEntity).toList()));

        System.out.println("\n--- Labor Costs ---");

        project.workforces().stream().forEach(( workforce ) -> {
            System.out.printf("%s: %.2f € (hourly rate: %.2f €/h, hours worked: %.1f h, productivity: %.1f)%n", workforce.name(), estimateService.calcCostWorkforceWithTva(workforceDtoMapper.mapToEntity(workforce)), workforce.pricePerHour(), workforce.workingHours(), workforce.productivityFactor());
        });

        System.out.printf("Total Labor Cost Before VAT: %.2f €%n", estimateService.calcCostWorkforces(project.workforces().stream().map(workforceDtoMapper::mapToEntity).toList()));
        System.out.printf("Total Labor Cost With VAT : %.2f €%n", estimateService.calcCostWorkforcesWithTva(project.workforces().stream().map(workforceDtoMapper::mapToEntity).toList()));

        List<MaterialRequest> materials = project.materials().stream().map(materialDtoMapper::mapToEntity).toList();
        List<WorkforceRequest> workforces = project.workforces().stream().map(workforceDtoMapper::mapToEntity).toList();
        System.out.printf("\nTotal Cost Before Margin: %.2f €%n", estimateService.calcTotalCost(materials, workforces));
        if (project.profitMargin() != null) {
            System.out.printf("Profit Margin (%.0f%%): %.2f €%n", project.profitMargin(), estimateService.calcProfitMargin(materials, workforces, project.profitMargin()));
            System.out.printf("Total Final Cost of the Project: %.2f €%n", estimateService.calcTotalCost(materials, workforces, project.profitMargin()));
        } else {
            System.out.printf("Profit Margin (%.0f%%): %.2f €%n", project.profitMargin(), project.profitMargin(), 0.0);
            System.out.printf("Total Final Cost of the Project: %.2f €%n", estimateService.calcTotalCost(materials, workforces));
        }

    }


}
