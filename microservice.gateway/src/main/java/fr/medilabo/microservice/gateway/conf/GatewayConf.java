package fr.medilabo.microservice.gateway.conf;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

@Configuration
public class GatewayConf {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder){
        return builder.routes()
                .route("PatientInfo - Get All", r -> r.path("/patient")
                        .and().method(HttpMethod.GET)
                        .uri("http://localhost:8081/"))
                .route("PatientInfo - Get By Id", r -> r.path("/patient/{id}")
                        .and().method(HttpMethod.GET)
                        .uri("http://localhost:8081/"))
                .route("PatientInfo - Create Patient", r -> r.path("/patient")
                        .and().method(HttpMethod.POST)
                        .uri("http://localhost:8081/"))
                .route("PatientInfo - Update Patient", r -> r.path("/patient")
                        .and().method(HttpMethod.PUT)
                        .uri("http://localhost:8081/"))
                .route("PatientInfo - Delete Patient", r -> r.path("/patient")
                        .and().method(HttpMethod.DELETE)
                        .uri("http://localhost:8081/"))
                .build();
    }
}
