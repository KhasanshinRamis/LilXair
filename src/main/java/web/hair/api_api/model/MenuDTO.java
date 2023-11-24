package web.hair.api_api.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class MenuDTO {

    private Long menuId;

    @NotNull
    @Size(max = 255)
    private String name;

    @Size(max = 255)
    private String price;

}
