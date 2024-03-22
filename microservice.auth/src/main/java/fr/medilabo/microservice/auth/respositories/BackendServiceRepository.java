package fr.medilabo.microservice.auth.respositories;

import fr.medilabo.microservice.auth.models.BackendService;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface BackendServiceRepository extends CrudRepository<BackendService, Long> {

    public Optional<BackendService> findOneByName(String name);
}
