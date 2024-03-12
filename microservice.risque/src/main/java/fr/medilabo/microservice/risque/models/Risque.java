package fr.medilabo.microservice.risque.models;

import fr.medilabo.microservice.risque.enums.RisqueEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Risque {
    private Long patId;
    private String patient;
    private String risque;
}
