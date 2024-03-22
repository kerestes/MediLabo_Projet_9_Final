package fr.medilabo.microservice.auth.services;

import fr.medilabo.microservice.auth.models.BackendService;
import fr.medilabo.microservice.auth.respositories.BackendServiceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BackendServiceService {
    private Logger logger = LoggerFactory.getLogger(BackendServiceService.class);
    @Autowired
    private BackendServiceRepository repository;

    public Optional<BackendService> findOneByName(String name){
        logger.info("Call findOneByName - BackendService Service - service searched: " + name);
        return repository.findOneByName(name);
    }

    public BackendService save(BackendService backendService){
        logger.info("Call save - BackendService Service - service saved: " + backendService.getName());
        return repository.save(backendService);
    }
}
