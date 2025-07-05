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
public class AvaliacaoProfessor extends Avaliacao {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professor_avaliacao")
    @JsonIgnore
    private Professor professor;

    private int notaDidatica;
    private int notaDominioConteudo;
    private int notaInteracaoAlunos;

    @Override
    public double getMediaNotas() {
        return (notaDidatica + notaDominioConteudo + notaInteracaoAlunos) / 3.0;
    }
}
