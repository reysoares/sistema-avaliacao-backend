package com.sistema.avaliacao.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Disciplina {

    @Id
    @EqualsAndHashCode.Include
    private String codigo;

    private String nome;
    private String semestre;
    private String descricao;
    private int cargaHoraria;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professor_disciplina")
    @JsonIgnore
    private Professor professor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_disciplina")
    @JsonIgnore
    private Curso curso;
}
