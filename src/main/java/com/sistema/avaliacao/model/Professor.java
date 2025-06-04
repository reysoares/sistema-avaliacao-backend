package com.sistema.avaliacao.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;


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
    private String email;
    private String departamento;

}
