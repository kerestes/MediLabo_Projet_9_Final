package fr.medilabo.microservice.patient.controllers;

import fr.medilabo.microservice.patient.models.Adresse;
import fr.medilabo.microservice.patient.models.Patient;
import fr.medilabo.microservice.patient.services.AdresseService;
import fr.medilabo.microservice.patient.services.PatientService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/patient")
//@CrossOrigin(origins = "http://localhost:9001")
public class PatientController {

    private final Logger logger = LoggerFactory.getLogger(PatientController.class);
    @Autowired
    private PatientService service;

    @Autowired
    private AdresseService adresseService;

    @GetMapping
    public ResponseEntity<List<Patient>> getAll(HttpServletRequest request){
        logger.info("Call getAll - ip (" + request.getRemoteAddr() +") - token (" + request.getHeader("Authorization") +")");
        List<Patient> patients = service.findAll();
        if (!patients.isEmpty()){
            return ResponseEntity.ok(patients);
        }
        logger.warn("Empty patient list - communication error ?");
        return ResponseEntity.internalServerError().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable Long id, HttpServletRequest request){
        logger.info("Call getPatientById - ip (" + request.getRemoteAddr() +") - token (" + request.getHeader("Authorization") +")");
        Optional<Patient> optionalPatient = service.findById(id);
        if(optionalPatient.isPresent()){
            return ResponseEntity.ok(optionalPatient.get());
        }
        logger.info("Entity not found - ip (" + request.getRemoteAddr() +") - token (" + request.getHeader("Authorization") +")");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping
    public ResponseEntity<Patient> createPatient(@Valid @RequestBody Patient patient, HttpServletRequest request){
        logger.info("Call createPatient - ip (" + request.getRemoteAddr() +") - token (" + request.getHeader("Authorization") +")");
        Optional<Patient> optionalPatient = service.verifyPatient(patient);
        if(optionalPatient.isEmpty()){
            if (patient.getAdresse() != null){
                Optional<Adresse> optionalAdresse = adresseService.verifyAdresse(patient.getAdresse());
                if(optionalAdresse.isEmpty()){
                    adresseService.save(patient.getAdresse());
                } else {
                    patient.setAdresse(optionalAdresse.get());
                }
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(service.save(patient));
        }
        logger.info("Entity already exists - ip (" + request.getRemoteAddr() +") - token (" + request.getHeader("Authorization") +")");
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @PutMapping
    public ResponseEntity<Patient> updatePatient(@Valid @RequestBody Patient patient, HttpServletRequest request){
        logger.info("Call updatePatient - ip (" + request.getRemoteAddr() +") - token (" + request.getHeader("Authorization") +")");
        Optional<Patient> optionalPatient = service.findById(patient.getId());
        if(optionalPatient.isPresent()){
            if(patient.getAdresse() != null){
                Optional<Adresse> optionalAdresse = adresseService.verifyAdresse(patient.getAdresse());
                if(optionalAdresse.isPresent()){
                    patient.setAdresse(optionalAdresse.get());
                } else{
                    patient.setAdresse(adresseService.save(patient.getAdresse()));
                }
            }
            return ResponseEntity.ok(service.save(patient));
        }
        logger.info("Entity not found - ip (" + request.getRemoteAddr() +") - token (" + request.getHeader("Authorization") +")");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id, HttpServletRequest request){
        logger.info("Call deleteById - ip (" + request.getRemoteAddr() +") - token (" + request.getHeader("Authorization") +")");
        service.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach((error) ->{

            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });
        return new ResponseEntity<Object>(errors, HttpStatus.BAD_REQUEST);
    }
}
