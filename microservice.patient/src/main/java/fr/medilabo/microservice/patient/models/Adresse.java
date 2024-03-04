package fr.medilabo.microservice.patient.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "adresses")
public class Adresse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Il faut remplir l'adresse")
    @Column(nullable = false)
    private String adresse;
    @NotBlank(message = "Il faut remplir la ville")
    @Column(nullable = false)
    private String ville;
    @NotBlank(message = "Il faut remplir le code postal")
    @Column(name = "code_postal", nullable = false)
    private String codePostal;
}
