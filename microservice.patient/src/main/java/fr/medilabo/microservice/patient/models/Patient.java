package fr.medilabo.microservice.patient.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "patients")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String prenom;
    @Column(nullable = false)
    private String nom;
    @Column(name = "date_de_naissance", nullable = false)
    private Date dateNaissance;
    @Column(nullable = false)
    private char genre;
    @ManyToOne
    @JoinColumn(nullable = true)
    private Adresse adress;
    @Column(nullable = true)
    private String telephone;

}
