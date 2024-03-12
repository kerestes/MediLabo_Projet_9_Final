package fr.medilabo.microservice.risque.models;

import java.util.Date;

public record Patient (Long id, String prenom, String nom, Date dateNaissance, char genre) {
}
