package com.treinarecife.br.projeto.usuarios.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.treinarecife.br.projeto.usuarios.UsuarioRepository;
import com.treinarecife.br.projeto.usuarios.model.Usuario;
import com.treinarecife.br.projeto.usuarios.model.dto.UsuarioCreateDTO;
import com.treinarecife.br.projeto.usuarios.model.enums.StatusUsuario;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario inserir(UsuarioCreateDTO usuarioDTO) {
        var novoUsuario = new Usuario(usuarioDTO);

        return usuarioRepository.save(novoUsuario);
    }

}
