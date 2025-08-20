package com.sistema.avaliacao.controller.administrador;

import com.sistema.avaliacao.payload.dto.AdministradorDTO;
import com.sistema.avaliacao.payload.dto.UsuarioLoginDTO;
import com.sistema.avaliacao.service.administrador.AdministradorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AdministradorController {

    @Autowired
    private AdministradorService administradorService;

    @GetMapping("/public/administrador/perfil/{id}")
    public ResponseEntity<AdministradorDTO> getPerfilAdministrador(
            @PathVariable Long id,
            @AuthenticationPrincipal UsuarioLoginDTO usuarioLogado) {

        boolean isPerfilProprio = usuarioLogado != null && usuarioLogado.getId().equals(id);
        AdministradorDTO administradorDTO = administradorService.getAdministradorDTO(id, isPerfilProprio);
        return ResponseEntity.ok(administradorDTO);
    }

    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    @PutMapping("/admin/administrador/{matriculaAdministrativa}")
    public ResponseEntity<AdministradorDTO> updateAdministrador(@Valid @RequestBody AdministradorDTO administradorDTO, @PathVariable String matriculaAdministrativa) {
        AdministradorDTO updatedAdministradorDTO = administradorService.updateAdministrador(administradorDTO, matriculaAdministrativa);
        return new ResponseEntity<>(updatedAdministradorDTO, HttpStatus.OK);
    }
}