package org.example.GUI;

import com.github.freva.asciitable.AsciiTable;
import com.github.freva.asciitable.Column;
import org.example.dtos.requests.ProjectRequest;
import org.example.dtos.responses.ClientResponse;
import org.example.entities.Client;
import org.example.entities.Project;
import org.example.enums.ProjectStatus;
import org.example.services.interfaces.ClientService;
import org.example.services.interfaces.ProjectService;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static com.github.freva.asciitable.HorizontalAlign.*;
import static com.github.freva.asciitable.HorizontalAlign.CENTER;
import static org.example.utils.Print.*;
import static org.example.utils.ValidatedInputReader.*;
import static org.example.utils.ValidationCriteria.*;

public class ProjectUI {

    final ProjectService service;
    final ClientService clientService;

    public ProjectUI ( ProjectService service, ClientService clientService ) {
        this.service = service;
        this.clientService = clientService;

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

            default -> throw new IllegalArgumentException("Invalid choice");
        }
    }

    private void create () {
        title("Create a new project");

        final String clientName = scanString("enter the client name : ", NOT_BLANK);

        try {
            this.showTable(List.of(this.clientService.findByName(clientName)));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        final String name = scanString("entre project name : ", NOT_BLANK);
        final double surface = scanDouble("entre the project surface : ", POSITIVE_DOUBLE);

        final ProjectStatus status = scanEnum("Select project status:", ProjectStatus.class, Objects::nonNull);
        final Double totalCost = scanDouble("entre the total cost : ", POSITIVE_DOUBLE);
        final Double profitMargin = scanDouble("entre the profit margin : ", POSITIVE_DOUBLE);
        final Double tva = scanDouble("entre the tva : ", POSITIVE_DOUBLE);

        Client client = new Client();
        client.setId(UUID.fromString("fadf08f1-8a6f-4122-a90f-06fc7cbc836a"));

        ProjectRequest projectRequest = new ProjectRequest(name, surface, status, totalCost, profitMargin, tva, client);

        Project project = this.service.create(projectRequest);

        System.out.println(project);

    }




    private void showTable( List<ClientResponse> clients) {

        System.out.println(AsciiTable.getTable(AsciiTable.BASIC_ASCII_NO_DATA_SEPARATORS, clients, Arrays.asList(
                new Column().header("ID").headerAlign(CENTER).with(client -> String.valueOf(client.id())),
                new Column().header("Name").headerAlign(CENTER).dataAlign(LEFT).with(ClientResponse::name),
                new Column().header("Phone").headerAlign(RIGHT).dataAlign(CENTER).with(ClientResponse::phone),
                new Column().header("Address").headerAlign(LEFT).dataAlign(LEFT).with(ClientResponse::address),
                new Column().header("Is Professional").headerAlign(CENTER).dataAlign(CENTER)
                        .with(client -> client.isProfessional() ? "Yes" : "No")
        )));


    }
}
