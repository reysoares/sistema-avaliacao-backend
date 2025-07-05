package com.sistema.avaliacao.service.curso;

import com.sistema.avaliacao.payload.dto.CursoDTO;
import com.sistema.avaliacao.payload.response.CursoResponse;

public interface CursoService {

    CursoResponse getAllCursos(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    CursoResponse searchCursoByKeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    CursoDTO createCurso(CursoDTO cursoDTO);

    CursoDTO updateCurso(Long cursoId, CursoDTO cursoDTO, String matriculaFuncional);

    CursoDTO deleteCurso(Long cursoId);

}
