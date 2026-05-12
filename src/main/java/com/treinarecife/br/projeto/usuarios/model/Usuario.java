package com.treinarecife.br.projeto.usuarios.model;

import java.time.LocalDate;

import com.treinarecife.br.projeto.usuarios.model.enums.StatusUsuario;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Usuario {
    private Long id;
    private String nome;
    private String cpf;
    private String email;
    private String senha;
    private LocalDate dataNascimento;
    private StatusUsuario status;
}
