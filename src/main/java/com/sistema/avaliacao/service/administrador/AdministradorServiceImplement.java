package com.sistema.avaliacao.service.administrador;

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
    public AdministradorDTO getAdministradorDTO(Long id, boolean isPerfilProprio) {
        Administrador administrador = administradorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Administrador", "id", id));

        AdministradorDTO administradorDTO = modelMapper.map(administrador, AdministradorDTO.class);

        if (!isPerfilProprio) {
            administradorDTO.setEmailInstitucional(null);
            administradorDTO.setDataNascimento(null);
            administradorDTO.setMatriculaAdministrativa(null);
        }

        return administradorDTO;
    }

    @Override
    public AdministradorDTO updateAdministrador(AdministradorDTO administradorDTO, String matriculaAdministrativa) {
        Administrador administrador = administradorRepository.findByMatriculaAdministrativa(matriculaAdministrativa)
                .orElseThrow(() -> new ResourceNotFoundException("Administrador", "matriculaAdministrativa", matriculaAdministrativa));

        administrador.setNome(administradorDTO.getNome());
        administrador.setDescricao(administradorDTO.getDescricao());
        administrador.setDataNascimento(administradorDTO.getDataNascimento());
        administrador.setEmailInstitucional(administradorDTO.getEmailInstitucional());

        Administrador administradorAtualizado = administradorRepository.save(administrador);
        return modelMapper.map(administradorAtualizado, AdministradorDTO.class);
    }

}
