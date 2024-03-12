package fr.medilabo.microservice.risque.services;

import fr.medilabo.microservice.risque.models.Note;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class NoteService {

    private final String URL_NOTES = "http://localhost:8082/notes";

    public List<Note> getAllNotes(){
        RestTemplate restTemplate = new RestTemplate();
        Note[] notes = restTemplate.getForEntity(URL_NOTES, Note[].class).getBody();
        return Arrays.asList(notes);
    }
}
