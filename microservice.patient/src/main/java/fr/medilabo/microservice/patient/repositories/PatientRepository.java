package fr.medilabo.microservice.patient.repositories;

import fr.medilabo.microservice.patient.models.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
}
