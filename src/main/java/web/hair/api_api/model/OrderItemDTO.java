package web.hair.api_api.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class OrderItemDTO {

    private Long orderItemId;

    private Integer amount;

    @NotNull
    private Long order;

    @NotNull
    private Long menu;

}
