package fr.medilabo.microservice.auth.errors;

public class BadCredentialsException extends RuntimeException{

    public BadCredentialsException(String message){
        super(message);
    }
}
