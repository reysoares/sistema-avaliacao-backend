package com.sistema.avaliacao.service;

import com.sistema.avaliacao.payload.dto.AlunoDTO;
import com.sistema.avaliacao.payload.response.AlunoResponse;

public interface AlunoService {

    AlunoResponse getAllAlunos(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);
    AlunoDTO creatAluno(AlunoDTO alunoDTO);
    AlunoDTO deleteAluno (String matriculaAcademica);
    AlunoDTO updateAluno(AlunoDTO alunoDTO, String matriculaAcademica);;
}
