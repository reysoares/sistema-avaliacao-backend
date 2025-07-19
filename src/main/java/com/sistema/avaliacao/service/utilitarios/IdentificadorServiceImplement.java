package com.sistema.avaliacao.service.utilitarios;

import com.sistema.avaliacao.enums.Perfil;
import com.sistema.avaliacao.exceptions.APIException;
import com.sistema.avaliacao.payload.dto.UsuarioCadastroDTO;
import com.sistema.avaliacao.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IdentificadorServiceImplement implements IdentificadorService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public Perfil identificarPerfil(UsuarioCadastroDTO usuarioDTO) {
        String email = usuarioDTO.getEmailInstitucional();
        String matricula = usuarioDTO.getMatricula();

        if (usuarioRepository.existsByEmailInstitucional(email)) {
            throw new APIException("E-mail institucional já cadastrado.");
        }

        if (email.endsWith("@academico.ifpb.edu.br") && matricula.matches("\\d{12}")) {
            return Perfil.ALUNO;
        } else if (email.endsWith("@ifpb.edu.br") && matricula.matches("\\d{7}")) {
            return Perfil.PROFESSOR;
        }

        throw new APIException("Não foi possível identificar o perfil com os dados fornecidos.");
    }
}
