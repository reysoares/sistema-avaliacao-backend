package com.sistema.avaliacao.repositories;

import com.sistema.avaliacao.enums.Perfil;
import com.sistema.avaliacao.model.Funcao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FuncaoRepository extends JpaRepository<Funcao, Long> {

    Optional<Funcao> findByPerfil(Perfil perfil);

}
