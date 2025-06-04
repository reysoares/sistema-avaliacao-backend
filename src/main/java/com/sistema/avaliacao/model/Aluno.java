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
public class Aluno {

    @Id
    @EqualsAndHashCode.Include
    private String matriculaAcademica;
    private String nome;
    private String senha;
    private String email;
    private String curso;

}