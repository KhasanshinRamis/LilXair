package web.hair.api_api.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import web.hair.api_api.domain.Client;


public interface ClientRepository extends JpaRepository<Client, Long> {

    boolean existsByEmailIgnoreCase(String email);

}
