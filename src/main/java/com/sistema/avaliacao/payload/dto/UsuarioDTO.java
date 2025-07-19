package com.sistema.avaliacao.payload.dto;

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
    private FuncaoDTO funcaoDTO;
    private LocalDate dataNascimento;
    private String imagem;
    private String descricao;
}
