package fr.medilabo.microservice.risque.unitaire;

import fr.medilabo.microservice.risque.models.Note;
import fr.medilabo.microservice.risque.models.Patient;
import fr.medilabo.microservice.risque.services.NoteService;
import fr.medilabo.microservice.risque.services.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = {
        "notes.url=http://localhost:8082",
        "patient.url=http://localhost:8081"
})
public class GetRisqueTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PatientService patientService;
    @MockBean
    private NoteService noteService;
    private List<Note> noteList;
    private List<Patient> patientList;

    @BeforeEach
    public void setLists(){
        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(2000, 10, 10);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(1990, 10, 10);
        Calendar calendar3 = Calendar.getInstance();
        calendar3.set(2010, 10, 10);
        Calendar calendar4 = Calendar.getInstance();
        calendar4.set(2005, 10, 10);
        Calendar calendar5 = Calendar.getInstance();
        calendar5.set(2015, 10, 10);
        Calendar calendar6 = Calendar.getInstance();
        calendar6.set(2095, 10, 10);

        patientList = Arrays.asList(
                new Patient(1L, "Jean", "Dupont", calendar1.getTime(), 'M'),
                new Patient(2L, "Marie", "Carlo", calendar2.getTime(), 'F'),
                new Patient(3L, "Robert", "Michel", calendar3.getTime(), 'M'),
                new Patient(4L, "Clara", "Rodrigues", calendar4.getTime(), 'F'),
                new Patient(5L, "Antoine", "Pinto", calendar5.getTime(), 'M'),
                new Patient(6L, "Jeanne", "Silva", calendar6.getTime(), 'F')
        );

        noteList = Arrays.asList(
                new Note(1L, "Dupont", "test test Anticorps"),
                new Note(2L, "Carlo", "test test Poids"),
                new Note(2L, "Carlo", "test test Anormal"),
                new Note(3L, "Michel", "test test Hémoglobine A1C"),
                new Note(3L, "Michel", "test test Microalbumine"),
                new Note(3L, "Michel", "test test Taille"),
                new Note(4L, "Rodrigues", "test test Cholestérol"),
                new Note(4L, "Rodrigues", "test test Fumeur"),
                new Note(4L, "Rodrigues", "test test Rechute"),
                new Note(4L, "Rodrigues", "test test Vertiges"),
                new Note(5L, "Pinto", "test test Cholestérol"),
                new Note(5L, "Pinto", "test test Réaction"),
                new Note(5L, "Pinto", "test test Rechute"),
                new Note(5L, "Pinto", "test test Anormal"),
                new Note(5L, "Pinto", "test test Microalbumine"),
                new Note(6L, "Silva", "test test Hémoglobine A1C"),
                new Note(6L, "Silva", "test test Anticorps"),
                new Note(6L, "Silva", "test test Poid"),
                new Note(6L, "Silva", "test test Fumeur"),
                new Note(6L, "Silva", "test test Vertiges"),
                new Note(6L, "Silva", "test test Microalbumine"),
                new Note(6L, "Silva", "test test Anticorps")
        );
    }

    @Test
    public void getRisqueTest() throws Exception {
        Mockito.when(patientService.getAllPatient()).thenReturn(patientList);
        Mockito.when(noteService.getAllNotes()).thenReturn(noteList);

        mockMvc.perform(get("/risques")).andExpectAll(
                status().isOk(),
                jsonPath("$[0].risque", containsStringIgnoringCase("none")),
                jsonPath("$[1].risque", containsStringIgnoringCase("Borderline")),
                jsonPath("$[2].risque", containsStringIgnoringCase("InDanger")),
                jsonPath("$[3].risque", containsStringIgnoringCase("InDanger")),
                jsonPath("$[4].risque", containsStringIgnoringCase("EarlyOnset")),
                jsonPath("$[5].risque", containsStringIgnoringCase("EarlyOnset"))

        );
    }

    @Test
    public void getRisquePatientNullTest() throws Exception {
        Mockito.when(patientService.getAllPatient()).thenReturn(Arrays.asList());
        Mockito.when(noteService.getAllNotes()).thenReturn(noteList);

        mockMvc.perform(get("/risques")).andExpect(
                status().isInternalServerError()

        );
    }

    @Test
    public void getRisqueNotesNullTest() throws Exception {
        Mockito.when(patientService.getAllPatient()).thenReturn(patientList);
        Mockito.when(noteService.getAllNotes()).thenReturn(Arrays.asList());

        mockMvc.perform(get("/risques")).andExpect(
                status().isInternalServerError()

        );
    }

}
