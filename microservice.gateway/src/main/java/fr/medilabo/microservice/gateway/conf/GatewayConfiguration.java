package fr.medilabo.microservice.gateway.conf;

import fr.medilabo.microservice.gateway.enums.RoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

@Configuration
public class GatewayConfiguration {

    private VerifyTokenFilter verifyOrganisateur = new VerifyTokenFilter();

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder){
        return builder.routes()
                .route("User Auth", r -> r.path("/auth/**")
                        .uri("http://localhost:8083"))
                .route("PatientInfo", r -> r.path("/patient", "/patient/**")
                        .filters(f ->  f.filter(verifyOrganisateur))
                        .uri("http://localhost:8081/"))
                .route("PatientNote", r -> r.path("/notes", "/notes/**")
                        .filters(f ->  f.filter(verifyOrganisateur))
                        .uri("http://localhost:8082/"))
                .route("PatientDanger", r -> r.path("/risques", "/risques/**")
                        .filters(f ->  f.filter(verifyOrganisateur))
                        .uri("http://localhost:8084/"))
                .build();
    }

    @Bean
    public CorsWebFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("http://localhost:4200");
        config.addAllowedMethod("*");
        config.addAllowedHeader("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return new CorsWebFilter(source);
    }
}
