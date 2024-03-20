package fr.medilabo.microservice.patient.models;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "patients")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Il faut remplir le nom")
    @Column(nullable = false)
    private String nom;
    @NotBlank(message = "Il faut remplir le prenom")
    @Column(nullable = false)
    private String prenom;
    @NotNull(message = "Il faut remplir la date de naissance")
    @Column(name = "date_de_naissance", nullable = false)
    private Date dateNaissance;
    @NotNull(message = "Il faut remplir le genre")
    @Column(nullable = false)
    private char genre;
    @ManyToOne
    @JoinColumn(nullable = true)
    @Valid
    private Adresse adresse;
    @Column(nullable = true)
    private String telephone;

}
