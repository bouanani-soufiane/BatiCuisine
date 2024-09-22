package org.example.GUI;

import org.example.dtos.requests.WorkforceRequest;
import org.example.entities.Project;

import static org.example.utils.Print.title;
import static org.example.utils.ValidatedInputReader.scanDouble;
import static org.example.utils.ValidatedInputReader.scanString;
import static org.example.utils.ValidationCriteria.NOT_BLANK;
import static org.example.utils.ValidationCriteria.POSITIVE_DOUBLE;

public class WorkforceUI {

    public WorkforceRequest displayWorkforce( Project project) {
        title("Add New Workforce");

        final String workforceName = scanString("enter workforce name : " , NOT_BLANK);
        final Double workforceTva = scanDouble("enter the value of tva : ",POSITIVE_DOUBLE);
        final Double price_per_hour  = scanDouble("Enter the price per hour : ", POSITIVE_DOUBLE);
        final Double working_hours = scanDouble("Enter the working hours : ", POSITIVE_DOUBLE);
        final Double productivity_factor = scanDouble("Enter the productivity factor : ", POSITIVE_DOUBLE);

        return new WorkforceRequest(workforceName , workforceTva , price_per_hour,working_hours,productivity_factor,project);

    }
}
