package com.sistema.avaliacao.repositories;

import com.sistema.avaliacao.model.Administrador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdministradorRepository extends JpaRepository<Administrador, Long> {

    Optional<Administrador> findByMatriculaAdministrativa (String matriculaAdministrativa);

}
