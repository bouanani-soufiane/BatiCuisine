package org.example.entities;

public class Employee {
    private int id;
    private String name;
    private String surname;

    public int id () {
        return id;
    }

    public Employee setId ( int id ) {
        this.id = id;
        return this;
    }

    public String name () {
        return name;
    }

    public Employee setName ( String name ) {
        this.name = name;
        return this;
    }

    public String surname () {
        return surname;
    }

    public Employee setSurname ( String surname ) {
        this.surname = surname;
        return this;
    }

    @Override
    public String toString () {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }
}
