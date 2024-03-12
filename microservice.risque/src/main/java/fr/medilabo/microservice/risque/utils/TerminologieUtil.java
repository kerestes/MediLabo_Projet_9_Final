package fr.medilabo.microservice.risque.utils;

import fr.medilabo.microservice.risque.enums.RisqueEnum;
import fr.medilabo.microservice.risque.models.Note;
import fr.medilabo.microservice.risque.models.Patient;
import fr.medilabo.microservice.risque.models.Risque;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class TerminologieUtil {

    private final String[] TERMINOLOGIE = {
            "Hémoglobine A1C",
            "Microalbumine",
            "Taille",
            "Poids",
            "Poid",
            "Fumeur",
            "Fumeuse",
            "Anormal",
            "Cholestérol",
            "Vertiges",
            "Rechute",
            "Réaction",
            "Anticorps"
    };

    public List<Risque> calculeRisque(List<Note> notes, List<Patient> patients){
        Map<Long, Long> risquePoints = new HashMap<>();
        List<Risque> risques = new ArrayList<>();

        notes.forEach(note -> {
            if(risquePoints.containsKey(note.patId())){
                risquePoints.put(note.patId(), risquePoints.get(note.patId()) + verifierTerminologie(note));
            } else {
                risquePoints.put(note.patId(), verifierTerminologie(note));
            }
        });

        risquePoints.entrySet().forEach(risquePoint -> {
            Risque risque = new Risque();
            Patient patient = patients.stream().filter(p -> p.id() == risquePoint.getKey()).findFirst().get();
            int age = Period.between(patient.dateNaissance().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate(), LocalDate.now()).getYears();

            risque.setPatId(patient.id());
            risque.setPatient(patient.nom());
             if((age <= 30 && Character.compare(patient.genre(), 'M') == 0 && risquePoint.getValue() >=5) ||
                    (age <= 30 && Character.compare(patient.genre(), 'F') == 0 && risquePoint.getValue() >=7) ||
                    (age > 30 && risquePoint.getValue() >=8)){
                risque.setRisque(RisqueEnum.EARLYONSET.getRisque());
            } else if((age <= 30 && Character.compare(patient.genre(), 'M') == 0 && risquePoint.getValue() >=3) ||
                    (age <= 30 && Character.compare(patient.genre(), 'F') == 0 && risquePoint.getValue() >=4) ||
                    (age > 30 && risquePoint.getValue() >=6)){
                risque.setRisque(RisqueEnum.INDANGER.getRisque());
            } else if(age > 30 && risquePoint.getValue() >= 2 ) {
                risque.setRisque(RisqueEnum.BORDERLINE.getRisque());
            } else {
                 risque.setRisque(RisqueEnum.NONE.getRisque());
             }
             risques.add(risque);
        });
        return risques;
    }

    private Long verifierTerminologie(Note note){
        return Arrays.stream(TERMINOLOGIE).filter(f -> note.note().toLowerCase().contains(f.toLowerCase())).count();
    }
}
