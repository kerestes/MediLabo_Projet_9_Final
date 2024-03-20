package fr.medilabo.microservice.risque.IntegrationTest;

import fr.medilabo.microservice.risque.models.Note;
import fr.medilabo.microservice.risque.services.NoteService;
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
public class NoteServiceTest {
    @Autowired
    private NoteService noteService;

    @Test
    public void noteServiceTest(){
        List<Note> notes = noteService.getAllNotes();
        System.out.println(notes);
        Assertions.assertTrue(!notes.isEmpty());
        Assertions.assertTrue(notes.get(0).patient().equals("TestNone"));
        Assertions.assertTrue(notes.get(0).note().contains("Poids"));
        Assertions.assertTrue(notes.get(1).patient().equals("TestBoderline"));
        Assertions.assertTrue(notes.get(1).note().contains("anormale"));
    }

}
