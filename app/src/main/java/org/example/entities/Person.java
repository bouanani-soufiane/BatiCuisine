package org.example.entities;

public class Person {
    private int id;
    private String name;

    public int id () {
        return id;
    }

    public Person setId ( int id ) {
        this.id = id;
        return this;
    }

    public String name () {
        return name;
    }

    public Person setName ( String name ) {
        this.name = name;
        return this;
    }

}
