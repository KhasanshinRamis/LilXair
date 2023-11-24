package web.hair.api_api.service;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import web.hair.api_api.domain.Client;
import web.hair.api_api.domain.Courier;
import web.hair.api_api.domain.Order;
import web.hair.api_api.model.OrderDTO;
import web.hair.api_api.repos.ClientRepository;
import web.hair.api_api.repos.CourierRepository;
import web.hair.api_api.repos.OrderRepository;
import web.hair.api_api.util.NotFoundException;


@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ClientRepository clientRepository;
    private final CourierRepository courierRepository;

    public OrderService(final OrderRepository orderRepository,
            final ClientRepository clientRepository, final CourierRepository courierRepository) {
        this.orderRepository = orderRepository;
        this.clientRepository = clientRepository;
        this.courierRepository = courierRepository;
    }

    public List<OrderDTO> findAll() {
        final List<Order> orders = orderRepository.findAll(Sort.by("orderId"));
        return orders.stream()
                .map(order -> mapToDTO(order, new OrderDTO()))
                .toList();
    }

    public OrderDTO get(final Long orderId) {
        return orderRepository.findById(orderId)
                .map(order -> mapToDTO(order, new OrderDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final OrderDTO orderDTO) {
        final Order order = new Order();
        mapToEntity(orderDTO, order);
        return orderRepository.save(order).getOrderId();
    }

    public void update(final Long orderId, final OrderDTO orderDTO) {
        final Order order = orderRepository.findById(orderId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(orderDTO, order);
        orderRepository.save(order);
    }

    public void delete(final Long orderId) {
        orderRepository.deleteById(orderId);
    }

    private OrderDTO mapToDTO(final Order order, final OrderDTO orderDTO) {
        orderDTO.setOrderId(order.getOrderId());
        orderDTO.setAddress(order.getAddress());
        orderDTO.setOrderDate(order.getOrderDate());
        orderDTO.setDeadline(order.getDeadline());
        orderDTO.setStatus(order.getStatus());
        orderDTO.setDeliveryDate(order.getDeliveryDate());
        orderDTO.setComment(order.getComment());
        orderDTO.setClient(order.getClient() == null ? null : order.getClient().getClientId());
        orderDTO.setCourier(order.getCourier() == null ? null : order.getCourier().getCourierId());
        return orderDTO;
    }

    private Order mapToEntity(final OrderDTO orderDTO, final Order order) {
        order.setAddress(orderDTO.getAddress());
        order.setOrderDate(orderDTO.getOrderDate());
        order.setDeadline(orderDTO.getDeadline());
        order.setStatus(orderDTO.getStatus());
        order.setDeliveryDate(orderDTO.getDeliveryDate());
        order.setComment(orderDTO.getComment());
        final Client client = orderDTO.getClient() == null ? null : clientRepository.findById(orderDTO.getClient())
                .orElseThrow(() -> new NotFoundException("client not found"));
        order.setClient(client);
        final Courier courier = orderDTO.getCourier() == null ? null : courierRepository.findById(orderDTO.getCourier())
                .orElseThrow(() -> new NotFoundException("courier not found"));
        order.setCourier(courier);
        return order;
    }

}
