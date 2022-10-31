package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication //meta-annotation that pulls in component scanning, autoconfiguration, and property support
public class T1DsapiApplication {

	public static void main(String... args) {
		SpringApplication.run(T1DsapiApplication.class, args);
	}

}