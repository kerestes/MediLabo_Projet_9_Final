package fr.medilabo.microservice.patient.services;

import fr.medilabo.microservice.patient.models.Patient;
import fr.medilabo.microservice.patient.repositories.PatientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    private final Logger logger = LoggerFactory.getLogger(AdresseService.class);
    @Autowired
    private PatientRepository patientRepository;

    public List<Patient> findAll(){
        logger.info("Call findAll - Patient Service");
        return patientRepository.findAll();
    }

    public Optional<Patient> verifyPatient(Patient patient){
        logger.info("Call verifyPatient - Patient Service");
        return patientRepository.verifyPatient(patient.getNom(), patient.getPrenom());
    }

    public Patient save(Patient patient) {
        logger.info("Call save - Patient Service");
        return patientRepository.save(patient);
    }

    public Optional<Patient> findById(Long id){
        logger.info("Call findById - Patient Service");
        return patientRepository.findById(id);
    }

    public void deleteById(Long id){
        logger.info("Call deleteById - Patient Service");
        patientRepository.deleteById(id);
    }
}
