package fr.medilabo.microservice.risque.services;

import fr.medilabo.microservice.risque.controllers.RisqueController;
import fr.medilabo.microservice.risque.models.Note;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class NoteService {

    private final Logger logger = LoggerFactory.getLogger(NoteService.class);
    @Value("${notes.url}")
    private String URL_NOTES;

    public List<Note> getAllNotes(){
        logger.info("Call getAllNotes - Note Service");
        RestTemplate restTemplate = new RestTemplate();
        try{
            Note[] notes = restTemplate.getForEntity(URL_NOTES, Note[].class).getBody();
            return Arrays.asList(notes);
        } catch (Exception e){
            logger.error("Note service is not responding - connection fail");
        }
        return Arrays.asList();
    }
}
