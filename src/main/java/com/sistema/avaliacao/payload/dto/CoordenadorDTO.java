package com.sistema.avaliacao.payload.dto;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoordenadorDTO {

    private String matriculaFuncionalCoordenador;
    private String nome;
    private String senha;

}
