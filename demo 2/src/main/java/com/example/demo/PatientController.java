package com.example.demo;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

//creating GET, PUT, POST, DELETE functions
//returns the response
@RestController
class PatientController {

    private final PatientRepository repository; //brings in functionalities from JPA repository

    PatientController(PatientRepository repository) {
        this.repository = repository;
    }

//    @GetMapping("/patients") //provides list of all existing patients
//    List<Patient> all() {
//        return repository.findAll();
//    }

    //a more restful aggregate root (aggregate root resource)
    @GetMapping("/patients")
    CollectionModel<EntityModel<Patient>> all() { //CollectionModel Spring HATEOAS container, encapsulates collections of resources

        //encapsulating the difference collections (embedded: patientList, links)
        List<EntityModel<Patient>> employees = repository.findAll().stream()
                .map(patient -> EntityModel.of(patient,
                        linkTo(methodOn(PatientController.class).one(patient.getId())).withSelfRel(),
                        linkTo(methodOn(PatientController.class).all()).withRel("patients")))
                .collect(Collectors.toList());

        return CollectionModel.of(employees, //embedded
                linkTo(methodOn(PatientController.class).all()).withSelfRel()); //links
        //What is the point of adding all these links? It makes it possible to evolve REST services over time. Existing links
        // can be maintained while new links can be added in the future. Newer clients may take advantage of the new links,
        // while legacy clients can sustain themselves on the old links. This is especially helpful if services get relocated
        // and moved around. As long as the link structure is maintained, clients can STILL find and interact with things.
    }

    @PostMapping("/patients") //creates a new patient
    Patient newPatient(@RequestBody Patient newPatient) {
        return repository.save(newPatient); //saves new patient in the repository
    }

//    @GetMapping("/patients/{id}") //finds patient with id = 3
//    Patient one(@PathVariable Long id) {
//
//        return repository.findById(id)
//                .orElseThrow(() -> new PatientNotFoundException(id)); //if patient 3 doesn't exist
//    }

    @GetMapping("/patients/{id}") //creates RESTful representation of a single employee
    EntityModel<Patient> one(@PathVariable Long id) {

        Patient patient = repository.findById(id) //assigns findById info to the local variable patient
                .orElseThrow(() -> new PatientNotFoundException(id)); //why is it exception not advice?


        return EntityModel.of(patient, //returns id, name, and role
                //decompressed output shows a "_links" entry containing two URIs now
                linkTo(methodOn(PatientController.class).one(id)).withSelfRel(),
                linkTo(methodOn(PatientController.class).all()).withRel("patients"));
    }

    @PutMapping("/patients/{id}") //edits info of patient with id = 3
    //@RequestBody will be in "Body" in json format, @PathVariable is found in the path {{id}}
    Patient replacePatient(@RequestBody Patient newPatient, @PathVariable Long id) {

        return repository.findById(id)
                .map(patient -> { //
                    patient.setName(newPatient.getName());
                    patient.setRole(newPatient.getRole());
                    patient.setModel(newPatient.getModel());
                    return repository.save(patient);
                })
                .orElseGet(() -> {
                    newPatient.setId(id);
                    return repository.save(newPatient);
                });
    }

    @DeleteMapping("/patients/{id}") //deletes patient with id = 3
    void deletePatient(@PathVariable Long id) {
        repository.deleteById(id);
    }
}

