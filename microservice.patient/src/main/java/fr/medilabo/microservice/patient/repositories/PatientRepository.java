package fr.medilabo.microservice.patient.repositories;

import fr.medilabo.microservice.patient.models.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    @Query("SELECT p FROM Patient p where p.nom = :nom and p.prenom = :prenom")
    public Optional<Patient> verifyPatient(String nom, String prenom);
}
