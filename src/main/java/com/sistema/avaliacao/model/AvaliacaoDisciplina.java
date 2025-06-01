package com.sistema.avaliacao.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AvaliacaoDisciplina extends Avaliacao {

    @ManyToOne
    @JoinColumn(name = "disciplina_avaliacao")
    private Disciplina disciplina;

    private int notaConteudo;
    private int notaCargaTrabalho;
    private int notaInfraestrutura;

    @Override
    @Transient
    public double getMediaNotas() {
        return (notaConteudo + notaCargaTrabalho + notaInfraestrutura) / 3.0;
    }
}