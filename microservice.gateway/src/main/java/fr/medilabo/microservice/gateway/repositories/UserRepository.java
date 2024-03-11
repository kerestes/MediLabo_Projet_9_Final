package fr.medilabo.microservice.gateway.repositories;

import fr.medilabo.microservice.gateway.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
}
