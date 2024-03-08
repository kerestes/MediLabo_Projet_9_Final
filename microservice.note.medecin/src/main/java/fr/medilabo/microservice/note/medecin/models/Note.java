package fr.medilabo.microservice.note.medecin.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document("notes")
public class Note {

    private Long patId;
    private String patient;
    private String note;
}
