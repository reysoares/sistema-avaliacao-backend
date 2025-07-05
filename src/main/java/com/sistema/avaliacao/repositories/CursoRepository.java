package com.sistema.avaliacao.repositories;

import com.sistema.avaliacao.model.Curso;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRepository extends JpaRepository<Curso, Long> {

    Page<Curso> findByNomeStartingWithIgnoreCase(String keyword, Pageable pageable);

}
