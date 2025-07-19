package com.sistema.avaliacao.service.utilitarios;

import com.sistema.avaliacao.model.Usuario;
import com.sistema.avaliacao.repositories.AdministradorRepository;
import com.sistema.avaliacao.repositories.AlunoRepository;
import com.sistema.avaliacao.repositories.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private AdministradorRepository administradorRepository;

    @Override
    public UserDetails loadUserByUsername(String matricula) throws UsernameNotFoundException {
        Optional<? extends Usuario> usuarioOpt = alunoRepository.findByMatriculaAcademica(matricula);
        if (usuarioOpt.isEmpty()) {
            usuarioOpt = professorRepository.findByMatriculaFuncional(matricula);
        }
        if (usuarioOpt.isEmpty()) {
            usuarioOpt = administradorRepository.findByMatriculaAdministrativa(matricula);
        }

        return usuarioOpt.orElseThrow(() ->
                new UsernameNotFoundException("Usuário não encontrado: " + matricula));
    }
}