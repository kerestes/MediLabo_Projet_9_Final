package fr.medilabo.microservice.note.medecin.services;

import fr.medilabo.microservice.note.medecin.models.Patient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class PatientService {

    private final String URL_PATIENT = "http://localhost:8081/patient";

    public List<Object> getAllPatient(){
        RestTemplate restTemplate = new RestTemplate();
        Patient[] patients = restTemplate.getForObject(URL_PATIENT, Patient[].class);
        return Arrays.asList(patients);
    }
}
