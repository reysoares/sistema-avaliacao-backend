package com.sistema.avaliacao.controller.administrador;

import com.sistema.avaliacao.payload.dto.AdministradorDTO;
import com.sistema.avaliacao.service.administrador.AdministradorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AdministradorController {

    @Autowired
    private AdministradorService administradorService;

    @PostMapping("/admin/administrador")
    public ResponseEntity<AdministradorDTO> createAdministrador(@Valid @RequestBody AdministradorDTO administradorDTO) {
        AdministradorDTO savedAdministradorDTO = administradorService.createAdministrador(administradorDTO);
        return new ResponseEntity<>(savedAdministradorDTO, HttpStatus.CREATED);
    }

    @PutMapping("/admin/administrador/{matriculaAdministrativa}")
    public ResponseEntity<AdministradorDTO> updateAdministrador(@Valid @RequestBody AdministradorDTO administradorDTO, @PathVariable String matriculaAdministrativa) {
        AdministradorDTO updatedAdministradorDTO = administradorService.updateAdministrador(administradorDTO, matriculaAdministrativa);
        return new ResponseEntity<>(updatedAdministradorDTO, HttpStatus.OK);
    }

    @DeleteMapping("/admin/administrador/{matriculaAdministrativa}")
    public ResponseEntity<AdministradorDTO> deleteAdministrador(@PathVariable String matriculaAdministrativa) {
        AdministradorDTO AdministradorDeletedDTO = administradorService.deleteAdministrador(matriculaAdministrativa);
        return new ResponseEntity<>(AdministradorDeletedDTO, HttpStatus.OK);
    }
}