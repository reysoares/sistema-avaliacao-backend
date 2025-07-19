package com.sistema.avaliacao.controller.aluno;

import com.sistema.avaliacao.config.AppConstants;
import com.sistema.avaliacao.payload.dto.*;
import com.sistema.avaliacao.payload.response.AlunoResponse;
import com.sistema.avaliacao.service.aluno.AlunoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @GetMapping("/public/alunos/curso/{cursoId}")
    public ResponseEntity <AlunoResponse> getAlunosByCurso(
            @PathVariable Long cursoId,
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_ALUNOS_BY, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder) {
        AlunoResponse alunoResponse = alunoService.getAlunosByCurso(cursoId, pageNumber, pageSize, sortBy, sortOrder);
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

    @GetMapping("/public/aluno/perfil/{id}/{usuarioLogadoId}")
    public ResponseEntity<AlunoDTO> getPerfilAluno(
            @PathVariable Long id,
            @PathVariable Long usuarioLogadoId) {

        boolean isPerfilProprio = usuarioLogadoId != null && usuarioLogadoId.equals(id);
        AlunoDTO alunoDTO = alunoService.getAlunoDTO(id, isPerfilProprio);
        return ResponseEntity.ok(alunoDTO);
    }

    @PreAuthorize("hasAuthority('ALUNO')")
    @PutMapping("/public/aluno/{matriculaAcademica}/curso/{cursoId}")
    public ResponseEntity <AlunoDTO> updateAluno(@Valid @RequestBody AlunoDTO alunoDTO, @PathVariable String matriculaAcademica, @PathVariable Long cursoId) {
        AlunoDTO updateAlunoDTO = alunoService.atualizarAluno(alunoDTO, matriculaAcademica, cursoId);
        return new ResponseEntity<>(updateAlunoDTO, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ALUNO')")
    @PutMapping("/public/aluno/{matriculaAcademica}/imagem")
    public ResponseEntity <AlunoDTO> updateAlunoImagem(@PathVariable String matriculaAcademica, @RequestParam("imagem") MultipartFile imagem) throws IOException {
        AlunoDTO updateAlunoDTO = alunoService.updateAlunoImagem(matriculaAcademica, imagem);
        return new ResponseEntity<>(updateAlunoDTO, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ALUNO')")
    @DeleteMapping("/admin/aluno/{matriculaAcademica}")
    public ResponseEntity <AlunoDTO> deleteAluno(@PathVariable String matriculaAcademica) {
        AlunoDTO alunoDeletedDTO = alunoService.deleteAluno(matriculaAcademica);
        return new ResponseEntity<>(alunoDeletedDTO, HttpStatus.OK);
    }
}
