package web.hair.api_api.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import web.hair.api_api.domain.Order;


public interface OrderRepository extends JpaRepository<Order, Long> {
}
