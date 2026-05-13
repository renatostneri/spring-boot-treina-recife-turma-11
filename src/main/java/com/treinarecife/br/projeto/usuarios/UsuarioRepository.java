package com.treinarecife.br.projeto.usuarios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.treinarecife.br.projeto.usuarios.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

}
