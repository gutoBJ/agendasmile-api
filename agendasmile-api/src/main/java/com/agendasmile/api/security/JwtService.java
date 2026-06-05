package com.agendasmile.api.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;          // ← import!
import com.agendasmile.api.entity.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class JwtService {

    @Value("${api.security.token.secret}")
    private String secret;

    @Value("${api.security.token.expiration}")
    private Long expiration;

    // Gera o token JWT
    public String gerarToken(Usuario usuario) {
        return JWT.create()
                .withSubject(usuario.getEmail())
                .withClaim("id", usuario.getId())
                .withClaim("perfil", usuario.getPerfil().name())
                .withExpiresAt(dataExpiracao())
                .sign(Algorithm.HMAC256(secret));
    }

    // Valida o token e retorna o email
    public String validarToken(String token) {     // ← de volta!
        try {
            return decodificarToken(token).getSubject();
        } catch (JWTVerificationException e) {
            throw new IllegalArgumentException("Token inválido ou expirado!");
        }
    }

    // Extrai o perfil do token
    public String extrairPerfil(String token) {
        return decodificarToken(token)
                .getClaim("perfil")
                .asString();
    }

    // Extrai o id do token
    public Long extrairId(String token) {
        return decodificarToken(token)
                .getClaim("id")
                .asLong();
    }

    // ─── Métodos privados ─────────────────────────────────────────

    private DecodedJWT decodificarToken(String token) {
        return JWT.require(Algorithm.HMAC256(secret))
                .build()
                .verify(token);
    }

    private Instant dataExpiracao() {
        return Instant.now().plusMillis(expiration);
    }
}
