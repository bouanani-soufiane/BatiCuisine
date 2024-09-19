package org.example.GUI;

import org.example.mappers.dtomapper.ClientDtoMapper;
import org.example.mappers.rowmapper.ClientRowMapper;
import org.example.reposiroties.impl.ClientRepositoryImpl;
import org.example.reposiroties.interfaces.ClientRepository;
import org.example.services.impl.ClientServiceImpl;
import org.example.services.interfaces.ClientService;

import java.util.Scanner;

import static org.example.utils.Print.title;
import static org.example.utils.ValidatedInputReader.scanInt;
import static org.example.utils.ValidationCriteria.*;

public class MainGUI {

    private final ClientUI clientUI;
    public MainGUI ( Scanner scanner ) {
        ClientRowMapper clientRowMapper = new ClientRowMapper();
        ClientDtoMapper clientDtoMapper = new ClientDtoMapper();
        ClientRepositoryImpl clientRepository = new ClientRepositoryImpl(clientRowMapper);
        ClientService clientService = new ClientServiceImpl(clientRepository , clientDtoMapper);
        this.clientUI = new ClientUI(clientService ,clientDtoMapper);
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
            case 2 -> System.out.println("project management");
            case 3 -> System.out.println("Quotation management");
            case 4 -> { System.out.println("Good bye") ;System.exit(0);}
            default -> throw new IllegalArgumentException("Invalid choice");
        }
    }

}




