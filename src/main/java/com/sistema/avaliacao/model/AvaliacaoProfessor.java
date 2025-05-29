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
public class AvaliacaoProfessor extends Avaliacao {

    private int notaDidatica;
    private int notaDominioConteudo;
    private int notaInteracaoAlunos;

}
