package com.sistema.avaliacao.repositories;

import com.sistema.avaliacao.model.AvaliacaoDisciplina;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvaliacaoDisciplinaRepository extends JpaRepository<AvaliacaoDisciplina, Long> {

    Page<AvaliacaoDisciplina> findByAlunoMatriculaAcademica(String matriculaAcademica, Pageable pageable);

    Page<AvaliacaoDisciplina> findByDisciplinaCodigo(String codigo, Pageable pageable);

    boolean existsByAlunoMatriculaAcademicaAndDisciplinaCodigo(String matriculaAluno, String codigoDisciplina);

}