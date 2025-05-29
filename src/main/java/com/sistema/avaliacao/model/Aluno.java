package com.sistema.avaliacao.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity (name = "alunos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Aluno {

    @Id
    private String matriculaAcademica;
    private String nome;
    private String senha;
    private String email;
    private String curso;
    @OneToMany (mappedBy = "aluno")
    private List<Avaliacao> avaliacoes;

}