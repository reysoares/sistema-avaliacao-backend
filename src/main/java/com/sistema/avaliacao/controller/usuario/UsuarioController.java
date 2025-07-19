package com.sistema.avaliacao.controller.usuario;

import com.sistema.avaliacao.enums.Perfil;
import com.sistema.avaliacao.exceptions.APIException;
import com.sistema.avaliacao.jwt.JwtUtils;
import com.sistema.avaliacao.model.Usuario;
import com.sistema.avaliacao.payload.dto.*;
import com.sistema.avaliacao.payload.dto.UsuarioCadastroDTO;
import com.sistema.avaliacao.payload.dto.UsuarioLoginDTO;
import com.sistema.avaliacao.service.administrador.AdministradorService;
import com.sistema.avaliacao.service.aluno.AlunoService;
import com.sistema.avaliacao.service.professor.ProfessorService;
import com.sistema.avaliacao.service.usuario.UsuarioService;
import com.sistema.avaliacao.service.utilitarios.IdentificadorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/public/usuario")
public class UsuarioController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private IdentificadorService identificadorService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private AlunoService alunoService;

    @Autowired
    private ProfessorService professorService;

    @Autowired
    private AdministradorService administradorService;

    @GetMapping("/{id}/perfil")
    public ResponseEntity<String> getPerfilUsuario(@PathVariable Long id) {
        Perfil perfil = usuarioService.getPerfilUsuario(id);
        return ResponseEntity.ok(perfil.name());
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getMatricula(),
                            loginRequest.getSenha()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            Usuario usuario = (Usuario) authentication.getPrincipal();
            String token = jwtUtils.generateTokenFromUserName(usuario);

            UsuarioLoginDTO usuarioDTO = new UsuarioLoginDTO(
                    usuario.getId(),
                    usuario.getNome(),
                    loginRequest.getMatricula(),
                    usuario.getFuncao().getPerfil()
            );

            return ResponseEntity.ok(new LoginResponse(token, usuarioDTO));
        } catch (AuthenticationException e) {
            throw new APIException("Credenciais inválidas ou usuário não encontrado.");
        }
    }

    @PostMapping("/cadastro")
    public ResponseEntity<? extends UsuarioDTO> cadastrarUsuario(@RequestBody UsuarioCadastroDTO usuarioDTO) {
        Perfil perfil = identificadorService.identificarPerfil(usuarioDTO);

        return switch (perfil) {
            case PROFESSOR -> ResponseEntity.ok(professorService.createProfessor(usuarioDTO));
            case ALUNO -> ResponseEntity.ok(alunoService.createAluno(usuarioDTO));
            default -> throw new APIException("Tipo de usuário não permitido para cadastro.");
        };
    }

}