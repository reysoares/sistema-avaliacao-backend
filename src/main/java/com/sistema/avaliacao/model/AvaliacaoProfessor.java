package com.sistema.avaliacao.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Transient;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AvaliacaoProfessor extends Avaliacao {

    @ManyToOne
    @JoinColumn(name = "professor_avaliacao")
    private Professor professor;

    private int notaDidatica;
    private int notaDominioConteudo;
    private int notaInteracaoAlunos;

    @Override
    @Transient
    public double getMediaNotas() {
        return (notaDidatica + notaDominioConteudo + notaInteracaoAlunos) / 3.0;
    }
}
