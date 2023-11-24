package web.hair.api_api.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import web.hair.api_api.domain.OrderItem;


public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
