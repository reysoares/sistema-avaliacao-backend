package com.sistema.avaliacao.repositories;

import com.sistema.avaliacao.model.Professor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessorRepository extends JpaRepository<Professor, String> {
    Page<Professor> findByNomeStartingWithIgnoreCase(String keyword, Pageable pageable);
}
