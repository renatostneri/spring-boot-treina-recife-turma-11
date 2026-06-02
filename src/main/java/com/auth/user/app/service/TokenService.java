package com.auth.user.app.service;

import com.auth.user.app.model.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {
    // Injeta a assinatura secreta definida no application.properties
    @Value("${api.security.token.secret}")
    private String secret;

    private static final String ISSUER = "treina-recife-api";

    /**
     * Gera o token assinado digitalmente após o login com sucesso.
     */
    public String gerarToken(Usuario usuario) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer(ISSUER)
                    .withSubject(usuario.getEmail()) // Identificador do usuário (e-mail)
                    .withClaim("role", usuario.getPermissao()) // Injeta o perfil direto no Payload do JWT
                    .withExpiresAt(gerarDataExpiracao()) // Define o tempo de vida do token
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao gerar token JWT", exception);
        }
    }

    /**
     * Valida se o token está íntegro e dentro do prazo, retornando o e-mail (Subject).
     */
    public String validarToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build()
                    .verify(token)
                    .getSubject(); // Se for válido, retorna o e-mail do dono do token
        } catch (JWTVerificationException exception) {
            return ""; // Retorna string vazia se o token estiver corrompido ou expirado
        }
    }

    private Instant gerarDataExpiracao() {
        // Define que o token vai expirar em exatamente 2 horas (fuso horário de Recife/Brasília)
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
