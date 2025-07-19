package com.sistema.avaliacao.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@PrimaryKeyJoinColumn(name = "id")
public class Professor extends Usuario {

    @Column(unique = true, nullable = false)
    private String matriculaFuncional;

    private String departamento;
    private String unidadeEnsino;
    private String areaAtuacao;

    @Override
    public String getUsername() {
        return matriculaFuncional;
    }
}
