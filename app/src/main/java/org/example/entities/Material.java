package org.example.entities;

import org.example.enums.ComponentType;

import java.time.LocalDateTime;

public class Material extends Component{
    private Double quantity;
    private Double unitPrice;
    private Double transportCost;
    private Double coefficient;


    public Material ( String name, ComponentType componentType, Project project, Double tva , Double quantity, Double unitPrice, Double transportCost, Double coefficient ) {
        super(name, componentType, project, tva);
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.transportCost = transportCost;
        this.coefficient = coefficient;
    }

    public Material ( String name, ComponentType componentType, Project project, Double quantity, Double unitPrice, Double tva, Double transportCost, Double coefficient  , LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt) {
        super(name, componentType, project, tva, createdAt, updatedAt, deletedAt);
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.transportCost = transportCost;
        this.coefficient = coefficient;
    }

    public Double quantity () {
        return quantity;
    }

    public Material setQuantity ( Double quantity ) {
        this.quantity = quantity;
        return this;
    }

    public Double unitPrice () {
        return unitPrice;
    }

    public Material setUnitPrice ( Double unitPrice ) {
        this.unitPrice = unitPrice;
        return this;
    }

    public Double transportCost () {
        return transportCost;
    }

    public Material setTransportCost ( Double transportCost ) {
        this.transportCost = transportCost;
        return this;
    }

    public Double coefficient () {
        return coefficient;
    }

    public Material setCoefficient ( Double coefficient ) {
        this.coefficient = coefficient;
        return this;
    }
}
