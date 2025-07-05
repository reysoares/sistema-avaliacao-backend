package com.sistema.avaliacao.model;

import com.sistema.avaliacao.enums.Perfil;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public abstract class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private String nome;
    private String emailInstitucional;

    @Enumerated(EnumType.STRING)
    private Perfil perfil;

    private LocalDate dataNascimento;
    private String imagem;
    private String perfilDescricao;

}
