package com.sistema.avaliacao.payload.dto;

import com.sistema.avaliacao.model.Professor;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DisciplinaDTO {

    @Id
    private String codigo;
    private String nome;
    private String curso;
    private String semestre;
    private ProfessorDTO professor;
    private int cargaHoraria;

}
