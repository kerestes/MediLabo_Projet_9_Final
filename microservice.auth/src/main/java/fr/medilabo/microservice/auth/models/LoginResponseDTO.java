package fr.medilabo.microservice.auth.models;

public record LoginResponseDTO(String token, String role) {
}
