package com.sistema.avaliacao.service.disciplina;

import com.sistema.avaliacao.payload.dto.DisciplinaDTO;
import com.sistema.avaliacao.payload.response.DisciplinaResponse;

public interface DisciplinaService {
    DisciplinaResponse getAllDisciplinas(Integer pageNumber, Integer pageSize, String sortBy, String order);
    DisciplinaDTO createDisciplina(DisciplinaDTO disciplinaDTO);
    DisciplinaDTO updateDisciplina(DisciplinaDTO disciplinaDTO,String codigo);
    DisciplinaDTO deleteDisciplina(String codigo);

}