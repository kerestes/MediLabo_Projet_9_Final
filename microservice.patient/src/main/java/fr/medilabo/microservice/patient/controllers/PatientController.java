package fr.medilabo.microservice.patient.controllers;

import fr.medilabo.microservice.patient.models.Adresse;
import fr.medilabo.microservice.patient.models.Patient;
import fr.medilabo.microservice.patient.services.AdresseService;
import fr.medilabo.microservice.patient.services.PatientService;
import jakarta.validation.Valid;
import org.apache.catalina.connector.Response;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.JsonPath;
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
    @Autowired
    private PatientService service;

    @Autowired
    private AdresseService adresseService;

    @GetMapping
    public ResponseEntity<List<Patient>> getAll(){
        System.out.println("chamou");
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable Long id){
        Optional<Patient> optionalPatient = service.findById(id);
        if(optionalPatient.isPresent()){
            return ResponseEntity.ok(optionalPatient.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping
    public ResponseEntity<Patient> createPatient(@Valid @RequestBody Patient patient){
        Optional<Patient> optionalPatient = service.verifyPatient(patient);
        if(optionalPatient.isEmpty()){
            if (patient.getAdresse() != null){
                Optional<Adresse> optionalAdresse = adresseService.verifyAdresse(patient.getAdresse());
                if(optionalAdresse.isEmpty()){
                    Adresse adresse = adresseService.save(patient.getAdresse());
                } else {
                    patient.setAdresse(optionalAdresse.get());
                }
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(service.save(patient));
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @PutMapping
    public ResponseEntity<Patient> updatePatient(@Valid @RequestBody Patient patient){
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
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
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
