package fr.medilabo.microservice.gateway.services;

import fr.medilabo.microservice.gateway.models.User;
import fr.medilabo.microservice.gateway.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public User save(User user){ return repository.save(user);}

    public Optional<User> findById (String token){
        return repository.findById(token);
    }
}
