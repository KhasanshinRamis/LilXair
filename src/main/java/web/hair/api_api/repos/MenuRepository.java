package web.hair.api_api.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import web.hair.api_api.domain.Menu;


public interface MenuRepository extends JpaRepository<Menu, Long> {
}
