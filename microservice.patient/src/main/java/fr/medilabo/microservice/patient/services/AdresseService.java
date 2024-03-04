package fr.medilabo.microservice.patient.services;

import fr.medilabo.microservice.patient.models.Adresse;
import fr.medilabo.microservice.patient.repositories.AdresseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdresseService {
    @Autowired
    private AdresseRepository adresseRepository;

    public Adresse save(Adresse adresse){
        return adresseRepository.saveAndFlush(adresse);
    }

    public Optional<Adresse> verifyAdresse(Adresse adresse){
        return adresseRepository.verifyAdresse(adresse.getAdresse(), adresse.getVille(), adresse.getCodePostal());
    }
}
