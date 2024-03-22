package fr.medilabo.microservice.risque.services;

import fr.medilabo.microservice.risque.controllers.RisqueController;
import fr.medilabo.microservice.risque.models.BackendService;
import fr.medilabo.microservice.risque.models.Note;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class NoteService {

    private final Logger logger = LoggerFactory.getLogger(NoteService.class);
    @Value("${notes.url}")
    private String URL_NOTES;

    @Autowired
    private BackendService backendService;

    public List<Note> getAllNotes(){
        logger.info("Call getAllNotes - Note Service - with Backend Service, name (" + backendService.getName() +") - reg number (" + backendService.getRegNumber() + ")");
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Backend-name", backendService.getName());
        headers.add("Backend-reg-number", backendService.getRegNumber().toString());
        HttpEntity<String> request = new HttpEntity<String>(headers);
        try{
            Note[] notes = restTemplate.exchange(URL_NOTES, HttpMethod.GET, request, Note[].class).getBody();
            return Arrays.asList(notes);
        } catch (Exception e){
            logger.error("Note service is not responding - connection fail");
        }
        return Arrays.asList();
    }
}
