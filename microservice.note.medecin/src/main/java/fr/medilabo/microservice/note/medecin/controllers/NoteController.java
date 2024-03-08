package fr.medilabo.microservice.note.medecin.controllers;

import fr.medilabo.microservice.note.medecin.models.Note;
import fr.medilabo.microservice.note.medecin.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notes")
public class NoteController {

    @Autowired
    private NoteService service;

    @GetMapping("/{id}")
    public ResponseEntity<List<Note>> getNotesByPatId(@PathVariable Long id){
        return ResponseEntity.ok(service.findByPatId(id));
    }

    @PostMapping
    public ResponseEntity<Note> saveNote(@RequestBody Note note){
        return ResponseEntity.ok(service.save(note));
    }
}
