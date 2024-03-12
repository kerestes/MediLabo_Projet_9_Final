package fr.medilabo.microservice.note.medecin.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Getter
@Setter
@Document("notes")
public class Note {

    @MongoId
    private String _id;
    private Long patId;
    private String patient;
    private String note;
}
