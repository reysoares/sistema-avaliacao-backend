package com.sistema.avaliacao.payload.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfessorDTO {

    private String matriculaFuncional;
    private String nome;
    private String emailInstitucional;
    private String departamento;
    private String unidadeEnsino;
    private String areaAtuacao;
    private LocalDate dataNascimento;
    private String imagem;
    private String perfilDescricao;

}
