package fr.medilabo.microservice.patient.context;

import fr.medilabo.microservice.patient.models.BackendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.*;

@Configuration
public class BackendServiceContext {

    private final Logger logger = LoggerFactory.getLogger(BackendServiceContext.class);
    private static BackendService ownBackendService;
    private Map<String, BackendService> backendServiceList = new HashMap<>();

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public BackendService getNativeBackendService(){
        logger.info("Backend Service authentication initialization");
        ownBackendService = new BackendService();
        ownBackendService.setName("Patient");
        ownBackendService.setIp("0");
        ownBackendService.setPort(8082);
        ownBackendService.setRegNumber(UUID.randomUUID());
        return ownBackendService;
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public Map<String, BackendService> getRegistredBackendService(){
        return backendServiceList;
    }
}
