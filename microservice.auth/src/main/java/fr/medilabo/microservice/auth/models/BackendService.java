package fr.medilabo.microservice.auth.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "backend_services")
public class BackendService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Empty Name")
    private String name;
    @NotBlank(message = "Empty ip")
    private String ip;
    @NotNull(message = "Null port")
    private int port;
    @NotNull(message = "Null regNumber")
    @Column(name = "reg_number")
    private UUID regNumber;

}
