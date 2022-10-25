package com.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class PatientNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(PatientNotFoundException.class) //advice to only respond if an EmployeeNotFoundException is thrown
    @ResponseStatus(HttpStatus.NOT_FOUND) //issue an HttpStatus.NOT_FOUND
    String employeeNotFoundHandler(PatientNotFoundException ex) {
        return ex.getMessage(); //gives the message of the exception "Could not find patient " + id
    }
}