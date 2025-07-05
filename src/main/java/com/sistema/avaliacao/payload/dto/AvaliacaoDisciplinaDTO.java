package com.sistema.avaliacao.payload.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AvaliacaoDisciplinaDTO extends AvaliacaoDTO {

    private DisciplinaDTO disciplina;
    private int notaConteudo;
    private int notaCargaTrabalho;
    private int notaInfraestrutura;

    public double getMediaNotas() { return (notaConteudo + notaCargaTrabalho + notaInfraestrutura) / 3.0; }

}
