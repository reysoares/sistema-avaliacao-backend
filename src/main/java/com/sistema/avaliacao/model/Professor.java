package com.sistema.avaliacao.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Professor {

    @Id
    private String matriculaFuncional;
    private String nome;
    private String email;
    private String departamento;
    @OneToMany(mappedBy = "professor")
    private List <Disciplina> disciplinas;

}
