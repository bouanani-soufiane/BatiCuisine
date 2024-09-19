package org.example.entities;

import org.example.enums.ComponentType;

import java.time.LocalDateTime;

public class Workforce extends Component{
    private Double pricePerHour;
    private Double workingHours;
    private Double productivityFactor;

    public Workforce ( String name, ComponentType componentType, Project project , Double tva, Double pricePerHour, Double workingHours, Double productivityFactor ) {
        super(name, componentType, project , tva);
        this.pricePerHour = pricePerHour;
        this.workingHours = workingHours;
        this.productivityFactor = productivityFactor;
    }

    public Workforce ( String name, ComponentType componentType, Project project , Double tva , Double pricePerHour, Double workingHours, Double productivityFactor, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt ) {
        super(name, componentType, project, tva,createdAt, updatedAt, deletedAt);
        this.pricePerHour = pricePerHour;
        this.workingHours = workingHours;
        this.productivityFactor = productivityFactor;
    }

    public Double pricePerHour () {
        return pricePerHour;
    }

    public Workforce setPricePerHour ( Double pricePerHour ) {
        this.pricePerHour = pricePerHour;
        return this;
    }

    public Double workingHours () {
        return workingHours;
    }

    public Workforce setWorkingHours ( Double workingHours ) {
        this.workingHours = workingHours;
        return this;
    }

    public Double productivityFactor () {
        return productivityFactor;
    }


    public Workforce setProductivityFactor(Double productivityFactor) {
        this.productivityFactor = productivityFactor;
        return this;
    }
}
