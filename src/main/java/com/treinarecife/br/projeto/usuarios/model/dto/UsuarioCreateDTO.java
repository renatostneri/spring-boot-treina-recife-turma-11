package com.treinarecife.br.projeto.usuarios.model.dto;

import java.time.LocalDate;

import com.treinarecife.br.projeto.usuarios.model.enums.StatusUsuario;

import lombok.Data;

@Data
public class UsuarioCreateDTO {
    private String nome;
    private String cpf;
    private String email;
    private String senha;
    private LocalDate dataNascimento;
    private StatusUsuario status;
}
