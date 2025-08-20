package com.sistema.avaliacao.payload.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CursoDTO {
    private Long id;
    private String nome;
    private ProfessorDTO coordenador;
}
