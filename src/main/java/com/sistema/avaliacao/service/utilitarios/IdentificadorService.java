package com.sistema.avaliacao.service.utilitarios;

import com.sistema.avaliacao.enums.Perfil;
import com.sistema.avaliacao.payload.dto.UsuarioCadastroDTO;

public interface IdentificadorService {

    Perfil identificarPerfil(UsuarioCadastroDTO usuarioDTO);

}
