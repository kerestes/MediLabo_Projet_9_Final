package fr.medilabo.microservice.risque.IntegrationTest;

import fr.medilabo.microservice.risque.models.Patient;
import fr.medilabo.microservice.risque.services.PatientService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

@SpringBootTest
@TestPropertySource(properties = {
        "notes.url=http://localhost:8082/notes",
        "patient.url=http://localhost:8081/patient"
})
public class PatientServiceTest {

    @Autowired
    private PatientService patientService;

    @Test
    public void patientServiceTest(){
        List<Patient> patients = patientService.getAllPatient();
        System.out.println(patients);
        Assertions.assertTrue(!patients.isEmpty());
        Assertions.assertEquals("Test", patients.get(0).prenom());
        Assertions.assertEquals("TestNone", patients.get(0).nom());
        Assertions.assertEquals("Test", patients.get(1).prenom());
        Assertions.assertEquals("TestBoderline", patients.get(1).nom());
    }
}
