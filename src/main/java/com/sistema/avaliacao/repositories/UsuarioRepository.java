package com.sistema.avaliacao.repositories;

import com.sistema.avaliacao.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    boolean existsByEmailInstitucional(String emailInstitucional);

}

