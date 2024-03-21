package fr.medilabo.microservice.note.medecin.services;

import fr.medilabo.microservice.note.medecin.controllers.NoteController;
import fr.medilabo.microservice.note.medecin.models.Note;
import fr.medilabo.microservice.note.medecin.repositories.NoteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private final Logger logger = LoggerFactory.getLogger(NoteService.class);
    @Autowired
    private NoteRepository repository;

    public List<Note> findAll(){
        logger.info("Call findAll - Note Service");
        return repository.findAll();
    }

    public List<Note> findByPatId(Long id){
        logger.info("Call findByPatId - Note Service");
        return repository.findByPatId(id);
    }

    public Note save(Note note){
        logger.info("Call save - Note Service");
        return repository.save(note);
    }

    public void deleteNote(String id){
        logger.info("Call deleteNote - Note Service");
        repository.deleteById(id);
    }
}
