package com.sistema.avaliacao.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Professor {

    @Id
    private String matriculaFuncional;
    private String nome;
    private String email;
    private String departamento;

    @OneToMany(mappedBy = "professor")
    private List <Disciplina> disciplinas;

    @OneToMany(mappedBy = "professor", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List  <AvaliacaoProfessor> avaliacoesProfessor;
}
