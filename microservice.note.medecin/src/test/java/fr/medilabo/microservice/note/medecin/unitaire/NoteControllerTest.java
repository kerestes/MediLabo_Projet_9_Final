package fr.medilabo.microservice.note.medecin.unitaire;


import com.fasterxml.jackson.databind.ObjectMapper;
import fr.medilabo.microservice.note.medecin.models.Note;
import fr.medilabo.microservice.note.medecin.models.Patient;
import fr.medilabo.microservice.note.medecin.services.NoteService;
import fr.medilabo.microservice.note.medecin.services.PatientService;
import org.junit.jupiter.api.Assertions;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class NoteControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private NoteService noteService;
    @MockBean
    private PatientService patientService;
    ObjectMapper objectMapper = new ObjectMapper();
    List<Note> notes;
    List<Patient> patients;

    @BeforeEach
    public void setNotes(){
        notes = Arrays.asList(
                new Note("aaaaaa", 1L, "TestNone", "Le patient déclare qu'il 'se sent très bien' Poids égal ou inférieur au poids recommandé"),
                new Note("bbbbbb", 2L, "TestBorderline", "Le patient déclare qu'il ressent beaucoup de stress au travail Il se plaint également que son audition est anormale dernièrement"),
                new Note("cccccc", 2L, "TestBorderline", "Le patient déclare avoir fait une réaction aux médicaments au cours des 3 derniers mois Il remarque également que son audition continue d'être anormale"),
                new Note("dddddd", 3L, "TestInDanger", "Le patient déclare qu'il fume depuis peu"),
                new Note("eeeeeeee", 3L,  "TestInDanger", "Le patient déclare qu'il est fumeur et qu'il a cessé de fumer l'année dernière Il se plaint également de crises d’apnée respiratoire anormales Tests de laboratoire indiquant un taux de cholestérol LDL élevé"),
                new Note("fffffff", 4L, "TestEarlyOnset", "Le patient déclare qu'il lui est devenu difficile de monter les escaliers Il se plaint également d’être essoufflé Tests de laboratoire indiquant que les anticorps sont élevés Réaction aux médicaments"),
                new Note("gggggggg", 4L, "TestEarlyOnset", "Le patient déclare qu'il a mal au dos lorsqu'il reste assis pendant longtemps"),
                new Note("hhhhhhhhh", 4L, "TestEarlyOnset", "Le patient déclare avoir commencé à fumer depuis peu Hémoglobine A1C supérieure au niveau recommandé"),
                new Note("iiiiiiiii", 4L, "TestEarlyOnset", "Taille, Poids, Cholestérol, Vertige et Réaction")
        );

        patients = Arrays.asList(
                new Patient(1L, "TestNone", "Test"),
                new Patient(2L, "TestBoderline", "Test"),
                new Patient(3L, "TestDanger", "Test"),
                new Patient(4L, "TestEarlyOnset", "Test")
        );
    }

    @Test
    public void getAllTest() throws Exception {
        Mockito.when(noteService.findAll()).thenReturn(notes);

        mockMvc.perform(get("/notes")).andExpectAll(
                status().isOk(),
                jsonPath("$[0].patient", containsStringIgnoringCase("TestNone")),
                jsonPath("$[3].note", containsStringIgnoringCase("fume"))
        );
    }

    @Test
    public void getAllEmptyListTest() throws Exception{
        notes = new ArrayList<>();
        Mockito.when(noteService.findAll()).thenReturn(notes);

        mockMvc.perform(get("/notes")).andExpect(
                status().isInternalServerError()
        );
    }

    @Test
    public void getPatientListTest() throws Exception{
        Mockito.when(patientService.getAllPatient()).thenReturn(patients);

        mockMvc.perform(get("/notes/patlist")).andExpectAll(
                status().isOk(),
                jsonPath("$[0].nom", containsStringIgnoringCase("Test")),
                jsonPath("$[3].prenom", containsStringIgnoringCase("TestEarlyOnset"))
        );
    }

    @Test
    public void getPatientEmptyListTest() throws Exception{
        patients = new ArrayList<>();
        Mockito.when(patientService.getAllPatient()).thenReturn(patients);

        mockMvc.perform(get("/notes/patlist")).andExpect(
                status().isInternalServerError()
        );
    }

    @Test
    public void getNotesByPatIdTest() throws Exception {
        List<Note> notesPat = Arrays.asList(notes.get(1), notes.get(2));
        Mockito.when(noteService.findByPatId(anyLong())).thenReturn(notesPat);

        mockMvc.perform(get("/notes/{id}", 2)).andExpectAll(
                status().isOk(),
                jsonPath("$[0].patient", containsStringIgnoringCase("TestBorderline")),
                jsonPath("$[1].note", containsStringIgnoringCase("anormale"))
        );
    }

    @Test
    public void getNotesByPatIdEmptyListTest() throws Exception {
        List<Note> notesPat = new ArrayList<>();
        Mockito.when(noteService.findByPatId(anyLong())).thenReturn(notesPat);

        mockMvc.perform(get("/notes/{id}", 2)).andExpect(
                status().isNotFound()
        );
    }

    @Test
    public void saveTest() throws Exception {
        Note newNote = new Note("mmmmm", 4L, "Test", "Test, verify, try");
        Mockito.when(noteService.save(any())).thenReturn(newNote);

        mockMvc.perform(post("/notes", 2).content(objectMapper.writeValueAsString(newNote)).contentType(MediaType.APPLICATION_JSON))
            .andExpectAll(
                    status().isOk(),
                    jsonPath("$.patient", containsStringIgnoringCase("Test")),
                    jsonPath("$.note", containsStringIgnoringCase("verify"))
            );
    }

    @Test
    public void deleteTest() throws Exception {
        Mockito.doNothing().when(noteService).deleteNote(anyString());
        mockMvc.perform(delete("/notes/{id}", 1)).andExpect(
                status().isOk()
        );

        Mockito.verify(noteService, Mockito.times(1)).deleteNote(any());
    }
}
