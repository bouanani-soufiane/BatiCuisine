package org.example.GUI;

import com.github.freva.asciitable.AsciiTable;
import com.github.freva.asciitable.Column;
import org.example.dtos.requests.ClientRequest;
import org.example.dtos.responses.ClientResponse;
import org.example.entities.Client;
import org.example.exceptions.ClientNotFoundException;
import org.example.mappers.dtomapper.EntityDtoMapper;
import org.example.services.interfaces.ClientService;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static com.github.freva.asciitable.HorizontalAlign.*;
import static org.example.utils.Print.*;
import static org.example.utils.ValidatedInputReader.*;
import static org.example.utils.ValidationCriteria.*;

public class ClientUI {

    private final ClientService service;
    private final EntityDtoMapper<Client, ClientRequest, ClientResponse> mapper;
    private final MainGUI mainGUI;

    public ClientUI ( ClientService service, EntityDtoMapper<Client, ClientRequest, ClientResponse> mapper, MainGUI mainGUI ) {
        this.service = service;
        this.mapper = mapper;
        this.mainGUI = mainGUI;

    }

    public void showMenu () {
        int userChoice;
        do {

            title("Welcome to client menu \uD83D\uDCC4 ");
            System.out.println("1. Create new client");
            System.out.println("2. Show all clients");
            System.out.println("3. Update an existing client");
            System.out.println("4. Show client by name");

            userChoice = scanInt("Please to enter you choice: ", POSITIVE_INT);

            switch (userChoice) {
                case 1 -> this.create();
                case 2 -> this.findAll();
                case 3 -> this.update();
                case 4 -> this.findByName();

                default -> throw new IllegalArgumentException("Invalid choice");
            }
        } while (userChoice != 0);
    }

    private Optional<List<ClientResponse>> findByName () {
        secondaryTitle("find client by name");
        final String name = scanString("please enter client name  : ", NOT_BLANK);
        try {
            Optional<List<ClientResponse>> clientResponse = service.findByName(name);
            showTable(clientResponse.get());
            return clientResponse;
        } catch (ClientNotFoundException e) {
            System.out.println("Client with name " + name + " not found");
            return null;
        }
    }

    private void update () {
        secondaryTitle("here is all client to update : ");
        final List<ClientResponse> clients = service.findAll();
        this.showTable(clients);
        final String clientId = scanString("pealse entre client id to update : ", NOT_BLANK);
        ClientResponse choosedClient = clients.stream().filter(c -> c.id().toString().equals(clientId)).findFirst().orElse(null);
        if (choosedClient != null) {
            final String name = scanString("please to enter the client first name ( " + choosedClient.name() + " ) : ", NOT_BLANK);
            final String phone = scanString("Please to enter the client phone (" + choosedClient.phone() + ") : ", VALID_PHONE);
            final String address = scanString("Please to enter the client address ( " + choosedClient.address() + ") : ", NOT_BLANK);
            final Boolean isProfessional = scanBoolean("is he professional (y/n): ");

            ClientRequest client = new ClientRequest(name, phone, address, isProfessional);

            try {
                Client updatedClient = service.update(UUID.fromString(clientId), client);
                showTable(List.of(mapper.mapToDto(updatedClient)));

            } catch (RuntimeException e) {
                System.out.println("Client with id " + clientId + " was not updated");
            }


        } else {
            System.out.println("no client found");
        }
    }

    public ClientResponse create () {
        secondaryTitle("Please enter all necessary information!");
        final String name = scanString("Please enter the client full name: ", NOT_BLANK);
        final String address = scanString("Please enter the client address: ", NOT_BLANK);
        final String phone = scanString("Please enter the client phone (06/07): ", VALID_PHONE);
        final Boolean isProfessional = scanBoolean("Is the client professional (y/n): ");

        ClientRequest clientRequest = new ClientRequest(name, address, phone, isProfessional);

        try {
            Client client = this.service.create(clientRequest);
            secondaryTitle("Client created successfully ✨ ");
            showTable(List.of(mapper.mapToDto(client)));
            return mapper.mapToDto(client);
        } catch (RuntimeException e) {
            System.out.println("Cannot create client: " + e.getMessage());
            return null;
        }

    }


    public void findAll () {
        final List<ClientResponse> clients = service.findAll();
        showTable(clients);
    }


    ClientResponse chooseOrCreateClient () {
        title("Search for Client");
        secondaryTitle("Would you like to search for an existing client or add a new one?");
        secondaryTitle("1. Add a new client");
        secondaryTitle("2. Search for client");
        secondaryTitle("3. Back");

        final int clientChoice = scanInt("Please enter client choice: ", POSITIVE_INT);
        ClientResponse choosedClient = null;

        switch (clientChoice) {
            case 1 -> choosedClient = this.create();
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
                                choosedClient = create();
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

    Optional<ClientResponse> chooseClientByName () {
        final String name = scanString("entre client name : ", NOT_BLANK);
        try {
            Optional<List<ClientResponse>> clients = this.service.findByName(name);
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

    public static void showTable ( List<ClientResponse> clients ) {

        System.out.println(AsciiTable.getTable(AsciiTable.BASIC_ASCII_NO_DATA_SEPARATORS, clients, Arrays.asList(new Column().header("#").headerAlign(CENTER).with(client -> Integer.toString(clients.indexOf(client) + 1)), new Column().header("ID").headerAlign(CENTER).with(client -> String.valueOf(client.id())), new Column().header("Name").headerAlign(CENTER).dataAlign(LEFT).with(ClientResponse::name), new Column().header("Phone").headerAlign(RIGHT).dataAlign(CENTER).with(ClientResponse::phone), new Column().header("Address").headerAlign(LEFT).dataAlign(LEFT).with(ClientResponse::address), new Column().header("Is Professional").headerAlign(CENTER).dataAlign(CENTER).with(client -> client.isProfessional() ? "Yes" : "No"))));


    }


}
