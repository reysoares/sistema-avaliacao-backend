package com.sistema.avaliacao.repositories;

import com.sistema.avaliacao.model.Aluno;
import com.sistema.avaliacao.model.Coordenador;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoordenadorRepository extends JpaRepository<Coordenador, String> {
}
