package web.hair.api_api.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import web.hair.api_api.domain.Courier;
import java.util.List;



public interface CourierRepository extends JpaRepository<Courier, Long> {

    boolean existsByLoginIgnoreCase(String login);

    Optional<Courier> findByLogin(String login);

}
