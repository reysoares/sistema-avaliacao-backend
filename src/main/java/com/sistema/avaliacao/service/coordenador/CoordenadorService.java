package com.sistema.avaliacao.service.coordenador;

import com.sistema.avaliacao.payload.dto.CoordenadorDTO;

public interface CoordenadorService {
    CoordenadorDTO createCoordenador(CoordenadorDTO coordenadorDTO);
    CoordenadorDTO updateCoordenador(CoordenadorDTO coordenadorDTO, String matriculaFuncionalCoordenador);
    CoordenadorDTO deleteCoordenador (String matriculaFuncionalCoordenador);
}
