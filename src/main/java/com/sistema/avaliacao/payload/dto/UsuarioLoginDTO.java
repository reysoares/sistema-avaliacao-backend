package com.sistema.avaliacao.payload.dto;

import com.sistema.avaliacao.enums.Perfil;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioLoginDTO {
    private Long id;
    private String nome;
    private String matricula;
    private Perfil perfil;
}
