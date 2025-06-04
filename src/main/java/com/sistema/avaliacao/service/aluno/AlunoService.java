package com.sistema.avaliacao.service.aluno;

import com.sistema.avaliacao.payload.dto.AlunoDTO;
import com.sistema.avaliacao.payload.response.AlunoResponse;

public interface AlunoService {

    AlunoResponse getAllAlunos(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);
    AlunoDTO creatAluno(AlunoDTO alunoDTO);
    AlunoDTO updateAluno(AlunoDTO alunoDTO, String matriculaAcademica);
    AlunoDTO deleteAluno (String matriculaAcademica);
}
