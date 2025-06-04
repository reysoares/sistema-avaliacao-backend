package com.sistema.avaliacao.controller.aluno;

import com.sistema.avaliacao.config.AppConstants;
import com.sistema.avaliacao.payload.dto.*;
import com.sistema.avaliacao.payload.response.AlunoResponse;
import com.sistema.avaliacao.service.aluno.AlunoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AlunoController {

    @Autowired
    private AlunoService alunoService;

    @GetMapping("/public/aluno")
    public ResponseEntity <AlunoResponse> getAllAlunos(
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_ALUNOS_BY, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder) {
        AlunoResponse alunoResponse = alunoService.getAllAlunos(pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(alunoResponse, HttpStatus.OK);
    }

    @PostMapping("/public/aluno")
    public ResponseEntity <AlunoDTO> creatAluno(@Valid @RequestBody AlunoDTO alunoDTO) {
        AlunoDTO savedAlunoDTO = alunoService.creatAluno(alunoDTO);
        return new ResponseEntity<>(savedAlunoDTO, HttpStatus.CREATED);
    }

    @PutMapping("/public/aluno/{matriculaAcademica}")
    public ResponseEntity <AlunoDTO> updateAluno(@Valid @RequestBody AlunoDTO alunoDTO, @PathVariable String matriculaAcademica) {
        AlunoDTO updatedAlunoDTO = alunoService.updateAluno(alunoDTO, matriculaAcademica);
        return new ResponseEntity<>(updatedAlunoDTO, HttpStatus.OK);
    }

    @DeleteMapping("/admin/aluno/{matriculaAcademica}")
    public ResponseEntity <AlunoDTO> deleteAluno(@PathVariable String matriculaAcademica) {
        AlunoDTO alunoDeletedDTO = alunoService.deleteAluno(matriculaAcademica);
        return new ResponseEntity<>(alunoDeletedDTO, HttpStatus.OK);
    }
}
