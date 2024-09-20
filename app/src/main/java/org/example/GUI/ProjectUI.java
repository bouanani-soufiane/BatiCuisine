package org.example.GUI;

import com.github.freva.asciitable.AsciiTable;
import com.github.freva.asciitable.Column;
import org.example.dtos.requests.ClientRequest;
import org.example.dtos.requests.ProjectRequest;
import org.example.dtos.responses.ClientResponse;
import org.example.entities.Client;
import org.example.entities.Project;
import org.example.enums.ProjectStatus;
import org.example.exceptions.ClientNotFoundException;
import org.example.mappers.dtomapper.ClientDtoMapper;
import org.example.mappers.dtomapper.EntityDtoMapper;
import org.example.services.interfaces.ClientService;
import org.example.services.interfaces.ProjectService;

import java.util.*;

import static com.github.freva.asciitable.HorizontalAlign.*;
import static com.github.freva.asciitable.HorizontalAlign.CENTER;
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

    public ProjectUI ( ProjectService service, ClientService clientService, ClientUI clientUI, ClientDtoMapper clientMapper, MainGUI mainGUI ) {
        this.service = service;
        this.clientService = clientService;
        this.clientUI = clientUI;
        this.clientMapper = clientMapper;
        this.mainGUI = mainGUI;
    }

    public void showMenu () {
        title("Welcome to Project menu \uD83D\uDCC4 ");
        System.out.println("1. Create new project");
        System.out.println("2. Show all projects");
        System.out.println("3. Update an existing project");

        Integer userChoice = scanInt("Please to enter you choice: ", POSITIVE_INT);

        switch (userChoice) {
            case 1 -> this.create();
            case 2 -> this.create();
            case 3 -> this.create();
            case 4 -> this.mainGUI.menu();
            default -> throw new IllegalArgumentException("Invalid choice");
        }
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
