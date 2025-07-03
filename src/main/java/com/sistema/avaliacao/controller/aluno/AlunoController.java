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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class AlunoController {

    @Autowired
    private AlunoService alunoService;

    @GetMapping("/public/alunos")
    public ResponseEntity <AlunoResponse> getAllAlunos(
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_ALUNOS_BY, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder) {
        AlunoResponse alunoResponse = alunoService.getAllAlunos(pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(alunoResponse, HttpStatus.OK);
    }

    @GetMapping("/public/alunos/keyword/{keyword}")
    public ResponseEntity <AlunoResponse> getAlunosByKeyword(
            @PathVariable String keyword,
            @RequestParam (name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam (name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam (name = "sortBy", defaultValue = AppConstants.SORT_ALUNOS_BY, required = false) String sortBy,
            @RequestParam (name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder) {
        AlunoResponse alunoResponse = alunoService.searchAlunoByKeyword(keyword, pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(alunoResponse, HttpStatus.FOUND);
    }

    @PostMapping("/public/aluno")
    public ResponseEntity <AlunoDTO> createAluno(@Valid @RequestBody AlunoDTO alunoDTO) {
        AlunoDTO savedAlunoDTO = alunoService.createAluno(alunoDTO);
        return new ResponseEntity<>(savedAlunoDTO, HttpStatus.CREATED);
    }

    @PutMapping("/public/aluno/{matriculaAcademica}")
    public ResponseEntity <AlunoDTO> updateAluno(@Valid @RequestBody AlunoDTO alunoDTO, @PathVariable String matriculaAcademica) {
        AlunoDTO updateAlunoDTO = alunoService.atualizarAlunoViaSuap(alunoDTO, matriculaAcademica);
        return new ResponseEntity<>(updateAlunoDTO, HttpStatus.OK);
    }

    @PutMapping("/public/aluno/{matriculaAcademica}/imagem")
    public ResponseEntity <AlunoDTO> updateAlunoImagem(@PathVariable String matriculaAcademica, @RequestParam("imagem") MultipartFile imagem) throws IOException {
        AlunoDTO updateAlunoDTO = alunoService.updateAlunoImagem(matriculaAcademica, imagem);
        return new ResponseEntity<>(updateAlunoDTO, HttpStatus.OK);
    }

    @PutMapping("/public/aluno/{matriculaAcademica}/descricao")
    public ResponseEntity<AlunoDTO> updateAlunoDescricao(@PathVariable String matriculaAcademica, @RequestBody AlunoDTO alunoDTO) {
        AlunoDTO updateAlunoDTO = alunoService.updatePerfilDescricao(matriculaAcademica, alunoDTO);
        return new ResponseEntity<>(updateAlunoDTO, HttpStatus.OK);
    }

    @DeleteMapping("/admin/aluno/{matriculaAcademica}")
    public ResponseEntity <AlunoDTO> deleteAluno(@PathVariable String matriculaAcademica) {
        AlunoDTO alunoDeletedDTO = alunoService.deleteAluno(matriculaAcademica);
        return new ResponseEntity<>(alunoDeletedDTO, HttpStatus.OK);
    }
}
