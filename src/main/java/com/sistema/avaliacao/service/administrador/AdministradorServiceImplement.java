package com.sistema.avaliacao.service.administrador;

import com.sistema.avaliacao.exceptions.APIException;
import com.sistema.avaliacao.exceptions.ResourceNotFoundException;
import com.sistema.avaliacao.model.Administrador;
import com.sistema.avaliacao.payload.dto.AdministradorDTO;
import com.sistema.avaliacao.repositories.AdministradorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdministradorServiceImplement implements AdministradorService {

    @Autowired
    private AdministradorRepository administradorRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public AdministradorDTO createAdministrador(AdministradorDTO administradorDTO) {
        Administrador administrador = modelMapper.map(administradorDTO, Administrador.class);

        boolean administradorJaExiste = administradorRepository.findAll().stream().findAny().isPresent();

        if (administradorJaExiste) {
            throw new APIException("Já existe um administrador cadastrado no sistema.");
        }

        Administrador administradorSalvo = administradorRepository.save(administrador);
        return modelMapper.map(administradorSalvo, AdministradorDTO.class);
    }

    @Override
    public AdministradorDTO updateAdministrador(AdministradorDTO administradorDTO, String matriculaAdministrativa) {
        Administrador administradorExistente = administradorRepository.findByMatriculaAdministrativa(matriculaAdministrativa)
                .orElseThrow(() -> new ResourceNotFoundException("Administrador", "matriculaAdministrativa", matriculaAdministrativa));

        administradorExistente.setNome(administradorDTO.getNome());
        administradorExistente.setEmailInstitucional(administradorDTO.getEmailInstitucional());
        administradorExistente.setPerfil(administradorDTO.getPerfil());
        administradorExistente.setPerfilDescricao(administradorDTO.getPerfilDescricao());

        Administrador administradorAtualizado = administradorRepository.save(administradorExistente);
        return modelMapper.map(administradorAtualizado, AdministradorDTO.class);
    }

    @Override
    public AdministradorDTO deleteAdministrador(String matriculaAdministrativa) {
        Administrador administrador = administradorRepository.findByMatriculaAdministrativa(matriculaAdministrativa)
                .orElseThrow(() -> new ResourceNotFoundException("Administrador", "matriculaAdministrativa", matriculaAdministrativa));

        AdministradorDTO administradorDTO = modelMapper.map(administrador, AdministradorDTO.class);
        administradorRepository.delete(administrador);
        return administradorDTO;
    }
}
