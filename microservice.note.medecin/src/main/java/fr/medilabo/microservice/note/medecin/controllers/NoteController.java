package fr.medilabo.microservice.note.medecin.controllers;

import fr.medilabo.microservice.note.medecin.models.Note;
import fr.medilabo.microservice.note.medecin.models.Patient;
import fr.medilabo.microservice.note.medecin.services.NoteService;
import fr.medilabo.microservice.note.medecin.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notes")
public class NoteController {

    @Autowired
    private NoteService noteService;
    @Autowired
    private PatientService patientService;

    @GetMapping
    public ResponseEntity<List<Note>> getAllNotes(){
        return ResponseEntity.ok(noteService.findAll());
    }

    @GetMapping("/patlist")
    public ResponseEntity<List<Patient>> getAllPatients(){
        return ResponseEntity.ok(patientService.getAllPatient());
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<Note>> getNotesByPatId(@PathVariable Long id){
        return ResponseEntity.ok(noteService.findByPatId(id));
    }

    @PostMapping
    public ResponseEntity<Note> saveNote(@RequestBody Note note){
        return ResponseEntity.ok(noteService.save(note));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNote(@PathVariable String id){
        noteService.deleteNote(id);
        return ResponseEntity.ok().build();
    }
}
