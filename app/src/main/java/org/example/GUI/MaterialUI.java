package org.example.GUI;

import com.github.freva.asciitable.AsciiTable;
import com.github.freva.asciitable.Column;
import org.example.dtos.requests.MaterialRequest;
import org.example.entities.Material;
import org.example.entities.Project;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.github.freva.asciitable.HorizontalAlign.CENTER;
import static com.github.freva.asciitable.HorizontalAlign.LEFT;
import static org.example.utils.Print.title;
import static org.example.utils.ValidatedInputReader.*;
import static org.example.utils.ValidationCriteria.*;

public class MaterialUI {

    public List<MaterialRequest> display ( Project project ) {
        List<MaterialRequest> materials = new ArrayList<>();

        title("Add New Material");

        boolean addingMore = true;
        while (addingMore) {
            final String materialName = scanString("Enter material name: ", NOT_BLANK);
            final Double quantity = scanDouble("Enter the quantity: ", POSITIVE_DOUBLE);
            final Double unitPrice = scanDouble("Enter the unit price: ", POSITIVE_DOUBLE);
            final Double materialTva = scanDouble("Enter the value of TVA: ", POSITIVE_DOUBLE);
            final Double transportCost = scanDouble("Enter the transport cost: ", POSITIVE_DOUBLE);
            final Double coefficient = scanDouble("Enter the coefficient: ", POSITIVE_DOUBLE);

            materials.add(new MaterialRequest(materialName, materialTva, quantity, unitPrice, transportCost, coefficient, project));

            addingMore = scanBoolean("Do you want to add another material? (yes/no): ");
        }

        return materials;
    }

    public static void showMaterialTable ( List<Material> materials ) {
        System.out.println(AsciiTable.getTable(AsciiTable.BASIC_ASCII_NO_DATA_SEPARATORS, materials, Arrays.asList(new Column().header("#").headerAlign(CENTER).with(material -> Integer.toString(materials.indexOf(material) + 1)), new Column().header("Name").headerAlign(CENTER).dataAlign(LEFT).with(Material::name), new Column().header("Quantity").headerAlign(CENTER).dataAlign(CENTER).with(material -> String.valueOf(material.quantity())), new Column().header("Unit Price").headerAlign(CENTER).dataAlign(CENTER).with(material -> String.valueOf(material.unitPrice())), new Column().header("TVA").headerAlign(CENTER).dataAlign(CENTER).with(material -> String.valueOf(material.tva())), new Column().header("Transport Cost").headerAlign(CENTER).dataAlign(CENTER).with(material -> String.valueOf(material.transportCost())), new Column().header("Coefficient").headerAlign(CENTER).dataAlign(CENTER).with(material -> String.valueOf(material.coefficient())))));
    }
}
