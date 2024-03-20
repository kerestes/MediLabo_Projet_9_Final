package fr.medilabo.microservice.risque.controllers;

import fr.medilabo.microservice.risque.models.Note;
import fr.medilabo.microservice.risque.models.Patient;
import fr.medilabo.microservice.risque.models.Risque;
import fr.medilabo.microservice.risque.services.NoteService;
import fr.medilabo.microservice.risque.services.PatientService;
import fr.medilabo.microservice.risque.utils.TerminologieUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/risques")
public class RisqueController {

    private final Logger logger = LoggerFactory.getLogger(RisqueController.class);

    @Autowired
    private NoteService noteService;
    @Autowired
    private PatientService patientService;
    @Autowired
    private TerminologieUtil terminologieUtil;

    @GetMapping
    public ResponseEntity<List<Risque>> getRisques(HttpServletRequest request){
        logger.info("Call getRisques - ip (" + request.getRemoteAddr() +") - token (" + request.getHeader("Authorization") +")");
        List<Note> notes = noteService.getAllNotes();
        List<Patient> patients = patientService.getAllPatient();
        if(patients != null && !patients.isEmpty() && notes != null && !notes.isEmpty())
            return ResponseEntity.ok(terminologieUtil.calculeRisque(notes, patients));
        return ResponseEntity.internalServerError().build();
    }
}
