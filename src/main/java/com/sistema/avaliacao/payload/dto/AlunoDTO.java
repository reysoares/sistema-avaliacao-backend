package com.sistema.avaliacao.payload.dto;

import com.sistema.avaliacao.config.SituacaoAluno;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlunoDTO {

    private String matriculaAcademica;
    private String nome;
    private String emailInstitucional;
    private String curso;
    private String matriz;
    private int periodoReferencia;
    private SituacaoAluno situacaoAluno;
    private LocalDate dataNascimento;
    private String imagem;
    private String perfilDescricao;

}
