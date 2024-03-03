package fr.medilabo.microservice.patient.controllers;

import fr.medilabo.microservice.patient.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PatientController {
    @Autowired
    private PatientService service;
}
