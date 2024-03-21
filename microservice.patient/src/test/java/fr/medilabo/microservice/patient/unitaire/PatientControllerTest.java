package fr.medilabo.microservice.patient.unitaire;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.medilabo.microservice.patient.models.Adresse;
import fr.medilabo.microservice.patient.models.Patient;
import fr.medilabo.microservice.patient.services.AdresseService;
import fr.medilabo.microservice.patient.services.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PatientService patientService;
    @MockBean
    private AdresseService adresseService;
    ObjectMapper objectMapper = new ObjectMapper();
    List<Adresse> adresses;
    List<Patient> patients;

    @BeforeEach
    public void setPatients(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(1966,12,31);
        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(1945,06,24);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(2004,06,18);
        Calendar calendar3 = Calendar.getInstance();
        calendar3.set(2002,06,28);

        adresses = Arrays.asList(
                new Adresse(1L, "1 Brookside St", "Paris", "75010"),
                new Adresse(2L, "2 High St", "Paris", "75001"),
                new Adresse(3L, "3 Club Road", "Paris", "75012"),
                new Adresse(4L, "4 Valley Dr", "Paris", "75018")
        );

        patients = Arrays.asList(
                new Patient(1L, "TestNone", "Test", calendar.getTime(), 'F', adresses.get(0), "100-222-3333"),
                new Patient(2L, "TestBoderline", "Test", calendar1.getTime(), 'M', adresses.get(1), "200-33-4444"),
                new Patient(3L, "TestDanger", "Test", calendar2.getTime(), 'M', adresses.get(2), "300-444-5555"),
                new Patient(4L, "TestEarlyOnset", "Test", calendar3.getTime(), 'F', adresses.get(3), "400-555-6666")
        );

    }

    @Test
    public void getAllTest() throws Exception {
        Mockito.when(patientService.findAll()).thenReturn(patients);

        mockMvc.perform(get("/patient")).andExpectAll(
                status().isOk(),
                jsonPath("$[0].nom", containsStringIgnoringCase("TestNone")),
                jsonPath("$[0].adresse.adresse", containsStringIgnoringCase("Brookside")),
                jsonPath("$[2].telephone", containsStringIgnoringCase("300-444-5555")),
                jsonPath("$[0].adresse.ville", containsStringIgnoringCase("Paris"))
        );
    }

    @Test
    public void getAllEmptyListTest() throws Exception {
        Mockito.when(patientService.findAll()).thenReturn(Arrays.asList());

        mockMvc.perform(get("/patient")).andExpect(
                status().isInternalServerError()
        );
    }

    @Test
    public void findByIdTest() throws Exception {
        Mockito.when(patientService.findById(anyLong())).thenReturn(Optional.of(patients.get(0)));

        mockMvc.perform(get("/patient/{id}", 1)).andExpectAll(
                status().isOk(),
                jsonPath("$.nom", containsStringIgnoringCase("TestNone")),
                jsonPath("$.adresse.adresse", containsStringIgnoringCase("Brookside"))
        );
    }

    @Test
    public void findByIdNotFoudTest() throws Exception{
        Mockito.when(patientService.findById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get("/patient/{id}", 5)).andExpect(
                status().isNotFound()
        );
    }

    @Test
    public void createNewPatientWithExistingAddressTest() throws Exception{

        Patient newPatient = makeNewPatient();

        Mockito.when(patientService.verifyPatient(any())).thenReturn(Optional.empty());
        Mockito.when(adresseService.verifyAdresse(any())).thenReturn(Optional.of(adresses.get(0)));
        Mockito.when(patientService.save(any())).thenReturn(newPatient);

        mockMvc.perform(post("/patient").content(objectMapper.writeValueAsString(newPatient)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(
                        status().isCreated()
                );
        Mockito.verify(patientService, Mockito.times(1)).verifyPatient(any());
        Mockito.verify(patientService, Mockito.times(1)).save(any());
        Mockito.verify(adresseService, Mockito.times(1)).verifyAdresse(any());
        Mockito.verify(adresseService, Mockito.times(0)).save(any());
    }

    @Test
    public void createNewPatientWithNewAddressTest() throws Exception {

        Adresse newAdresse = makeNewAdresse();
        Patient newPatient = makeNewPatient();
        newPatient.setAdresse(newAdresse);


        Mockito.when(patientService.verifyPatient(any())).thenReturn(Optional.empty());
        Mockito.when(adresseService.verifyAdresse(any())).thenReturn(Optional.empty());
        Mockito.when(adresseService.save(any())).thenReturn(newAdresse);
        Mockito.when(patientService.save(any())).thenReturn(newPatient);

        mockMvc.perform(post("/patient").content(objectMapper.writeValueAsString(newPatient)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(
                        status().isCreated()
                );
        Mockito.verify(patientService, Mockito.times(1)).verifyPatient(any());
        Mockito.verify(patientService, Mockito.times(1)).save(any());
        Mockito.verify(adresseService, Mockito.times(1)).verifyAdresse(any());
        Mockito.verify(adresseService, Mockito.times(1)).save(any());
    }

    @Test
    public void createNewPatientAlreadyExistsErrorTest() throws Exception {

        Mockito.when(patientService.verifyPatient(any())).thenReturn(Optional.of(patients.get(0)));

        mockMvc.perform(post("/patient").content(objectMapper.writeValueAsString(patients.get(0))).contentType(MediaType.APPLICATION_JSON))
                .andExpect(
                        status().isConflict()
                );
        Mockito.verify(patientService, Mockito.times(1)).verifyPatient(any());
        Mockito.verify(patientService, Mockito.times(0)).save(any());
        Mockito.verify(adresseService, Mockito.times(0)).verifyAdresse(any());
        Mockito.verify(adresseService, Mockito.times(0)).save(any());
    }

    @Test
    public void updatePatientWithoutAdresseTest() throws Exception {
        patients.get(0).setAdresse(null);
        Mockito.when(patientService.findById(anyLong())).thenReturn(Optional.of(patients.get(0)));
        Mockito.when(patientService.save(any())).thenReturn(patients.get(0));

        mockMvc.perform(put("/patient").content(objectMapper.writeValueAsString(patients.get(0))).contentType(MediaType.APPLICATION_JSON))
                .andExpect(
                        status().isOk()
                );

        Mockito.verify(patientService, Mockito.times(1)).findById(any());
        Mockito.verify(patientService, Mockito.times(1)).save(any());
        Mockito.verify(adresseService, Mockito.times(0)).verifyAdresse(any());
        Mockito.verify(adresseService, Mockito.times(0)).save(any());
    }

    @Test
    public void updatePatientWithExistsAdresseTest() throws Exception {
        Mockito.when(patientService.findById(anyLong())).thenReturn(Optional.of(patients.get(0)));
        Mockito.when(adresseService.verifyAdresse(any())).thenReturn(Optional.of(adresses.get(0)));
        Mockito.when(patientService.save(any())).thenReturn(patients.get(0));

        mockMvc.perform(put("/patient").content(objectMapper.writeValueAsString(patients.get(0))).contentType(MediaType.APPLICATION_JSON))
                .andExpect(
                        status().isOk()
                );

        Mockito.verify(patientService, Mockito.times(1)).findById(any());
        Mockito.verify(patientService, Mockito.times(1)).save(any());
        Mockito.verify(adresseService, Mockito.times(1)).verifyAdresse(any());
        Mockito.verify(adresseService, Mockito.times(0)).save(any());
    }

    @Test
    public void updatePatientWithNewAdresseTest() throws Exception {
        patients.get(0).setAdresse(makeNewAdresse());
        Mockito.when(patientService.findById(anyLong())).thenReturn(Optional.of(patients.get(0)));
        Mockito.when(adresseService.verifyAdresse(any())).thenReturn(Optional.empty());
        Mockito.when(adresseService.save(any())).thenReturn(makeNewAdresse());
        Mockito.when(patientService.save(any())).thenReturn(patients.get(0));

        mockMvc.perform(put("/patient").content(objectMapper.writeValueAsString(patients.get(0))).contentType(MediaType.APPLICATION_JSON))
                .andExpect(
                        status().isOk()
                );

        Mockito.verify(patientService, Mockito.times(1)).findById(any());
        Mockito.verify(patientService, Mockito.times(1)).save(any());
        Mockito.verify(adresseService, Mockito.times(1)).verifyAdresse(any());
        Mockito.verify(adresseService, Mockito.times(1)).save(any());
    }

    @Test
    public void updatePatientDoesNotExistErrorTest() throws Exception{
        Mockito.when(patientService.findById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(put("/patient").content(objectMapper.writeValueAsString(patients.get(0))).contentType(MediaType.APPLICATION_JSON))
                .andExpect(
                        status().isNotFound()
                );

        Mockito.verify(patientService, Mockito.times(1)).findById(any());
        Mockito.verify(patientService, Mockito.times(0)).save(any());
        Mockito.verify(adresseService, Mockito.times(0)).verifyAdresse(any());
        Mockito.verify(adresseService, Mockito.times(0)).save(any());
    }

    @Test
    public void deletePatientTest() throws Exception{
        Mockito.doNothing().when(patientService).deleteById(anyLong());

        mockMvc.perform(delete("/patient/{id}", 1)).andExpect(
                status().isOk()
        );

        Mockito.verify(patientService, Mockito.times(1)).deleteById(anyLong());
    }

    private Patient makeNewPatient(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(1987,11,24);

        Patient newPatient = new Patient();
        newPatient.setNom("new Patient");
        newPatient.setPrenom("new Patient");
        newPatient.setTelephone("000-000-0000");
        newPatient.setGenre('M');
        newPatient.setAdresse(adresses.get(0));
        newPatient.setDateNaissance(calendar.getTime());

        return newPatient;
    }

    private Adresse makeNewAdresse(){
        Adresse newAdresse = new Adresse();
        newAdresse.setAdresse("Test adresse");
        newAdresse.setVille("Test ville");
        newAdresse.setCodePostal("00000");
        return newAdresse;
    }
}
