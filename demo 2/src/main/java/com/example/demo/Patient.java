package com.example.demo;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity //a JPA annotation to make this object ready for storage in a JPA-based store
class Patient {

    private @Id @GeneratedValue Long id; //we want to automatically generate the primary key value
    private String name;
    private String role;
    private String model;

    Patient() {}

    Patient(String name, String role, String model) {
        //attributes of our Patient domain object:
        this.name = name;
        this.role = role;
        this.model = model;
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getRole() {
        return this.role;
    }

    public String getModel() {return this.model; }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setModel(String model) { this.model = model; }

    @Override
    //test whether the object is an instance of the specified type
    public boolean equals(Object o) {

        if (this == o) //if this.() is equal to the object
            return true;
        if (!(o instanceof Patient)) //o is not of type Patient
            return false;
        Patient patient = (Patient) o;
        //check equality of the patient's id, name, role, and model
        return Objects.equals(this.id, patient.id) && Objects.equals(this.name, patient.name)
                && Objects.equals(this.role, patient.role)
                && Objects.equals(this.model, patient.model);
    }

    @Override
    //hashCode() returns an integer value, generated by a hashing algorithm
    //objects that are equal return the same hash code
    public int hashCode() {
        return Objects.hash(this.id, this.name, this.role, this.model);
    }

    @Override
    //shows user desired patient information
    public String toString() {
        return "Patient{" + "id=" + this.id + ", name='" + this.name + '\'' + ", role='" + this.role + '\'' + ", model='" + this.model + '\'' + '}';
    }
}