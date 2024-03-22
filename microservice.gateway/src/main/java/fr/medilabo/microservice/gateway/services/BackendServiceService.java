package fr.medilabo.microservice.gateway.services;

import fr.medilabo.microservice.gateway.models.BackendService;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class BackendServiceService {

    private final Logger logger = LoggerFactory.getLogger(BackendServiceService.class);
    @Value("${auth.path}")
    private String URL_AUTH_BACK;

    @Autowired
    private BackendService backendService;

    @PostConstruct
    public void backendServiceRegistration (){
        logger.info("Backend Service " + backendService.getName() + " initialization - with UUID: " + backendService.getRegNumber());
        RestTemplate template = new RestTemplate();
        backendService = template.postForEntity(URL_AUTH_BACK+"/registration", backendService, BackendService.class).getBody();
        if(backendService.getId() != 0)
            logger.info("Backend Service successfully initialized");
        else
            logger.warn("Backend Service initialization error");
    }

    public Optional<BackendService> findBackendServiceByName(String name){
        logger.info("Call findBackendServiceByName - BackendService Service");
        RestTemplate template = new RestTemplate();
        return Optional.ofNullable(template.getForEntity(URL_AUTH_BACK+"/find/"+name, BackendService.class).getBody());
    }
}
