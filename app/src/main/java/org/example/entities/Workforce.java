package org.example.entities;

import java.time.LocalDateTime;

public class Workforce extends Component{
    private Double pricePerHour;
    private Double workingHours;
    private Double productivityFactor;

    public Workforce(){
        super();
    }

    public Workforce ( String name, Project project , Double tva, Double pricePerHour, Double workingHours, Double productivityFactor ) {
        super(name, project , tva);
        this.pricePerHour = pricePerHour;
        this.workingHours = workingHours;
        this.productivityFactor = productivityFactor;
    }

    public Workforce ( String name, Project project , Double tva , Double pricePerHour, Double workingHours, Double productivityFactor, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt ) {
        super(name, project, tva,createdAt, updatedAt, deletedAt);
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

    @Override
    public String toString () {
        return "Workforce{" +
                "pricePerHour=" + pricePerHour +
                ", workingHours=" + workingHours +
                ", productivityFactor=" + productivityFactor +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", deletedAt=" + deletedAt +
                '}';
    }
}
