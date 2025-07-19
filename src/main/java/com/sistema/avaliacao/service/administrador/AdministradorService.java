package com.sistema.avaliacao.service.administrador;

import com.sistema.avaliacao.payload.dto.AdministradorDTO;
import com.sistema.avaliacao.payload.dto.UsuarioDTO;
import jakarta.validation.Valid;

public interface AdministradorService {

    AdministradorDTO getAdministradorDTO(Long id, boolean isPerfilProprio);

    AdministradorDTO updateAdministrador(@Valid AdministradorDTO administradorDTO, String matriculaAdministrativa);

}
