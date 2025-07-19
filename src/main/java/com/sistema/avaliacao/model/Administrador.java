package com.sistema.avaliacao.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@PrimaryKeyJoinColumn(name = "id")
public class Administrador extends Usuario {

    @Column(unique = true, nullable = false)
    private String matriculaAdministrativa;

    @Override
    public String getUsername() {
        return matriculaAdministrativa;
    }
}