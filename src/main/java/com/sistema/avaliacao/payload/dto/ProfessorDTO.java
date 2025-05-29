package com.sistema.avaliacao.payload.dto;

import com.sistema.avaliacao.model.Disciplina;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfessorDTO {

    @Id
    private String matriculaFuncional;
    private String nome;
    private String email;
    private String departamento;
    private List<DisciplinaDTO> disciplinas;
}
