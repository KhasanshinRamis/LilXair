package web.hair.api_api.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class OrderDTO {

    private Long orderId;

    @Size(max = 255)
    private String address;

    private OffsetDateTime orderDate;

    private OffsetDateTime deadline;

    @Size(max = 255)
    private String status;

    private OffsetDateTime deliveryDate;

    private String comment;

    @NotNull
    private Long client;

    @NotNull
    private Long courier;

}
