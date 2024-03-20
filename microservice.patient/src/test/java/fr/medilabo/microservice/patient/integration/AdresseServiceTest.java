package fr.medilabo.microservice.patient.integration;

import fr.medilabo.microservice.patient.models.Adresse;
import fr.medilabo.microservice.patient.services.AdresseService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
public class AdresseServiceTest {

    @Autowired
    private AdresseService adresseService;

    @Test
    public void verifyAdresseExistsTest(){
        Adresse adresse = new Adresse();
        adresse.setCodePostal("75010");
        adresse.setVille("Paris");
        adresse.setAdresse("1 Brookside St");

        Optional<Adresse> optionalAdresse = adresseService.verifyAdresse(adresse);

        Assertions.assertTrue(optionalAdresse.isPresent());
        Assertions.assertEquals(1L, optionalAdresse.get().getId());
    }

    @Test
    public void saveAndDeleteAdresse(){
        Adresse adresse = new Adresse();
        adresse.setCodePostal("00000");
        adresse.setVille("Teste");
        adresse.setAdresse("test");

        //save
        Assertions.assertTrue(adresse.getId() == null);
        Adresse savedAdresse = adresseService.save(adresse);
        Assertions.assertTrue(savedAdresse.getId() != null);

        Long id = savedAdresse.getId();;

        //delete
        adresseService.deleteById(id);
        Optional<Adresse> optionalAdresse = adresseService.findById(id);
        Assertions.assertTrue(optionalAdresse.isEmpty());
    }
}
