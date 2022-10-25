package com.example.demo;

class PatientNotFoundException extends RuntimeException {

    PatientNotFoundException(Long id) {
        super("Could not find patient " + id);
    }
}