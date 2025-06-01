package com.sistema.avaliacao.payload.dto;

import com.sistema.avaliacao.model.AvaliacaoDisciplina;
import com.sistema.avaliacao.model.Professor;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DisciplinaDTO {

    @Id
    private String codigo;
    private String nome;
    private String curso;
    private String semestre;
    private int cargaHoraria;
    private ProfessorDTO professor;
    private List <AvaliacaoDisciplinaDTO> avaliacoesDisciplina;

}
