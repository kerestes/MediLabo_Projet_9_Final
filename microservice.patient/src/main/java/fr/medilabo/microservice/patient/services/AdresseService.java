package fr.medilabo.microservice.patient.services;

import fr.medilabo.microservice.patient.controllers.PatientController;
import fr.medilabo.microservice.patient.models.Adresse;
import fr.medilabo.microservice.patient.repositories.AdresseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdresseService {
    private final Logger logger = LoggerFactory.getLogger(AdresseService.class);
    @Autowired
    private AdresseRepository adresseRepository;

    public Adresse save(Adresse adresse){
        logger.info("Call save - Adresse Service");
        return adresseRepository.save(adresse);
    }

    public Optional<Adresse> findById(Long id){
        logger.info("Call findById - Adresse Service");
        return adresseRepository.findById(id);
    }

    public void deleteById(Long id){
        logger.info("Call deleteById - Adresse Service");
        adresseRepository.deleteById(id);
    }

    public Optional<Adresse> verifyAdresse(Adresse adresse){
        logger.info("Call verifyAdresse - Adresse Service");
        return adresseRepository.verifyAdresse(adresse.getAdresse(), adresse.getVille(), adresse.getCodePostal());
    }
}
