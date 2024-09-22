package org.example.GUI;

import static org.example.utils.Print.title;
import static org.example.utils.ValidatedInputReader.*;
import static org.example.utils.ValidationCriteria.*;

public class MaterialUI {

    public MaterialUI() {

    }

    public void display(){
        title("Add New Material");
        final String name = scanString("enter material name : " , NOT_BLANK);
        final Double quantity = scanDouble("enter the quantity : ",POSITIVE_DOUBLE);
        final Double unitPrice = scanDouble("enter the unitPrice : ",POSITIVE_DOUBLE);
        final Double tva = scanDouble("enter the value of tva : ",POSITIVE_DOUBLE);
        final Double transportCost = scanDouble("enter the transportCost : ",POSITIVE_DOUBLE);
        final Double coefficient = scanDouble("enter the coefficient : ",POSITIVE_DOUBLE);

    }
}
