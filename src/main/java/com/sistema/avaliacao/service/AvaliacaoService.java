package com.sistema.avaliacao.service;

import com.sistema.avaliacao.model.Avaliacao;
import com.sistema.avaliacao.payload.dto.AvaliacaoDTO;
import com.sistema.avaliacao.payload.response.AvaliacaoResponse;

import java.util.List;

public interface AvaliacaoService<D extends AvaliacaoDTO> {
    AvaliacaoResponse<D> getAllAvaliacoes(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);
    List<D> getAvaliacoesDoAluno(String matricula);
    D createAvaliacao(D avaliacaoDTO);
    void deleteAvaliacao(Long id);
}
