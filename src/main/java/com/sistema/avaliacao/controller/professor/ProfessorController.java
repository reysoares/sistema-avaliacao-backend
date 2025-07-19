package com.sistema.avaliacao.controller.professor;

import com.sistema.avaliacao.payload.dto.AlunoDTO;
import com.sistema.avaliacao.payload.dto.UsuarioLoginDTO;
import com.sistema.avaliacao.payload.response.AvaliacaoProfessorResponse;
import com.sistema.avaliacao.payload.response.DisciplinaResponse;
import com.sistema.avaliacao.service.avaliacao.AvaliacaoProfessorService;
import com.sistema.avaliacao.service.disciplina.DisciplinaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class ProfessorController {

    @Autowired
    private ProfessorService professorService;

    @Autowired
    private DisciplinaService disciplinaService;

    @Autowired
    private AvaliacaoProfessorService avaliacaoProfessorService;

    @GetMapping("/public/professores")
    public ResponseEntity <ProfessorResponse> getAllProfessor(
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_PROFESSORES_BY, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder) {
        ProfessorResponse professorResponse = professorService.getAllProfessor(pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(professorResponse, HttpStatus.OK);
    }

    @GetMapping("/public/professores/keyword/{keyword}")
    public ResponseEntity <ProfessorResponse> getProfessoresByKeyword(
            @PathVariable String keyword,
            @RequestParam (name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam (name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam (name = "sortBy", defaultValue = AppConstants.SORT_ALUNOS_BY, required = false) String sortBy,
            @RequestParam (name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder) {
        ProfessorResponse professorResponse = professorService.searchProfessorByKeyword(keyword, pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(professorResponse, HttpStatus.FOUND);
    }

    @GetMapping("/public/professor/disciplinas/{matriculaFuncional}")
    public ResponseEntity <DisciplinaResponse> getAllDisciplinas(
            @PathVariable String matriculaFuncional,
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_PROFESSORES_BY, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder) {
        ProfessorDTO professorDTO = new ProfessorDTO();
        professorDTO.setMatriculaFuncional(matriculaFuncional);
        DisciplinaResponse disciplinaResponse = disciplinaService.getProfessorDisciplinas(pageNumber, pageSize, sortBy, sortOrder, professorDTO);
        return new ResponseEntity<>(disciplinaResponse, HttpStatus.OK);
    }

    @GetMapping("/public/professor/avaliacoes/{matriculaFuncional}")
    public ResponseEntity <AvaliacaoProfessorResponse> getAllAvaliacoes(
            @PathVariable String matriculaFuncional,
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_PROFESSORES_BY, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder) {
        ProfessorDTO professorDTO = new ProfessorDTO();
        professorDTO.setMatriculaFuncional(matriculaFuncional);
        AvaliacaoProfessorResponse avaliacaoProfessorResponse = avaliacaoProfessorService.getProfessorAvaliacoes(pageNumber, pageSize, sortBy, sortOrder, professorDTO);
        return new ResponseEntity<>(avaliacaoProfessorResponse, HttpStatus.OK);
    }

    @GetMapping("/public/professor/perfil/{id}")
    public ResponseEntity<ProfessorDTO> getPerfilProfessor(
            @PathVariable Long id,
            @AuthenticationPrincipal UsuarioLoginDTO usuarioLogado) {

        boolean isPerfilProprio = usuarioLogado != null && usuarioLogado.getId().equals(id);
        ProfessorDTO professorDTO = professorService.getProfessorDTO(id, isPerfilProprio);
        return ResponseEntity.ok(professorDTO);
    }

    @PreAuthorize("hasAuthority('PROFESSOR')")
    @PutMapping("/public/professor/{matriculaFuncional}")
    public ResponseEntity <ProfessorDTO> updateProfessor(@Valid @RequestBody ProfessorDTO professorDTO, @PathVariable String matriculaFuncional) {
        ProfessorDTO updatedProfessorDTO = professorService.atualizarProfessor(professorDTO, matriculaFuncional);
        return new ResponseEntity<>(updatedProfessorDTO, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('PROFESSOR')")
    @PutMapping("/public/professor/{matriculaFuncional}/imagem")
    public ResponseEntity <ProfessorDTO> updateProfessorImagem(@PathVariable String matriculaFuncional, @RequestParam("imagem") MultipartFile imagem) throws IOException {
        ProfessorDTO updateProfessorDTO = professorService.updateProfessorImagem(matriculaFuncional, imagem);
        return new ResponseEntity<>(updateProfessorDTO, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('PROFESSOR')")
    @DeleteMapping("/admin/professor/{matriculaFuncional}")
    public ResponseEntity <ProfessorDTO> deleteProfessor(@PathVariable String matriculaFuncional) {
        ProfessorDTO professorDeletedDTO = professorService.deleteProfessor(matriculaFuncional);
        return new ResponseEntity<>(professorDeletedDTO, HttpStatus.OK);
    }
}
