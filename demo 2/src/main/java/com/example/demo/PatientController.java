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

//        private final PatientRepository repository; //brings in functionalities from JPA repository

        private final ArrayList<Patient> listOfPatients;

        PatientController() {
//            this.repository = repository;
            this.listOfPatients= new ArrayList<Patient>();
            this.GenerateExampleData();
        }

    private void GenerateExampleData(){
        this.listOfPatients.add(new Patient(
                "Anna",
                165,
                "MVP"
        ));
        this.listOfPatients.add(new Patient(
                "Nikolaj",
                180,
                "Cambridge"
        ));
        this.listOfPatients.add(new Patient(
                "Caroline",
                175,
                "UVA"
        ));
        this.listOfPatients.add(new Patient(
                "Jose",
                180,
                "What ever you want"
        ));
        this.listOfPatients.add(new Patient(
                "Andrea",
                185,
                "super"
        ));
        this.listOfPatients.add(new Patient(
                "Jade",
                168,
                "coolio"
        ));
    }

//    @GetMapping("/patients") //provides list of all existing patients
//    List<Patient> all() {
//        return repository.findAll();
//    }

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

//    @PutMapping("/patient/{index}/{height}") //change height of patient with id = {index}
//    //@RequestBody will be in "Body" in json format, @PathVariable is found in the path {{id}}
//    public ResponseEntity ReplacePatient(@PathVariable int height, @PathVariable int index) {
//        Patient temp = this.listOfPatients.get(index);
//        temp.setHeight(height);
//        return ResponseEntity.ok().build();
//    }

    @PutMapping("/patient/{index}/") //change height of patient with id = {index}
    public ResponseEntity UpdatePatient(@RequestParam int height, @PathVariable int index) {
        Patient temp = this.listOfPatients.get(index);
        temp.setHeight(height);
        return ResponseEntity.ok().build();
    }

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


    //a more restful aggregate root (aggregate root resource)
//    @GetMapping("/patients")
//    CollectionModel<EntityModel<Patient>> all() { //CollectionModel Spring HATEOAS container, encapsulates collections of resources
//
//        //encapsulating the difference collections (embedded: patientList, links)
//        List<EntityModel<Patient>> employees = repository.findAll().stream()
//                .map(patient -> EntityModel.of(patient,
//                        linkTo(methodOn(PatientController.class).one(patient.getId())).withSelfRel(),
//                        linkTo(methodOn(PatientController.class).all()).withRel("patients")))
//                .collect(Collectors.toList());
//
//        return CollectionModel.of(employees, //embedded
//                linkTo(methodOn(PatientController.class).all()).withSelfRel()); //links
        //What is the point of adding all these links? It makes it possible to evolve REST services over time. Existing links
        // can be maintained while new links can be added in the future. Newer clients may take advantage of the new links,
        // while legacy clients can sustain themselves on the old links. This is especially helpful if services get relocated
        // and moved around. As long as the link structure is maintained, clients can STILL find and interact with things.
    // }
//
//    @PostMapping("/patients") //creates a new patient
//    Patient newPatient(@RequestBody Patient newPatient) {
//        return repository.save(newPatient); //saves new patient in the repository
//    }

//    @GetMapping("/patients/{id}") //finds patient with id = 3
//    Patient one(@PathVariable Long id) {
//
//        return repository.findById(id)
//                .orElseThrow(() -> new PatientNotFoundException(id)); //if patient 3 doesn't exist
//    }

//    @GetMapping("/patients/{id}") //creates RESTful representation of a single employee
//    EntityModel<Patient> one(@PathVariable Long id) {
//
//        Patient patient = repository.findById(id) //assigns findById info to the local variable patient
//                .orElseThrow(() -> new PatientNotFoundException(id)); //why is it exception not advice?
//
//
//        return EntityModel.of(patient, //returns id, name, and role
//                //decompressed output shows a "_links" entry containing two URIs now
//                linkTo(methodOn(PatientController.class).one(id)).withSelfRel(),
//                linkTo(methodOn(PatientController.class).all()).withRel("patients"));
//    }

//    @PutMapping("/patients/{id}") //edits info of patient with id = 3
//    //@RequestBody will be in "Body" in json format, @PathVariable is found in the path {{id}}
//    Patient replacePatient(@RequestBody Patient newPatient, @PathVariable Long id) {
//
//        return repository.findById(id)
//                .map(patient -> { //
//                    patient.setName(newPatient.getName());
//                    patient.setHeight(newPatient.getHeight());
//                    patient.setModel(newPatient.getModel());
//                    return repository.save(patient);
//                })
//                .orElseGet(() -> {
//                    newPatient.setId(id);
//                    return repository.save(newPatient);
//                });
//    }

//    @DeleteMapping("/patients/{id}") //deletes patient with id = 3
//    void deletePatient(@PathVariable Long id) {
//        repository.deleteById(id);
//    }
}

