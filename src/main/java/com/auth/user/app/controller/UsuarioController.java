package com.auth.user.app.controller;

import com.auth.user.app.controller.request.UsuarioResquest;
import com.auth.user.app.controller.response.UsuarioResponse;
import com.auth.user.app.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;


    @PostMapping
    public ResponseEntity<UsuarioResponse> create(@RequestBody UsuarioResquest request, UriComponentsBuilder uriBuilder){
        var usuario = usuarioService.create(request);
        var uri = uriBuilder.path("/usuario/{id}").buildAndExpand(usuario.getId()).toUri();
        return ResponseEntity.created(uri).body(usuario.toDTO());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> read(@PathVariable Long id){
        var usuario = usuarioService.findById(id);
        return ResponseEntity.ok().body(usuario.toDTO());
    }
}
