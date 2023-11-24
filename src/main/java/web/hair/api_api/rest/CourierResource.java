package web.hair.api_api.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import web.hair.api_api.model.CourierDTO;
import web.hair.api_api.service.CourierService;


@RestController
@RequestMapping(value = "/api/couriers", produces = MediaType.APPLICATION_JSON_VALUE)
public class CourierResource {

    private final CourierService courierService;

    public CourierResource(final CourierService courierService) {
        this.courierService = courierService;
    }

    @GetMapping
    public ResponseEntity<List<CourierDTO>> getAllCouriers() {
        return ResponseEntity.ok(courierService.findAll());
    }

    @GetMapping("/{courierId}")
    public ResponseEntity<CourierDTO> getCourier(
            @PathVariable(name = "courierId") final Long courierId) {
        return ResponseEntity.ok(courierService.get(courierId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createCourier(@RequestBody @Valid final CourierDTO courierDTO) {
        final Long createdCourierId = courierService.create(courierDTO);
        return new ResponseEntity<>(createdCourierId, HttpStatus.CREATED);
    }

    @PutMapping("/{courierId}")
    public ResponseEntity<Long> updateCourier(
            @PathVariable(name = "courierId") final Long courierId,
            @RequestBody @Valid final CourierDTO courierDTO) {
        courierService.update(courierId, courierDTO);
        return ResponseEntity.ok(courierId);
    }

    @DeleteMapping("/{courierId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteCourier(
            @PathVariable(name = "courierId") final Long courierId) {
        courierService.delete(courierId);
        return ResponseEntity.noContent().build();
    }

}
