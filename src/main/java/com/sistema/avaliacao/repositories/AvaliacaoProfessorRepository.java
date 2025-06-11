package com.sistema.avaliacao.repositories;

import com.sistema.avaliacao.model.AvaliacaoProfessor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvaliacaoProfessorRepository extends JpaRepository<AvaliacaoProfessor, Long> {

    Page<AvaliacaoProfessor> findByAlunoMatriculaAcademica(String matriculaAcademica, Pageable pageable);

    Page<AvaliacaoProfessor> findByProfessorMatriculaFuncional(String matriculaFuncional, Pageable pageable);

    boolean existsByAlunoMatriculaAcademicaAndProfessorMatriculaFuncional(String matriculaAluno, String matriculaProfessor);

}
