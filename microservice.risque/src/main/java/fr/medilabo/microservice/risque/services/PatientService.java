package fr.medilabo.microservice.risque.services;

import fr.medilabo.microservice.risque.models.BackendService;
import fr.medilabo.microservice.risque.models.Patient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class PatientService {

    private final Logger logger = LoggerFactory.getLogger(PatientService.class);
    @Value("${patient.url}")
    private String URL_PATIENT;
    @Autowired
    private BackendService backendService;

    public List<Patient> getAllPatient(){
        logger.info("Call getAllPatient - Patient Service - with Backend Service, name (" + backendService.getName() +") - reg number (" + backendService.getRegNumber() + ")");
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> request = new HttpEntity<String>(setHeaders());
        try{
            Patient[] patients = restTemplate.exchange(URL_PATIENT, HttpMethod.GET, request, Patient[].class).getBody();
            return Arrays.asList(patients);
        } catch (Exception e){
            logger.error("Patient service is not responding - connection fail");
        }
        return Arrays.asList();
    }

    private HttpHeaders setHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Backend-name", backendService.getName());
        headers.add("Backend-reg-number", backendService.getRegNumber().toString());
        return headers;
    }
}
