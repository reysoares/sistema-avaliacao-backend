package com.sistema.avaliacao.service.professor;

import com.sistema.avaliacao.payload.dto.ProfessorDTO;
import com.sistema.avaliacao.payload.response.ProfessorResponse;

public interface ProfessorService {
    ProfessorResponse getAllProfessor(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);
    ProfessorDTO createProfessor(ProfessorDTO professorDTO);
    ProfessorDTO updateProfessor(ProfessorDTO professorDTO, String matriculaFuncional);
    ProfessorDTO deleteProfessor (String matriculaFuncional);
}
