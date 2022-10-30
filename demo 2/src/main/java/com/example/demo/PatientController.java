package com.example.demo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//creating GET, PUT, POST, DELETE functions
//returns the response
    @RestController
    class PatientController {

        private final ArrayList<Patient> listOfPatients;

        PatientController() {
            this.listOfPatients= new ArrayList<Patient>();
            this.GenerateExampleData();
        }

    private void GenerateExampleData(){
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
        }
            else
                return ResponseEntity.notFound().build();
    }

    @PostMapping("/patient") //creates a new patient
    public ResponseEntity NewPatient(@RequestBody Patient newPatient) {
        listOfPatients.add(newPatient);
        return ResponseEntity.ok().build();//saves new patient in the repository
    }

    @PutMapping("/patient/{index}") //edits info of patient with id = {index}
    //@RequestBody will be in "Body" in json format, @PathVariable is found in the path {{id}}
    public ResponseEntity ReplacePatient(@RequestBody Patient newPatient, @PathVariable int index) {
        this.listOfPatients.set(index, newPatient);
        return ResponseEntity.ok().build();
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
        }
        else
            return ResponseEntity.notFound().build();
    }

    @GetMapping("/patient/{index}/StartSim") // send info of patient with id (number) to sim of choice
    public ResponseEntity StartSim(@PathVariable int index, @RequestParam String model) throws IOException { //number is a variable
        if (index >= 0 && index < listOfPatients.size()) {
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(listOfPatients.get(index));
            return ResponseEntity.ok(json);
        }
        else
            return ResponseEntity.notFound().build();
    }

}

