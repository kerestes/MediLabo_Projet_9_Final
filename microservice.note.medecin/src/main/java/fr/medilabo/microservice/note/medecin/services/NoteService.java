package fr.medilabo.microservice.note.medecin.services;

import fr.medilabo.microservice.note.medecin.models.Note;
import fr.medilabo.microservice.note.medecin.repositories.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    @Autowired
    private NoteRepository repository;

    public List<Note> findAll(){
        return repository.findAll();
    }

    public List<Note> findByPatId(Long id){
        return repository.findByPatId(id);
    }

    public Note save(Note note){
        return repository.save(note);
    }

    public void deleteNote(String id){
        repository.deleteById(id);
    }
}
