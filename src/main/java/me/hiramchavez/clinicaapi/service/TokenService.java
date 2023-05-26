package me.hiramchavez.clinicaapi.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import me.hiramchavez.clinicaapi.Model.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.secret}")
    private String apiSecrete;

    public String generarToken(Usuario usuario) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecrete);
            return JWT.create()
              .withIssuer("clinicapp")
              .withSubject(usuario.getLogin())
              .withClaim("id", usuario.getId())
              .withExpiresAt(generarFechaExpiracion())
              .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new RuntimeException();
        }
    }

    public String getSubject(String token) {
        DecodedJWT verifier = null;

        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecrete);
            verifier = JWT.require(algorithm)
              .withIssuer("clinicapp")
              .build()
              .verify(token);

            verifier.getSubject();
        } catch (JWTVerificationException exception){
            throw new RuntimeException("Token JWT inválido o expirado!");
        }

        if (verifier.getSubject() == null)
            throw new RuntimeException("Verifier válido");

        return verifier.getSubject();
    }

    private Instant generarFechaExpiracion() {
        return LocalDateTime.now().plusDays(2).toInstant(ZoneOffset.of("-05:00"));
    }
}
