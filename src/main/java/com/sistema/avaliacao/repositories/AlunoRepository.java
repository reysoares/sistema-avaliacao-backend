package com.sistema.avaliacao.repositories;

import com.sistema.avaliacao.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlunoRepository extends JpaRepository <Aluno, String> {

    Aluno findByEmail(String email);

}
