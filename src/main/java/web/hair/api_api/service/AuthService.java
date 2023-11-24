package web.hair.api_api.service;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import ch.qos.logback.core.util.Duration;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import web.hair.api_api.domain.Courier;
import web.hair.api_api.model.LoginDTO;
import web.hair.api_api.repos.CourierRepository;

@Service
public class AuthService {

    private final CourierRepository courierRepository;

    private final KeyPair keyPair;

    private final long expirationTimeMS = Duration.buildByHours(8).getMilliseconds();

    public AuthService(CourierRepository courierRepository) throws NoSuchAlgorithmException {
        this.courierRepository = courierRepository;
        var generator = KeyPairGenerator.getInstance("RSA");
        var random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(0L);
        generator.initialize(2048, random);
        keyPair = generator.generateKeyPair();
    }

    public String login(LoginDTO loginDTO){
         Courier courier = courierRepository.findByLogin(loginDTO.getLogin())
        .filter(c->c.getPassword().equals(loginDTO.getPassword()))
        .orElseThrow(()->new BadCredentialsException("Неверный логин или пароль дурак"));

        return Jwts.builder()
                .setSubject(courier.getLogin())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTimeMS))
                .signWith(keyPair.getPrivate())
                .compact();
    }

    public UsernamePasswordAuthenticationToken validate(String jwt)
    {
        try {
            var claims = Jwts.parserBuilder()
                    .setSigningKey(keyPair.getPublic())
                    .build()
                    .parseClaimsJws(jwt);
            var sub = claims
                    .getBody()
                    .getSubject();
            return new UsernamePasswordAuthenticationToken(
                    Long.valueOf(sub), null,
                    Collections.singleton(new SimpleGrantedAuthority("ROLE_COURIER")));
        } catch (JwtException | IllegalArgumentException e) {
            return null;
        }
    }
}
