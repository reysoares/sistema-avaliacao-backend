package com.sistema.avaliacao.service.coordenador;

import com.sistema.avaliacao.exceptions.APIException;
import com.sistema.avaliacao.exceptions.ResourceNotFoundException;
import com.sistema.avaliacao.model.Coordenador;
import com.sistema.avaliacao.payload.dto.CoordenadorDTO;
import com.sistema.avaliacao.repositories.CoordenadorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CoordenadorServiceImplement implements CoordenadorService {

    @Autowired
    private CoordenadorRepository coordenadorRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CoordenadorDTO creatCoordenador(CoordenadorDTO coordenadorDTO) {
        Coordenador coordenador = modelMapper.map(coordenadorDTO, Coordenador.class);

        boolean coordenadorJaExiste = coordenadorRepository.findAll().stream().findAny().isPresent();

        if (coordenadorJaExiste) {
            throw new APIException("Já existe um coordenador cadastrado no sistema.");
        }

        Coordenador coordenadorSalvo = coordenadorRepository.save(coordenador);
        return modelMapper.map(coordenadorSalvo, CoordenadorDTO.class);
    }

    @Override
    public CoordenadorDTO updateCoordenador(CoordenadorDTO coordenadorDTO, String matriculaFuncionalCoordenador) {
        Coordenador coordenadorExistente = coordenadorRepository.findById(matriculaFuncionalCoordenador)
                .orElseThrow(() -> new ResourceNotFoundException("Coordenador", "matriculaFuncionalCoordenador", matriculaFuncionalCoordenador));

        coordenadorExistente.setNome(coordenadorDTO.getNome());
        coordenadorExistente.setMatriculaFuncionalCoordenador(coordenadorDTO.getMatriculaFuncionalCoordenador());
        coordenadorExistente.setSenha(coordenadorDTO.getSenha());

        Coordenador coordenadorAtualizado = coordenadorRepository.save(coordenadorExistente);
        return modelMapper.map(coordenadorAtualizado, CoordenadorDTO.class);
    }

    @Override
    public CoordenadorDTO deleteCoordenador(String matriculaFuncionalCoordenador) {
        Coordenador coordenador = coordenadorRepository.findById(matriculaFuncionalCoordenador)
                .orElseThrow(() -> new ResourceNotFoundException("Coordenador", "matriculaFuncionalCoordenador", matriculaFuncionalCoordenador));

        CoordenadorDTO coordenadorDTO = modelMapper.map(coordenador, CoordenadorDTO.class);
        coordenadorRepository.delete(coordenador);
        return coordenadorDTO;
    }
}
