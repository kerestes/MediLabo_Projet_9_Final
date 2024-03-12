package fr.medilabo.microservice.risque.enums;

public enum RisqueEnum {
    NONE("None"),
    BORDERLINE("Borderline"),
    INDANGER("InDanger"),
    EARLYONSET("EarlyOnset");

    private String risque;

    RisqueEnum(String risque){
        this.risque = risque;
    }

    public String getRisque(){
        return this.risque;
    }
}
