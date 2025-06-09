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
public class Coordenador {

    @Id
    @EqualsAndHashCode.Include
    private String matriculaFuncionalCoordenador;
    private String nome;
    private String senha;
}
