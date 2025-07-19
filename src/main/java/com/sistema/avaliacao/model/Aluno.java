package com.sistema.avaliacao.model;

import com.sistema.avaliacao.enums.SituacaoAluno;
import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@PrimaryKeyJoinColumn(name = "id")
public class Aluno extends Usuario {

    @Column(unique = true, nullable = false)
    private String matriculaAcademica;

    private String matriz;
    private int periodoReferencia;

    @Enumerated(EnumType.STRING)
    private SituacaoAluno situacaoAluno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_aluno")
    @JsonIgnore
    private Curso curso;

    @Override
    public String getUsername() {
        return matriculaAcademica;
    }
}