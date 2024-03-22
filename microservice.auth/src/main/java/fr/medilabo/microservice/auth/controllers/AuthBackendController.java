package fr.medilabo.microservice.auth.controllers;

import fr.medilabo.microservice.auth.models.BackendService;
import fr.medilabo.microservice.auth.services.BackendServiceService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth/back")
public class AuthBackendController {
    private final Logger logger = LoggerFactory.getLogger(AuthBackendController.class);
    @Autowired
    private BackendServiceService service;

    @GetMapping("/find/{name}")
    public ResponseEntity<BackendService> findService(@PathVariable String name, HttpServletRequest request){
        logger.info("Call findService - ip (" + request.getRemoteAddr() +":"+ request.getRemotePort() +") - Name to find (" + name +")");
        Optional<BackendService> optionalBackendService = service.findOneByName(name);
        if(optionalBackendService.isPresent())
            return ResponseEntity.ok(optionalBackendService.get());
        return ResponseEntity.unprocessableEntity().build();
    }

    @PostMapping("/registration")
    public ResponseEntity<BackendService> registrationService(@RequestBody BackendService backendService, HttpServletRequest request){
        logger.info("Call registrationService - ip (" + request.getRemoteAddr() +":"+ request.getRemotePort() +") - Name (" + backendService.getName() +")");
        backendService.setIp(request.getRemoteAddr());
        Optional<BackendService> optionalBackendService = service.findOneByName(backendService.getName());;
        if(optionalBackendService.isPresent()){
            backendService.setId(optionalBackendService.get().getId());
        }
        return ResponseEntity.ok(service.save(backendService));
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
