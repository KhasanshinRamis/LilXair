package web.hair.api_api.service;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import web.hair.api_api.domain.Menu;
import web.hair.api_api.domain.Order;
import web.hair.api_api.domain.OrderItem;
import web.hair.api_api.model.OrderItemDTO;
import web.hair.api_api.repos.MenuRepository;
import web.hair.api_api.repos.OrderItemRepository;
import web.hair.api_api.repos.OrderRepository;
import web.hair.api_api.util.NotFoundException;


@Service
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final MenuRepository menuRepository;

    public OrderItemService(final OrderItemRepository orderItemRepository,
            final OrderRepository orderRepository, final MenuRepository menuRepository) {
        this.orderItemRepository = orderItemRepository;
        this.orderRepository = orderRepository;
        this.menuRepository = menuRepository;
    }

    public List<OrderItemDTO> findAll() {
        final List<OrderItem> orderItems = orderItemRepository.findAll(Sort.by("orderItemId"));
        return orderItems.stream()
                .map(orderItem -> mapToDTO(orderItem, new OrderItemDTO()))
                .toList();
    }

    public OrderItemDTO get(final Long orderItemId) {
        return orderItemRepository.findById(orderItemId)
                .map(orderItem -> mapToDTO(orderItem, new OrderItemDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final OrderItemDTO orderItemDTO) {
        final OrderItem orderItem = new OrderItem();
        mapToEntity(orderItemDTO, orderItem);
        return orderItemRepository.save(orderItem).getOrderItemId();
    }

    public void update(final Long orderItemId, final OrderItemDTO orderItemDTO) {
        final OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(orderItemDTO, orderItem);
        orderItemRepository.save(orderItem);
    }

    public void delete(final Long orderItemId) {
        orderItemRepository.deleteById(orderItemId);
    }

    private OrderItemDTO mapToDTO(final OrderItem orderItem, final OrderItemDTO orderItemDTO) {
        orderItemDTO.setOrderItemId(orderItem.getOrderItemId());
        orderItemDTO.setAmount(orderItem.getAmount());
        orderItemDTO.setOrder(orderItem.getOrder() == null ? null : orderItem.getOrder().getOrderId());
        orderItemDTO.setMenu(orderItem.getMenu() == null ? null : orderItem.getMenu().getMenuId());
        return orderItemDTO;
    }

    private OrderItem mapToEntity(final OrderItemDTO orderItemDTO, final OrderItem orderItem) {
        orderItem.setAmount(orderItemDTO.getAmount());
        final Order order = orderItemDTO.getOrder() == null ? null : orderRepository.findById(orderItemDTO.getOrder())
                .orElseThrow(() -> new NotFoundException("order not found"));
        orderItem.setOrder(order);
        final Menu menu = orderItemDTO.getMenu() == null ? null : menuRepository.findById(orderItemDTO.getMenu())
                .orElseThrow(() -> new NotFoundException("menu not found"));
        orderItem.setMenu(menu);
        return orderItem;
    }

}
