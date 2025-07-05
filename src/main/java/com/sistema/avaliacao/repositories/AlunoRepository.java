package com.sistema.avaliacao.repositories;

import com.sistema.avaliacao.model.Aluno;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AlunoRepository extends JpaRepository <Aluno, String> {

    Page<Aluno> findByNomeStartingWithIgnoreCase(String keyword, Pageable pageable);

    Page<Aluno> findByCursoId(Long cursoId, Pageable pageDetails);

    Optional<Aluno> findByMatriculaAcademica(String matriculaAcademica);

}
