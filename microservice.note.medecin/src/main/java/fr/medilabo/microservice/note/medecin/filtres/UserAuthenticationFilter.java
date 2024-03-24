package fr.medilabo.microservice.note.medecin.filtres;

import fr.medilabo.microservice.note.medecin.models.BackendService;
import fr.medilabo.microservice.note.medecin.models.security.UserDetailsImpl;
import fr.medilabo.microservice.note.medecin.services.BackendServiceService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Component
public class UserAuthenticationFilter extends OncePerRequestFilter {

    private final Logger logger = LoggerFactory.getLogger(UserAuthenticationFilter.class);
    @Autowired
    private BackendServiceService backendServiceService;
    @Autowired
    private Map<String, BackendService> registratedBackendService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        logger.info("Call UserAuthenticationFilter");
        String nameService = request.getHeader("Backend-name");
        String regNumber = request.getHeader("Backend-reg-number");
        logger.info("Service: " + nameService + " - Reg Number: " + regNumber);

        if (nameService != null && regNumber != null) {
            logger.info("Backend filter name registred");
            if(registratedBackendService.containsKey(nameService)){
                logger.info("Backend filter name registred");
                BackendService externalBackendService = registratedBackendService.get(nameService);
                if(externalBackendService.getRegNumber().toString().equals(regNumber)){
                    logger.info("Name and RegNumber registered and check");
                    registerAuthSpringSecurityContext(externalBackendService);
                }
            }else{
                Optional<BackendService> optionalExternalBackendService = backendServiceService.findBackendServiceByName(nameService);
                if(optionalExternalBackendService.isPresent()) {
                    if (optionalExternalBackendService.get().getRegNumber().toString().equals(regNumber)) {
                        logger.info("Name and RegNumber not registered but check");
                        registerAuthSpringSecurityContext(optionalExternalBackendService.get());
                    }
                }
            }
        }
        filterChain.doFilter(request, response);
    }


    private void registerAuthSpringSecurityContext (BackendService service) {
        UserDetailsImpl userDetails = new UserDetailsImpl(service);
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
