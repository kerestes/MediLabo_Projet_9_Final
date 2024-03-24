package fr.medilabo.microservice.patient.filtres;

import fr.medilabo.microservice.patient.models.BackendService;
import fr.medilabo.microservice.patient.services.BackendServiceService;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Component
public class AuthBackendFiltre implements Filter {
    private final Logger logger = LoggerFactory.getLogger(AuthBackendFiltre.class);
    @Autowired
    private BackendServiceService service;
    @Autowired
    private Map<String, BackendService> registratedBackendService;


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        logger.info("Call AuthBackendFiltre");

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String nameService = req.getHeader("Backend-name");
        String regNumber = req.getHeader("Backend-reg-number");

        logger.info("Service: " + nameService + " - Reg Number: " + regNumber);
        
        if(nameService != null && regNumber != null){
            if(registratedBackendService.containsKey(nameService)){
                logger.info("Backend filter name registred");
                BackendService externalBackendService = registratedBackendService.get(nameService);
                if(externalBackendService.getRegNumber().toString().equals(regNumber)){
                    logger.info("Name and RegNumber registered and check");
                    chain.doFilter(request, response);
                }
            }else{
                Optional<BackendService> optionalExternalBackendService = service.findBackendServiceByName(nameService);
                if(optionalExternalBackendService.isPresent()) {
                    if (optionalExternalBackendService.get().getRegNumber().toString().equals(regNumber)) {
                        logger.info("Name and RegNumber not registered but check");
                        registratedBackendService.put(optionalExternalBackendService.get().getName(), optionalExternalBackendService.get());
                        chain.doFilter(request, response);
                    }
                }
            }
        }
        res.setStatus(HttpStatus.UNAUTHORIZED.value());
    }
}
