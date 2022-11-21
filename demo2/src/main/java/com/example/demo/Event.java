package com.example.demo;

public class Event {

    private int carbs;
    private int bolus;

    public Event(int carbs, int bolus) {

        this.carbs = carbs;
        this.bolus = bolus;
    }

    public int getCarbs() { return this.carbs; }

    public int getBolus() { return this.bolus; }


}