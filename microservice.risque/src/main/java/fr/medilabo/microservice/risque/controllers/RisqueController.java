package fr.medilabo.microservice.risque.controllers;

import fr.medilabo.microservice.risque.models.Note;
import fr.medilabo.microservice.risque.models.Patient;
import fr.medilabo.microservice.risque.models.Risque;
import fr.medilabo.microservice.risque.services.NoteService;
import fr.medilabo.microservice.risque.services.PatientService;
import fr.medilabo.microservice.risque.utils.TerminologieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/risques")
public class RisqueController {

    @Autowired
    private NoteService noteService;
    @Autowired
    private PatientService patientService;
    @Autowired
    private TerminologieUtil terminologieUtil;

    @GetMapping
    public ResponseEntity<List<Risque>> getRisques(){
        List<Note> notes = noteService.getAllNotes();
        List<Patient> patients = patientService.getAllPatient();
        return ResponseEntity.ok(terminologieUtil.calculeRisque(notes, patients));
    }
}
