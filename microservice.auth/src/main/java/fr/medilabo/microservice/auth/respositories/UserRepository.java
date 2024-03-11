package fr.medilabo.microservice.auth.respositories;

import fr.medilabo.microservice.auth.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    public Optional<User> findOneByEmail(String email);
}
