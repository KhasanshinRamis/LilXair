package web.hair.api_api.rest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.AllArgsConstructor;
import web.hair.api_api.model.LoginDTO;
import web.hair.api_api.model.TokenDTO;
import web.hair.api_api.service.AuthService;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class AuthResource {
    private final AuthService authService;
    @PostMapping("/login")
    public TokenDTO login(@RequestBody LoginDTO loginDTO)
    {
        var token = authService.login(loginDTO);
        var response = new TokenDTO();
        response.setToken(token);
        return response;
    }
}
