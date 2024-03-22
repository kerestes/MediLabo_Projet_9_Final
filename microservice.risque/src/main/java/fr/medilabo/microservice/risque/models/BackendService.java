package fr.medilabo.microservice.risque.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BackendService {

    private Long id;
    private String name;
    private String ip;
    private int port;
    private UUID regNumber;

}
