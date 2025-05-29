package com.sistema.avaliacao.payload.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvaliacaoProfessorDTO extends AvaliacaoDTO {

    private int notaDidatica;
    private int notaDominioConteudo;
    private int notaInteracaoAlunos;

}
