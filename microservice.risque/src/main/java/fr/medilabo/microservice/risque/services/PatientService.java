package fr.medilabo.microservice.risque.services;

import fr.medilabo.microservice.risque.models.Patient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class PatientService {

    @Value("${patient.url}")
    private String URL_PATIENT;

    public List<Patient> getAllPatient(){
        RestTemplate restTemplate = new RestTemplate();
        Patient[] patients = restTemplate.getForObject(URL_PATIENT, Patient[].class);
        return Arrays.asList(patients);
    }
}
