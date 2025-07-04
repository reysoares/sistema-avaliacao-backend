package com.sistema.avaliacao.service.avaliacao;

import com.sistema.avaliacao.payload.dto.AlunoDTO;
import com.sistema.avaliacao.payload.dto.AvaliacaoProfessorDTO;
import com.sistema.avaliacao.payload.dto.ProfessorDTO;
import com.sistema.avaliacao.payload.response.AvaliacaoProfessorResponse;

public interface AvaliacaoProfessorService {

    AvaliacaoProfessorResponse getAlunoAvaliacoes(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder, AlunoDTO alunoDTO);

    AvaliacaoProfessorResponse getProfessorAvaliacoes(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder, ProfessorDTO professorDTO);

    AvaliacaoProfessorDTO createAvaliacaoProfessor(AvaliacaoProfessorDTO avaliacaoDTO);

    AvaliacaoProfessorDTO updateAvaliacaoProfessor(AvaliacaoProfessorDTO avaliacaoDTO, String matriculaAcademica, Long id);

    AvaliacaoProfessorDTO deleteAvaliacaoProfessor(String matriculaAcademica, Long id);

}
