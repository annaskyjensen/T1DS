package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;

//by declaring the following interface which extends Spring Data JPA’s JpaRepository, we automatically will be able to:
//  - create new patients
//  - update existing ones
//  - delete patients
//  - find patients

interface PatientRepository extends JpaRepository<Patient, Long> {

}