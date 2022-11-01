package com.example.demo;

public class Patient {

    private String name;
    private int height;
    private int weight;
    private int ssbg;
//    private String model;

    public Patient(String name, int height, int weight, int ssbg) {
        //attributes of our Patient domain object:
        this.name = name;
        this.height = height;
        this.weight = weight;
        this.ssbg = ssbg;
//        this.model = model;
    }

    public String getName() {
        return this.name;
    }

    public int getHeight() {
        return this.height;
    }

    public int getWeight() { return this.weight; }

    public int getSsbg() { return this.ssbg; }

//    public String getModel() {return this.model; }

    public void setName(String name) {
        this.name = name;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWeight(int weight) { this.weight = weight; }

    public void setSsbg(int ssbg) { this.ssbg = ssbg; }

//    public void setModel(String model) { this.model = model; }



}