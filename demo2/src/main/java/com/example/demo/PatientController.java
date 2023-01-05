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
        //File f = new File("/Users/anna/Desktop/PatientCollections/" + newPatient.getName() + ".txt");
        File f = new File("/demo2/src/main/java/PatientFiles" + newPatient.getName() + ".txt");
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
            String path = new File("demo2/src/main/java/com/example/demo/mvp.sh").getAbsolutePath();
            Process process = Runtime.getRuntime().exec(new String[]{path,Integer.toString(index),Integer.toString(ssbg) });
            return ResponseEntity.ok(json);
        } else
            return ResponseEntity.notFound().build();
    }

    @PostMapping("/patient/AddEvent/{index}") //edits patient file
    public ResponseEntity AddEvent(@RequestBody Event eventInfo, @PathVariable int index) throws IOException {
        File f = new File("/Users/anna/Desktop/PatientCollections/" + listOfPatients.get(index).getName() + ".txt");

        FileWriter fw = new FileWriter("/Users/anna/Desktop/PatientCollections/" + listOfPatients.get(index).getName() + ".txt", true);
        BufferedWriter bw = new BufferedWriter(fw);
        System.out.print(eventInfo.getCarbs());
        bw.write("[" + eventInfo.getCarbs() + ", " + eventInfo.getBolus() + "]");
        bw.newLine();
        bw.close();
        return ResponseEntity.ok(f.getName());
    }



}



