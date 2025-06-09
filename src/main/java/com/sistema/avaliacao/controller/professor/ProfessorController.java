package com.sistema.avaliacao.controller.professor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.sistema.avaliacao.config.AppConstants;
import com.sistema.avaliacao.payload.dto.ProfessorDTO;
import com.sistema.avaliacao.payload.response.ProfessorResponse;
import com.sistema.avaliacao.service.professor.ProfessorService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class ProfessorController {

    @Autowired
    private ProfessorService professorService;

    @GetMapping("/public/professor")
    public ResponseEntity <ProfessorResponse> getAllProfessor(
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_PROFESSORES_BY, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder) {
        ProfessorResponse professorResponse = professorService.getAllProfessor(pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(professorResponse, HttpStatus.OK);
    }

    @PostMapping("/public/professor")
    public ResponseEntity <ProfessorDTO> createProfessor(@Valid @RequestBody ProfessorDTO professorDTO) {
        ProfessorDTO savedProfessorDTO = professorService.createProfessor(professorDTO);
        return new ResponseEntity<>(savedProfessorDTO, HttpStatus.CREATED);
    }

    @PutMapping("/public/professor/{matriculaFuncional}")
    public ResponseEntity <ProfessorDTO> updateProfessor(@Valid @RequestBody ProfessorDTO professorDTO, @PathVariable String matriculaFuncional) {
        ProfessorDTO updatedProfessorDTO = professorService.updateProfessor(professorDTO, matriculaFuncional);
        return new ResponseEntity<>(updatedProfessorDTO, HttpStatus.OK);
    }

    @DeleteMapping("/admin/professor/{matriculaFuncional}")
    public ResponseEntity <ProfessorDTO> deleteProfessor(@PathVariable String matriculaFuncional) {
        ProfessorDTO professorDeletedDTO = professorService.deleteProfessor(matriculaFuncional);
        return new ResponseEntity<>(professorDeletedDTO, HttpStatus.OK);
    }
}
