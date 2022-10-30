package com.example.demo;

public class Patient {

    private String name;
    private int height;
    private String model;

    public Patient(String name, int height, String model) {
        //attributes of our Patient domain object:
        this.name = name;
        this.height = height;
        this.model = model;
    }

    public String getName() {
        return this.name;
    }

    public int getHeight() {
        return this.height;
    }

    public String getModel() {return this.model; }

    public void setName(String name) {
        this.name = name;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setModel(String model) { this.model = model; }

}