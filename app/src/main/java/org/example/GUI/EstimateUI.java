package org.example.GUI;

import com.github.freva.asciitable.AsciiTable;
import com.github.freva.asciitable.Column;
import org.example.entities.Estimate;
import org.example.entities.Project;
import org.example.services.interfaces.EstimateService;
import org.example.services.interfaces.ProjectService;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import static com.github.freva.asciitable.HorizontalAlign.CENTER;
import static com.github.freva.asciitable.HorizontalAlign.LEFT;
import static org.example.utils.ValidatedInputReader.scanInt;
import static org.example.utils.ValidatedInputReader.scanString;
import static org.example.utils.ValidationCriteria.NOT_BLANK;
import static org.example.utils.ValidationCriteria.POSITIVE_INT;


public class EstimateUI {

    private final ProjectService projectService;
    private final ProjectUI projectUI;
    private final MainGUI mainGUI;
    private final EstimateService estimateService;

    public EstimateUI ( ProjectService projectService, ProjectUI projectUI, MainGUI mainGUI, EstimateService estimateService ) {
        this.projectService = projectService;
        this.projectUI = projectUI;
        this.mainGUI = mainGUI;
        this.estimateService = estimateService;
    }

    public void showMenu () {
        int userChoice;
        do {
            System.out.println("Welcome to the Estimate Menu");
            displayMenuOptions();

            userChoice = scanInt("Please enter your choice: ", POSITIVE_INT);
            handleMenuChoice(userChoice);
        } while (userChoice != 0);
    }

    private void displayMenuOptions () {
        System.out.println("1. Find estimate by project ID");
        System.out.println("2. Find estimate by ID");
        System.out.println("3. Find all estimates");
        System.out.println("4. Go back");
    }

    private void handleMenuChoice ( int choice ) {
        switch (choice) {
            case 1 -> findEstimateByProjectId();
            case 2 -> findEstimateById();
            case 3 -> findAllEstimates();
            case 4 -> mainGUI.menu();
            default -> System.out.println("Invalid choice, please try again.");
        }
    }

    public void findEstimateByProjectId () {
        this.projectUI.showAllProjects();
        final String id = scanString("Enter ID of the project to see its estimation: ", NOT_BLANK);

        try {
            Project project = this.projectService.findById(UUID.fromString(id));
            if (project != null) {
                projectUI.dispalyCalc(project);
            } else {
                System.out.println("Project not found.");
            }
        } catch (Exception e) {
            System.err.println("Error retrieving project: " + e.getMessage());
        }
    }

    public void findEstimateById () {
        String id = scanString("Enter Estimate ID: ", NOT_BLANK);

        try {
            UUID estimateId = UUID.fromString(id);
            Estimate estimate = estimateService.getEstimateById(estimateId);
            showEstimateTable(List.of(estimate));
        } catch (Exception e) {
            System.err.println("Error retrieving estimate: " + e.getMessage());
        }
    }

    public void findAllEstimates () {
        List<Estimate> estimates = estimateService.getAllEstimates();
        if (estimates.isEmpty()) {
            System.out.println("No estimates found.");
        } else {
            showEstimateTable(estimates);
        }
    }

    private void showEstimateTable ( List<Estimate> estimates ) {
        System.out.println(AsciiTable.getTable(AsciiTable.BASIC_ASCII_NO_DATA_SEPARATORS, estimates, List.of(new Column().header("#").headerAlign(CENTER).with(estimate -> Integer.toString(estimates.indexOf(estimate) + 1)), new Column().header("Estimate ID").headerAlign(CENTER).dataAlign(LEFT).with(e -> String.valueOf(e.id())), new Column().header("Issued Date").headerAlign(CENTER).dataAlign(LEFT).with(e -> e.issuedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))), new Column().header("valid_until_date").headerAlign(CENTER).dataAlign(LEFT).with(e -> e.validUntilDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))), new Column().header("is_accepted").headerAlign(CENTER).dataAlign(LEFT).with(e -> String.valueOf(e.isAccepted())), new Column().header("Total Cost").headerAlign(CENTER).dataAlign(CENTER).with(e -> String.valueOf(e.estimateAmount())), new Column().header("project_name").headerAlign(CENTER).dataAlign(LEFT).with(e -> String.valueOf(e.project().name())), new Column().header("surface").headerAlign(CENTER).dataAlign(LEFT).with(e -> String.valueOf(e.project().surface())), new Column().header("status").headerAlign(CENTER).dataAlign(LEFT).with(e -> String.valueOf(e.project().projectStatus())), new Column().header("profit_margin").headerAlign(CENTER).dataAlign(LEFT).with(e -> e.project().profitMargin() + " $"))));
    }


}
