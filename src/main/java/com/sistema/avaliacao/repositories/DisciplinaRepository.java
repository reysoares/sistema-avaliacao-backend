package com.sistema.avaliacao.repositories;


import com.sistema.avaliacao.model.Disciplina;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DisciplinaRepository extends JpaRepository <Disciplina, String> {

    Page<Disciplina> findByProfessorMatriculaFuncional(String matriculaFuncional, Pageable pageable);

}
