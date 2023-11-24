package web.hair.api_api.model;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ClientDTO {

    private Long clientId;

    @Size(max = 255)
    private String name;

    @Size(max = 255)
    private String surname;

    private String phone;

    @Size(max = 255)
    private String email;

}
