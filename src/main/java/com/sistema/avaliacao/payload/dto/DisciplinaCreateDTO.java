// src/main/java/com/sistema/avaliacao/payload/dto/DisciplinaCreateDTO.java

package com.sistema.avaliacao.payload.dto;

import lombok.Data;

@Data
public class DisciplinaCreateDTO {
    private String codigo;
    private String nome;
    private String semestre;
    private String descricao;
    private int cargaHoraria;
    private Long cursoId;       // <-- Receberemos o ID do curso
    private Long professorId;   // <-- Receberemos o ID do professor
}