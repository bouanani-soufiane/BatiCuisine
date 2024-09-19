package org.example.GUI;

import com.github.freva.asciitable.AsciiTable;
import com.github.freva.asciitable.Column;
import org.example.dtos.requests.ClientRequest;
import org.example.dtos.responses.ClientResponse;
import org.example.entities.Client;
import org.example.mappers.dtomapper.ClientDtoMapper;
import org.example.services.interfaces.ClientService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.github.freva.asciitable.HorizontalAlign.*;
import static org.example.utils.Print.*;
import static org.example.utils.ValidatedInputReader.*;
import static org.example.utils.ValidationCriteria.*;

public class ClientUI {

    ClientService service;
    ClientDtoMapper mapper ;
    public ClientUI( ClientService service , ClientDtoMapper mapper ){
        this.service = service;
        this.mapper = mapper;
    }

    public void showMenu() {
        title("Welcome to client menu \uD83D\uDCC4 ");
        System.out.println("1. Create new client");
        System.out.println("2. Update an existing client");
        System.out.println("3. Delete an existing client");
        System.out.println("4. Show all clients");
        System.out.println("5. Show client by id");

        Integer userChoice = scanInt("Please to enter you choice: ", POSITIVE_INT);

        switch (userChoice) {
            case 1 -> this.create();
            case 4 -> this.findAll();

            default -> throw new IllegalArgumentException("Invalid choice");
        }
    }
    public void create() {
        secondaryTitle("Please enter all necessary information!");
        final String name = scanString("Please enter the client full name: ", NOT_BLANK);
        final String address = scanString("Please enter the client address: ", NOT_BLANK);
        final String phone = scanString("Please enter the client phone (06/07): ", VALID_PHONE);
        final Boolean isProfessional = scanBoolean("Is the client professional (y/n): ");

        ClientRequest clientRequest = new ClientRequest(name, address, phone, isProfessional);

        try {
            Client client = this.service.create(clientRequest);
            secondaryTitle("Client created successfully ✨ ");
            this.showTable(Collections.singletonList(mapper.mapToDto(client)));
        } catch (Exception e) {
            System.out.println("Cannot create client: " + e.getMessage());
        }
    }

    public void findAll(){
        final List<ClientResponse> clients = service.findAll();
        this.showTable(clients);
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
