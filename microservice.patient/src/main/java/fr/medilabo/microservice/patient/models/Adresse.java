package fr.medilabo.microservice.patient.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "adresses")
public class Adresse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String adresse;
    @Column(nullable = false)
    private String ville;
    @Column(name = "code_postal", nullable = false)
    private String codePostal;
}
