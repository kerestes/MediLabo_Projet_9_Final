package fr.medilabo.microservice.patient.repositories;

import fr.medilabo.microservice.patient.models.Adresse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AdresseRepository extends JpaRepository<Adresse, Long> {

    @Query("SELECt a FROM Adresse a where a.adresse = :adresse and a.ville = :ville and a.codePostal = :codePostal")
    public Optional<Adresse> verifyAdresse(String adresse, String ville, String codePostal);
}
