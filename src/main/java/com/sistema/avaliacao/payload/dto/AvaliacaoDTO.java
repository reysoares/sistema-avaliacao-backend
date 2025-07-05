package com.sistema.avaliacao.payload.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class AvaliacaoDTO {
    private Long id;
    private AlunoDTO aluno;
    private String comentario;
    private LocalDate data;

    public abstract double getMediaNotas();
}
