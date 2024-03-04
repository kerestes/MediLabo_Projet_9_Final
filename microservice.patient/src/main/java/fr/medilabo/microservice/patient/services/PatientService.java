package fr.medilabo.microservice.patient.services;

import fr.medilabo.microservice.patient.models.Patient;
import fr.medilabo.microservice.patient.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    public List<Patient> findAll(){
        return patientRepository.findAll();
    }

    public Optional<Patient> verifyPatient(Patient patient){
        return patientRepository.verifyPatient(patient.getNom(), patient.getPrenom());
    }

    public Patient save(Patient patient) {
        return patientRepository.saveAndFlush(patient);
    }

    public Optional<Patient> findById(Long id){
        return patientRepository.findById(id);
    }

    public void deleteById(Long id){
        patientRepository.deleteById(id);
    }
}
