package org.example.GUI;

import org.example.dtos.requests.MaterialRequest;
import org.example.dtos.requests.ProjectRequest;
import org.example.dtos.requests.WorkforceRequest;
import org.example.dtos.responses.ClientResponse;
import org.example.entities.Material;
import org.example.entities.Project;
import org.example.enums.ProjectStatus;
import org.example.exceptions.ClientNotFoundException;
import org.example.mappers.dtomapper.ClientDtoMapper;
import org.example.services.interfaces.ClientService;
import org.example.services.interfaces.MaterialService;
import org.example.services.interfaces.ProjectService;
import org.example.services.interfaces.WorkforceService;

import java.util.*;
import static org.example.GUI.ClientUI.showTable;
import static org.example.utils.Print.*;
import static org.example.utils.ValidatedInputReader.*;
import static org.example.utils.ValidationCriteria.*;

public class ProjectUI {

    private final ProjectService service;
    private final ClientService clientService;
    private final ClientUI clientUI;
    private final ClientDtoMapper clientMapper;
    private final MainGUI mainGUI;
    private final MaterialService materialService;
    private final WorkforceService workforceService;

    public ProjectUI ( ProjectService service, ClientService clientService, ClientUI clientUI, ClientDtoMapper clientMapper, MainGUI mainGUI, MaterialService materialService, WorkforceService workforceService ) {
        this.service = service;
        this.clientService = clientService;
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
        ClientResponse choosedClient = chooseOrCreateClient();
        createProject(choosedClient);
    }


    private ClientResponse chooseOrCreateClient () {
        title("Search for Client");
        secondaryTitle("Would you like to search for an existing client or add a new one?");
        secondaryTitle("1. Add a new client");
        secondaryTitle("2. Search for client");
        secondaryTitle("3. Back");

        final int clientChoice = scanInt("Please enter client choice: ", POSITIVE_INT);
        ClientResponse choosedClient = null;

        switch (clientChoice) {
            case 1 -> choosedClient = clientUI.create();
            case 2 -> {
                boolean searching = true;
                while (searching) {
                    Optional<ClientResponse> optionalClient = chooseClientByName();
                    if (optionalClient.isPresent()) {
                        choosedClient = optionalClient.get();
                        searching = false;
                    } else {
                        Integer decision = scanInt("Do you want to (1) create a new client or (2) keep searching? (Enter 1 or 2): ", POSITIVE_INT);
                        switch (decision) {
                            case 1 -> {
                                choosedClient = clientUI.create();
                                searching = false;
                            }
                            case 2 -> System.out.println("Let's search again.");
                            default -> System.out.println("Invalid input. Please try again.");
                        }
                    }
                }
            }
            case 3 -> {
                mainGUI.menu();
                return null;
            }
            default -> {
                System.out.println("Invalid choice. Please select a valid option.");
                return chooseOrCreateClient();
            }
        }
        return choosedClient;
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

        title("Add New Workforce");

        final String workforceName = scanString("enter workforce name : " , NOT_BLANK);
        final Double workforceTva = scanDouble("enter the value of tva : ",POSITIVE_DOUBLE);
        final Double price_per_hour  = scanDouble("Enter the price per hour : ", POSITIVE_DOUBLE);
        final Double working_hours = scanDouble("Enter the working hours : ", POSITIVE_DOUBLE);
        final Double productivity_factor = scanDouble("Enter the productivity factor : ", POSITIVE_DOUBLE);

        WorkforceRequest workforceRequest = new WorkforceRequest(workforceName , workforceTva , price_per_hour,working_hours,productivity_factor,project);

        this.workforceService.create(workforceRequest);
//        System.out.println("here : "+workforceRequest);



//        title("Add New Material");
//
//        final String MaterialName = scanString("enter material name : " , NOT_BLANK);
//        final Double quantity = scanDouble("enter the quantity : ",POSITIVE_DOUBLE);
//        final Double unitPrice = scanDouble("enter the unitPrice : ",POSITIVE_DOUBLE);
//        final Double MaterialTva = scanDouble("enter the value of tva : ",POSITIVE_DOUBLE);
//        final Double transportCost = scanDouble("enter the transportCost : ",POSITIVE_DOUBLE);
//        final Double coefficient = scanDouble("enter the coefficient : ",POSITIVE_DOUBLE);

//        MaterialRequest materialRequest = new MaterialRequest(MaterialName , MaterialTva , quantity, unitPrice, transportCost,coefficient , project);

//        Material material = this.materialService.create(materialRequest);

//        System.out.println(material);
        System.out.println(project);
    }


    Optional<ClientResponse> chooseClientByName () {
        final String name = scanString("entre client name : ", NOT_BLANK);
        try {
            Optional<List<ClientResponse>> clients = this.clientService.findByName(name);
            if (clients.isPresent()) {
                System.out.println("here's the  list of client with (" + name.toUpperCase() + ") as client name : ");
                showTable(clients.get());
                final int selectedClientChoice = scanInt("Please to enter client choice:  ", combine(POSITIVE_INT, ( n ) -> n <= clients.get().size()));
                return clients.get().stream().skip(selectedClientChoice - 1).findFirst();
            } else {
                return Optional.empty();
            }
        } catch (ClientNotFoundException e) {
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }
}
