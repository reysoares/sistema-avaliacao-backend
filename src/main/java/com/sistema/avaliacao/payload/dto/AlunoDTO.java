package com.sistema.avaliacao.payload.dto;

import com.sistema.avaliacao.enums.SituacaoAluno;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AlunoDTO extends UsuarioDTO {

    private String matriculaAcademica;
    private String matriz;
    private int periodoReferencia;
    private SituacaoAluno situacaoAluno;
    private CursoDTO curso;

}
