package com.treinarecife.br.projeto.usuarios.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.treinarecife.br.projeto.usuarios.model.dto.UsuarioCreateDTO;
import com.treinarecife.br.projeto.usuarios.service.UsuarioService;

@RestController
@RequestMapping("usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public Long create(@RequestBody UsuarioCreateDTO dto) {
        var usuarioCriado = usuarioService.inserir(dto);
        return usuarioCriado.getId();
    }

}
