package com.sistema.avaliacao.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Disciplina {

    @Id
    private String codigo;

    private String nome;
    private String curso;
    private String semestre;
    private int cargaHoraria;

    @ManyToOne
    @JoinColumn(name = "professor_disciplina")
    private Professor professor;

    @OneToMany(mappedBy = "disciplina", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List <AvaliacaoDisciplina> avaliacoesDisciplina;
}
