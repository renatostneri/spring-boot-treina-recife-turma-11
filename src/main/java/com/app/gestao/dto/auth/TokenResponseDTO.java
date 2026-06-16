package com.app.gestao.dto.auth;

public record TokenResponseDTO(String token) {

    public static TokenResponseDTO fromEntity(String token) {
        return new TokenResponseDTO(token);
    }
}


