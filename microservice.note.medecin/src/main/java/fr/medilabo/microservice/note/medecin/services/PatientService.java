package fr.medilabo.microservice.note.medecin.services;

import fr.medilabo.microservice.note.medecin.models.Patient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class PatientService {

    private final Logger logger = LoggerFactory.getLogger(PatientService.class);
    @Value("${URL_PATIENT}")
    private String URL_PATIENT;

    public List<Patient> getAllPatient(){
        logger.info("Call getAllPatient - Patient Service");
        RestTemplate restTemplate = new RestTemplate();
        try{
            Patient[] patients = restTemplate.getForObject(URL_PATIENT, Patient[].class);
            return Arrays.asList(patients);
        } catch (Exception e){
            logger.error("Patient service is not responding - connection fail");
        }
        return Arrays.asList();
    }
}
