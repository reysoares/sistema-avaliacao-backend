package com.sistema.avaliacao.payload.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvaiacaoDisciplinaDTO extends AvaliacaoDTO{

    private int notaConteudo;
    private int notaCargaTrabalho;
    private int notaInfraestrutura;

}
