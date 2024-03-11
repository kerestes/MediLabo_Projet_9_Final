package fr.medilabo.microservice.auth.enums;

public enum RoleEnum {
    ORGANISATEUR("ORGANISATEUR"),
    PRATICIEN("PRATICIEN");

    private String role;

    RoleEnum(String role){
        this.role = role;
    }

    public String getRole(){
        return this.role;
    }

}
