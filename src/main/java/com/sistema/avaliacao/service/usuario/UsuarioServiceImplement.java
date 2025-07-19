package com.sistema.avaliacao.service.usuario;

import com.sistema.avaliacao.enums.Perfil;
import com.sistema.avaliacao.exceptions.ResourceNotFoundException;
import com.sistema.avaliacao.model.Funcao;
import com.sistema.avaliacao.model.Usuario;
import com.sistema.avaliacao.repositories.FuncaoRepository;
import com.sistema.avaliacao.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImplement implements  UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private FuncaoRepository funcaoRepository;

    @Override
    public Perfil getPerfilUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário", "id", id));

        Funcao funcao = funcaoRepository.findById(usuario.getFuncao().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Funcao", "id", id));

        return funcao.getPerfil();
    }
}
