package fr.medilabo.microservice.auth.models;

import fr.medilabo.microservice.auth.enums.RoleEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@Entity
@Table(name = "users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String prenom;
    private String nom;
    private String email;
    private String password;
    @Transient
    private String confirmPassword;
    private RoleEnum role;
}
