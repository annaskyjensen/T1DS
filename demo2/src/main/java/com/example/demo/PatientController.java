package com.example.demo;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerator;
import models.StartSimulation;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//creating GET, PUT, POST, DELETE functions
//returns the response
    @RestController
    class PatientController {

    private final ArrayList<Patient> listOfPatients;

    PatientController() {
        this.listOfPatients = new ArrayList<Patient>();
        this.GenerateExampleData();
    }

    private void GenerateExampleData() {
        this.listOfPatients.add(new Patient(
                "Anna",
                165,
                52,
                80

//                "MVP"
        ));
        this.listOfPatients.add(new Patient(
                "Nikolaj",
                180,
                80,
                100
//                "Cambridge"
        ));
        this.listOfPatients.add(new Patient(
                "Caroline",
                175,
                60,
                90
//                "UVA"
        ));
        this.listOfPatients.add(new Patient(
                "Jose",
                180,
                75,
                110
//                "What ever you want"
        ));
        this.listOfPatients.add(new Patient(
                "Andrea",
                185,
                81,
                90
//                "super"
        ));
        this.listOfPatients.add(new Patient(
                "Jade",
                168,
                50,
                80
//                "coolio"
        ));
    } //preloaded patients

    @GetMapping("/patient/{index}") // request info of patient with id (number)
    public ResponseEntity GiveMeNumber(@PathVariable int index) throws IOException { //number is a variable
        if (index >= 0 && index < listOfPatients.size()) {
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(listOfPatients.get(index));
            return ResponseEntity.ok(json);
        } else
            return ResponseEntity.notFound().build();
    }

    @PostMapping("/patient") //creates a new patient
    public ResponseEntity NewPatient(@RequestBody Patient newPatient) throws IOException {
        listOfPatients.add(newPatient);
        //create a file
        File f = new File("/Users/anna/Desktop/PatientCollections/" + newPatient.getName() + ".txt");
        f.createNewFile();
        System.out.print(newPatient.getName());
        return ResponseEntity.ok(newPatient.getName()); //saves new patient in the repository
    }

    @PutMapping("/patient/{index}") //edits info of patient with id = {index}
    //@RequestBody will be in "Body" in json format, @PathVariable is found in the path {{id}}
    public ResponseEntity ReplacePatient(@RequestBody Patient newPatient, @PathVariable int index) {
        this.listOfPatients.set(index, newPatient);
        return ResponseEntity.ok(newPatient);
    }

//    @PutMapping("/patient/{index}/") //change height of patient with id = {index}
//    public ResponseEntity UpdatePatient(@RequestParam int height, @PathVariable int index) {
//        Patient temp = this.listOfPatients.get(index);
//        temp.setHeight(height);
//        return ResponseEntity.ok().build();
//    }

    @DeleteMapping("/patient/{index}") //deletes patient with id = {index}
    public ResponseEntity DeletePatient(@PathVariable int index) throws IOException {
        if (index >= 0 && index < listOfPatients.size()) {
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(listOfPatients.get(index));
            this.listOfPatients.remove(index);
            return ResponseEntity.ok(json);
        } else
            return ResponseEntity.notFound().build();
    }

    @GetMapping("/patient/{index}/StartSim") // send info of patient with id (number) to sim of choice
    public ResponseEntity StartSim(@PathVariable int index, @RequestParam String model) throws IOException { //number is a variable
        if (index >= 0 && index < listOfPatients.size()) {
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(listOfPatients.get(index));
            int ssbg = (listOfPatients.get(index).getSsbg());
            StartSimulation ss = new StartSimulation();
            ss.start(index, ssbg);
            return ResponseEntity.ok(json);
        } else
            return ResponseEntity.notFound().build();
    }

//    @PostMapping("/patient/AddMeal/{index}") //edits patient file
//    public ResponseEntity AddMeal(@RequestBody Meal mealInfo, @PathVariable int index) throws IOException {
////        File f = new File("/Users/anna/Desktop/PatientCollections/" + listOfPatients.get(index).getName() + ".txt");
////
////        FileWriter fw = new FileWriter("/Users/anna/Desktop/PatientCollections/" + listOfPatients.get(index).getName() + ".txt", true);
////        BufferedWriter bw = new BufferedWriter(fw);
//        System.out.print(mealInfo.getCarbs());
////        bw.write(mealInfo.getCarbs() + ", ");
////        bw.close();
//        return ResponseEntity.ok().build();
////        return ResponseEntity.ok(f.getName());
//    }

    @PostMapping("/patient/AddMeal") //creates a new patient
    public ResponseEntity Eating(@RequestBody Meal mealInfo) throws IOException {
        System.out.print(mealInfo.getCarbs());
        return ResponseEntity.ok(mealInfo.getCarbs()); //saves new patient in the repository
    }


}



