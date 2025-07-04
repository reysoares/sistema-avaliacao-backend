package com.sistema.avaliacao.service.avaliacao;

import com.sistema.avaliacao.payload.dto.*;
import com.sistema.avaliacao.payload.response.AvaliacaoDisciplinaResponse;

public interface AvaliacaoDisciplinaService {

    AvaliacaoDisciplinaResponse getAlunoAvaliacoes(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder, AlunoDTO alunoDTO);

    AvaliacaoDisciplinaResponse getDisciplinaAvaliacoes(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder, DisciplinaDTO disciplinaDTO);

    AvaliacaoDisciplinaDTO createAvaliacaoDisciplina(AvaliacaoDisciplinaDTO avaliacaoDisciplinaDTO);

    AvaliacaoDisciplinaDTO updateAvaliacaoDisciplina( AvaliacaoDisciplinaDTO avaliacaoDisciplinaDTO, String matriculaAcademica, Long id);

    AvaliacaoDisciplinaDTO deleteAvaliacaoDisciplina(String matriculaAcademica, Long id);

}