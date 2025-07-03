package com.sistema.avaliacao.model;

import com.sistema.avaliacao.config.SituacaoAluno;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Aluno {

    @Id
    @EqualsAndHashCode.Include
    private String matriculaAcademica;

    private String nome;
    private String emailInstitucional;
    private String curso;
    private String matriz;
    private int periodoReferencia;
    private SituacaoAluno situacaoAluno;
    private LocalDate dataNascimento;
    private String imagem;
    private String perfilDescricao;

}