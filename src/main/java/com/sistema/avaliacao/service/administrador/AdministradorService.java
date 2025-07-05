package com.sistema.avaliacao.service.administrador;

import com.sistema.avaliacao.payload.dto.AdministradorDTO;
import jakarta.validation.Valid;

public interface AdministradorService {

    AdministradorDTO createAdministrador(@Valid AdministradorDTO administradorDTO);

    AdministradorDTO updateAdministrador(@Valid AdministradorDTO administradorDTO, String matriculaAdministrativa);

    AdministradorDTO deleteAdministrador(String matriculaAdministrativa);

}
