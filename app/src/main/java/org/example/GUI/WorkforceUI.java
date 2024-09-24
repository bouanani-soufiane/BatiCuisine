package org.example.GUI;

import com.github.freva.asciitable.AsciiTable;
import com.github.freva.asciitable.Column;
import org.example.dtos.requests.WorkforceRequest;
import org.example.entities.Component;
import org.example.entities.Project;
import org.example.entities.Workforce;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.github.freva.asciitable.HorizontalAlign.CENTER;
import static com.github.freva.asciitable.HorizontalAlign.LEFT;
import static org.example.utils.Print.title;
import static org.example.utils.ValidatedInputReader.*;
import static org.example.utils.ValidationCriteria.NOT_BLANK;
import static org.example.utils.ValidationCriteria.POSITIVE_DOUBLE;

public class WorkforceUI {

    public List<WorkforceRequest> displayWorkforce ( Project project ) {

        List<WorkforceRequest> workforceRequests = new ArrayList<>();

        do {

            title("Add New Workforce");

            final String workforceName = scanString("enter workforce name : ", NOT_BLANK);
            final Double workforceTva = scanDouble("enter the value of tva : ", POSITIVE_DOUBLE);
            final Double price_per_hour = scanDouble("Enter the price per hour : ", POSITIVE_DOUBLE);
            final Double working_hours = scanDouble("Enter the working hours : ", POSITIVE_DOUBLE);
            final Double productivity_factor = scanDouble("Enter the productivity factor : ", POSITIVE_DOUBLE);

            workforceRequests.add(new WorkforceRequest(workforceName, workforceTva, price_per_hour, working_hours, productivity_factor, project));

        } while (scanBoolean("Do you want to add another workforce? (yes/no): "));


        return workforceRequests;
    }


    public static void showWorkforceTable ( List<Workforce> workforces ) {
        System.out.println(AsciiTable.getTable(AsciiTable.BASIC_ASCII_NO_DATA_SEPARATORS, workforces, Arrays.asList(new Column().header("#").headerAlign(CENTER).with(workforce -> Integer.toString(workforces.indexOf(workforce) + 1)), new Column().header("Name").headerAlign(CENTER).dataAlign(LEFT).with(Component::name), new Column().header("TVA").headerAlign(CENTER).dataAlign(CENTER).with(workforce -> String.valueOf(workforce.tva())), new Column().header("Price per Hour").headerAlign(CENTER).dataAlign(CENTER).with(workforce -> String.valueOf(workforce.pricePerHour())), new Column().header("Working Hours").headerAlign(CENTER).dataAlign(CENTER).with(workforce -> String.valueOf(workforce.workingHours())), new Column().header("Productivity Factor").headerAlign(CENTER).dataAlign(CENTER).with(workforce -> String.valueOf(workforce.productivityFactor())))));
    }



}
