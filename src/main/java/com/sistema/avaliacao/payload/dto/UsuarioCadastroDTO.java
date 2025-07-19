package com.sistema.avaliacao.payload.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UsuarioCadastroDTO {

    private String nome;
    private String matricula;
    private String emailInstitucional;
    private LocalDate dataNascimento;
    private String senha;

}
