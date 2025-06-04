package com.sistema.avaliacao.payload.dto;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlunoDTO {

    @Id
    private String matriculaAcademica;
    private String nome;
    private String senha;
    private String email;
    private String curso;
}
