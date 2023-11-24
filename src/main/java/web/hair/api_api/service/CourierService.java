package web.hair.api_api.service;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import web.hair.api_api.domain.Courier;
import web.hair.api_api.model.CourierDTO;
import web.hair.api_api.repos.CourierRepository;
import web.hair.api_api.util.NotFoundException;


@Service
public class CourierService  {

    private final CourierRepository courierRepository;

    public CourierService(final CourierRepository courierRepository) {
        this.courierRepository = courierRepository;
    }

    public List<CourierDTO> findAll() {
        final List<Courier> couriers = courierRepository.findAll(Sort.by("courierId"));
        return couriers.stream()
                .map(courier -> mapToDTO(courier, new CourierDTO()))
                .toList();
    }

    public CourierDTO get(final Long courierId) {
        return courierRepository.findById(courierId)
                .map(courier -> mapToDTO(courier, new CourierDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final CourierDTO courierDTO) {
        final Courier courier = new Courier();
        mapToEntity(courierDTO, courier);
        return courierRepository.save(courier).getCourierId();
    }

    public void update(final Long courierId, final CourierDTO courierDTO) {
        final Courier courier = courierRepository.findById(courierId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(courierDTO, courier);
        courierRepository.save(courier);
    }

    public void delete(final Long courierId) {
        courierRepository.deleteById(courierId);
    }

    private CourierDTO mapToDTO(final Courier courier, final CourierDTO courierDTO) {
        courierDTO.setCourierId(courier.getCourierId());
        courierDTO.setName(courier.getName());
        courierDTO.setSurname(courier.getSurname());
        courierDTO.setPhone(courier.getPhone());
        courierDTO.setLogin(courier.getLogin());
        courierDTO.setPassword(courier.getPassword());
        return courierDTO;
    }

    private Courier mapToEntity(final CourierDTO courierDTO, final Courier courier) {
        courier.setName(courierDTO.getName());
        courier.setSurname(courierDTO.getSurname());
        courier.setPhone(courierDTO.getPhone());
        courier.setLogin(courierDTO.getLogin());
        courier.setPassword(courierDTO.getPassword());
        return courier;
    }

    public boolean loginExists(final String login) {
        return courierRepository.existsByLoginIgnoreCase(login);
    }

}
