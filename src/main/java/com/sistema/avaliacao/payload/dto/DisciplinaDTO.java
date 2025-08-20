package com.sistema.avaliacao.payload.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class DisciplinaDTO {

    private String codigo;
    private String nome;
    private CursoDTO curso;
    private String semestre;
    private String descricao;
    private int cargaHoraria;
    private ProfessorDTO professor;

}
