package com.sistema.avaliacao.controller.coordenador;

import com.sistema.avaliacao.payload.dto.CoordenadorDTO;
import com.sistema.avaliacao.service.coordenador.CoordenadorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CoordenadorController {

    @Autowired
    private CoordenadorService coordenadorService;

    @PostMapping("/admin/coordenador")
    public ResponseEntity<CoordenadorDTO> createCoordenador(@Valid @RequestBody CoordenadorDTO coordenadorDTO) {
        CoordenadorDTO savedCoordenadorDTO = coordenadorService.createCoordenador(coordenadorDTO);
        return new ResponseEntity<>(savedCoordenadorDTO, HttpStatus.CREATED);
    }

    @PutMapping("/admin/coordenador/{matriculaFuncionalCoordenador}")
    public ResponseEntity<CoordenadorDTO> updateCoordenador(@Valid @RequestBody CoordenadorDTO coordenadorDTO, @PathVariable String matriculaFuncionalCoordenador) {
        CoordenadorDTO updatedCoordenadorDTO = coordenadorService.updateCoordenador(coordenadorDTO, matriculaFuncionalCoordenador);
        return new ResponseEntity<>(updatedCoordenadorDTO, HttpStatus.OK);
    }

    @DeleteMapping("/admin/coordenador/{matriculaFuncionalCoordenador}")
    public ResponseEntity<CoordenadorDTO> deleteCoordenador(@PathVariable String matriculaFuncionalCoordenador) {
        CoordenadorDTO coordenadorDeletedDTO = coordenadorService.deleteCoordenador(matriculaFuncionalCoordenador);
        return new ResponseEntity<>(coordenadorDeletedDTO, HttpStatus.OK);
    }
}