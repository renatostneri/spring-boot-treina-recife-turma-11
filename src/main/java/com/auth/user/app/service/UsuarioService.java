package com.auth.user.app.service;

import com.auth.user.app.controller.request.UsuarioResquest;
import com.auth.user.app.controller.response.UsuarioResponse;
import com.auth.user.app.model.Usuario;
import com.auth.user.app.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Usuario create(UsuarioResquest usuarioDTO) {
        var senhaCodificada = passwordEncoder.encode(usuarioDTO.senha());
        Usuario usuario = new Usuario(usuarioDTO, senhaCodificada);
        return usuarioRepository.save(usuario);
    }

    public Usuario findById(Long id) {
        return usuarioRepository.getReferenceById(id);
    }
}
