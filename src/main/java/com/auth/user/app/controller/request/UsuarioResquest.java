package com.auth.user.app.controller.request;

public record UsuarioResquest(
        String login,
        String senha,
        String permissao
) {
}
