package fr.medilabo.microservice.note.medecin.controllers;

import fr.medilabo.microservice.note.medecin.models.Note;
import fr.medilabo.microservice.note.medecin.models.Patient;
import fr.medilabo.microservice.note.medecin.services.NoteService;
import fr.medilabo.microservice.note.medecin.services.PatientService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notes")
public class NoteController {

    private final Logger logger = LoggerFactory.getLogger(NoteController.class);
    @Autowired
    private NoteService noteService;
    @Autowired
    private PatientService patientService;

    @GetMapping
    public ResponseEntity<List<Note>> getAllNotes(HttpServletRequest request){
        logger.info("Call getAllNotes - ip (" + request.getRemoteAddr() +") - token (" + request.getHeader("Authorization") +")");
        List<Note> notes = noteService.findAll();
        if(!notes.isEmpty())
            return ResponseEntity.ok(notes);
        logger.warn("Empty Note list - communication error ?");
        return ResponseEntity.internalServerError().build();
    }

    @GetMapping("/patlist")
    public ResponseEntity<List<Patient>> getAllPatients(HttpServletRequest request){
        logger.info("Call getAllPatients - ip (" + request.getRemoteAddr() +") - token (" + request.getHeader("Authorization") +")");
        List<Patient> patients = patientService.getAllPatient();
        if(!patients.isEmpty())
            return ResponseEntity.ok(patientService.getAllPatient());
        logger.warn("Empty patient list - communication error ?");
        return ResponseEntity.internalServerError().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<Note>> getNotesByPatId(@PathVariable Long id, HttpServletRequest request){
        logger.info("Call getNotesByPatId - ip (" + request.getRemoteAddr() +") - token (" + request.getHeader("Authorization") +")");
        List<Note> notes = noteService.findByPatId(id);
        if(!notes.isEmpty())
            return ResponseEntity.ok(notes);
        logger.info("Entity not found - ip (" + request.getRemoteAddr() +") - token (" + request.getHeader("Authorization") +")");
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Note> saveNote(@RequestBody Note note, HttpServletRequest request){
        logger.info("Call saveNote - ip (" + request.getRemoteAddr() +") - token (" + request.getHeader("Authorization") +")");
        return ResponseEntity.ok(noteService.save(note));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNote(@PathVariable String id, HttpServletRequest request){
        logger.info("Call saveNote - ip (" + request.getRemoteAddr() +") - token (" + request.getHeader("Authorization") +")");
        noteService.deleteNote(id);
        return ResponseEntity.ok().build();
    }
}
