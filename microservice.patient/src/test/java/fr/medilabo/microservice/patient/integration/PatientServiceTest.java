package fr.medilabo.microservice.patient.integration;

import fr.medilabo.microservice.patient.models.Patient;
import fr.medilabo.microservice.patient.services.PatientService;
import org.aspectj.weaver.ast.Or;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
public class PatientServiceTest {

    @Autowired
    private PatientService patientService;

    @Test
    public void findAllTest(){
        List<Patient> patients = patientService.findAll();

        Assertions.assertTrue(!patients.isEmpty());
        Assertions.assertTrue(patients.size() == 4);
        Assertions.assertEquals("TestNone", patients.get(0).getNom());
        Assertions.assertEquals("TestBoderline", patients.get(1).getNom());
        Assertions.assertEquals("TestDanger", patients.get(2).getNom());
        Assertions.assertEquals("TestEarlyOnset", patients.get(3).getNom());
    }

    @Test
    public void verifyPatientExistTest(){
        Patient patient = new Patient();
        patient.setNom("TestNone");
        patient.setPrenom("Test");
        Optional<Patient> optionalPatient = patientService.verifyPatient(patient);

        Assertions.assertTrue(optionalPatient.isPresent());
        Assertions.assertEquals(1L, optionalPatient.get().getId());
        Assertions.assertEquals("100-222-3333", optionalPatient.get().getTelephone());
    }

    @Test
    public void verifyPatientNoExistTest(){
        Patient patient = new Patient();
        patient.setNom("Dupont");
        patient.setPrenom("Jean");
        Optional<Patient> optionalPatient = patientService.verifyPatient(patient);

        Assertions.assertTrue(optionalPatient.isEmpty());
    }

    @Test
    public void findByIdTest(){
        Optional<Patient> optionalPatient = patientService.findById(1L);

        Assertions.assertTrue(optionalPatient.isPresent());
        Assertions.assertEquals("TestNone", optionalPatient.get().getNom());
    }

    @Test
    public void saveAndDeletePatientTest(){
        //save Patient

        Calendar calendar = Calendar.getInstance();
        calendar.set(1987, 02, 18);
        Patient patient = new Patient();
        patient.setPrenom("Jean");
        patient.setNom("Dupont");
        patient.setGenre('M');
        patient.setDateNaissance(calendar.getTime());

        Patient patient1 = patientService.save(patient);
        Long id = patient1.getId();

        Assertions.assertTrue(patient.getId() != null);

        //Delete Patient
        patientService.deleteById(id);

        Optional<Patient> optionalPatient = patientService.findById(id);

        Assertions.assertTrue(optionalPatient.isEmpty());
    }
}
