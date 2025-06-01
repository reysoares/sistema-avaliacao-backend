package com.sistema.avaliacao.repositories;

import com.sistema.avaliacao.model.Avaliacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AvaliacaoRepository <T extends Avaliacao> extends JpaRepository <T, Long> {

    List<T> findByAlunoMatriculaAcademica(String matricula);

}
