package com.sistema.avaliacao.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@PrimaryKeyJoinColumn(name = "id")
public class AvaliacaoDisciplina extends Avaliacao {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "disciplina_avaliacao")
    @JsonIgnore
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