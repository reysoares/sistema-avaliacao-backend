package com.sistema.avaliacao.payload.dto;

import com.sistema.avaliacao.enums.Perfil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class UsuarioDTO {
    private Long id;
    private String nome;
    private String emailInstitucional;
    private Perfil perfil;
    private LocalDate dataNascimento;
    private String imagem;
    private String perfilDescricao;
}
