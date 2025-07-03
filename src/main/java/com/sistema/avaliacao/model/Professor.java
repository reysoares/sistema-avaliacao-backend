package com.sistema.avaliacao.model;

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
public class Professor {

    @Id
    @EqualsAndHashCode.Include
    private String matriculaFuncional;

    private String nome;
    private String emailInstitucional;
    private String departamento;
    private String unidadeEnsino;
    private String areaAtuacao;
    private LocalDate dataNascimento;
    private String imagem;
    private String perfilDescricao;

}
