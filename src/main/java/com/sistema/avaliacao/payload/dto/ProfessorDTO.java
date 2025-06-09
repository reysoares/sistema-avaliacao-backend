package com.sistema.avaliacao.payload.dto;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfessorDTO {

    @Id
    private String matriculaFuncional;
    private String nome;
    private String email;
    private String departamento;
}
