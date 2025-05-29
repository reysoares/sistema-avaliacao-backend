package com.sistema.avaliacao.model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvaliacaoDisciplina extends Avaliacao {

    private int notaConteudo;
    private int notaCargaTrabalho;
    private int notaInfraestrutura;

}
