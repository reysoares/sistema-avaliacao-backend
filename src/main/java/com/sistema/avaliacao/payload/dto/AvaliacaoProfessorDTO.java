package com.sistema.avaliacao.payload.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvaliacaoProfessorDTO extends AvaliacaoDTO {

    private ProfessorDTO professor;
    private int notaDidatica;
    private int notaDominioConteudo;
    private int notaInteracaoAlunos;

    public double getMediaNotas() {
        return (notaDidatica + notaDominioConteudo + notaInteracaoAlunos) / 3.0;
    }

}
