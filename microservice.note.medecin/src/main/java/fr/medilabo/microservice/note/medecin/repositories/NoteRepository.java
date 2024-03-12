package fr.medilabo.microservice.note.medecin.repositories;

import fr.medilabo.microservice.note.medecin.models.Note;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface NoteRepository extends MongoRepository<Note, String> {
    public List<Note> findByPatId(Long id);
}
