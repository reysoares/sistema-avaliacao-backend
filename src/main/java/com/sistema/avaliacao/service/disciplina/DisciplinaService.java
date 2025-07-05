package com.sistema.avaliacao.service.disciplina;

import com.sistema.avaliacao.payload.dto.DisciplinaDTO;
import com.sistema.avaliacao.payload.dto.ProfessorDTO;
import com.sistema.avaliacao.payload.response.DisciplinaResponse;

public interface DisciplinaService {

    DisciplinaResponse getAllDisciplinas(Integer pageNumber, Integer pageSize, String sortBy, String order);

    DisciplinaResponse getDisciplinasByCurso(Long cursoId, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    DisciplinaResponse searchAlunoByKeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    DisciplinaResponse getProfessorDisciplinas(Integer pageNumber, Integer pageSize, String sortBy, String order, ProfessorDTO professorDTO);

    DisciplinaDTO createDisciplina(DisciplinaDTO disciplinaDTO);

    DisciplinaDTO updateDisciplina(DisciplinaDTO disciplinaDTO, String codigo, Long cursoId, String matriculaFuncional);

    DisciplinaDTO deleteDisciplina(String codigo);

}