package com.sistema.avaliacao.repositories;

import com.sistema.avaliacao.model.Disciplina;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DisciplinaRepository extends JpaRepository <Disciplina, String> {
}
