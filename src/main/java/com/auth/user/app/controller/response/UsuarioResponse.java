package com.auth.user.app.controller.response;

public record UsuarioResponse(
        Long id,
        String login,
        String permissao
) {
}
