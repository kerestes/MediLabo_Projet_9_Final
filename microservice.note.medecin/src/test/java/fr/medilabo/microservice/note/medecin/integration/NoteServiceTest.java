package fr.medilabo.microservice.note.medecin.integration;

import fr.medilabo.microservice.note.medecin.models.Note;
import fr.medilabo.microservice.note.medecin.services.NoteService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
public class NoteServiceTest {
    @Autowired
    private NoteService noteService;

    @Test
    public void findAllTest(){
        List<Note> notes = noteService.findAll();

        Assertions.assertTrue(!notes.isEmpty());
        Assertions.assertTrue(notes.get(0).getPatient().equals("TestNone"));
        Assertions.assertTrue(notes.get(0).getNote().contains("Poids"));
        Assertions.assertTrue(notes.get(1).getPatient().contains("TestBorderline"));
        Assertions.assertTrue(notes.get(1).getNote().contains("anormale"));
    }

    @Test
    public void findByPatIdTest(){
        List<Note> notes = noteService.findByPatId(2L);

        Assertions.assertEquals(2, notes.size());
        Assertions.assertTrue(notes.get(0).getNote().contains("audition"));
        Assertions.assertTrue(notes.get(1).getPatient().contains("TestBorderline"));
    }

    @Test
    public void saveAndDeleteTest(){
        Note note = new Note();
        note.setPatId(5L);
        note.setPatient("Test");
        note.setNote("Test Test");

        //save
        Note savedNote = noteService.save(note);
        String _id = savedNote.get_id();

        Assertions.assertTrue(savedNote.get_id() != null);
        List<Note> findNote = noteService.findByPatId(5L);

        Assertions.assertEquals(1, findNote.size());
        Assertions.assertEquals(savedNote.getPatient(), findNote.get(0).getPatient());

        //delete

        noteService.deleteNote(_id);
        findNote = noteService.findByPatId(5L);

        Assertions.assertTrue(findNote.isEmpty());

    }
}
