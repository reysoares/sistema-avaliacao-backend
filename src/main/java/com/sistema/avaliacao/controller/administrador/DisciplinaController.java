package com.sistema.avaliacao.controller.administrador;

import com.sistema.avaliacao.config.AppConstants;
import com.sistema.avaliacao.payload.dto.DisciplinaCreateDTO;
import com.sistema.avaliacao.payload.dto.DisciplinaDTO;
import com.sistema.avaliacao.payload.response.AvaliacaoDisciplinaResponse;
import com.sistema.avaliacao.payload.response.DisciplinaResponse;
import com.sistema.avaliacao.service.avaliacao.AvaliacaoDisciplinaService;
import com.sistema.avaliacao.service.disciplina.DisciplinaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class DisciplinaController {

    @Autowired
    private DisciplinaService disciplinaService;

    @Autowired
    private AvaliacaoDisciplinaService avaliacaoDisciplinaService;

    @GetMapping("/public/disciplina")
    public ResponseEntity<DisciplinaResponse> getAllDisciplinas(
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_DISCIPLINAS_BY, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder) {

        DisciplinaResponse disciplinaResponse = disciplinaService.getAllDisciplinas(pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(disciplinaResponse, HttpStatus.OK);
    }

    @GetMapping("/public/disciplina/curso/{cursoId}")
    public ResponseEntity<DisciplinaResponse> getDisciplinasByCurso(
            @PathVariable Long cursoId,
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_DISCIPLINAS_BY, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder) {

        DisciplinaResponse disciplinaResponse = disciplinaService.getDisciplinasByCurso(cursoId, pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(disciplinaResponse, HttpStatus.OK);
    }

    @GetMapping("/public/disciplina/keyword/{keyword}")
    public ResponseEntity <DisciplinaResponse> getDisciplinaByKeyword(
            @PathVariable String keyword,
            @RequestParam (name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam (name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam (name = "sortBy", defaultValue = AppConstants.SORT_DISCIPLINAS_BY, required = false) String sortBy,
            @RequestParam (name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder) {
        DisciplinaResponse disciplinaResponse = disciplinaService.searchAlunoByKeyword(keyword, pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(disciplinaResponse, HttpStatus.FOUND);
    }

    @GetMapping("/public/avaliacoes/disciplina/{codigo}")
    public ResponseEntity<AvaliacaoDisciplinaResponse> getAvaliacoesDisciplina(
            @PathVariable String codigo,
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_DISCIPLINAS_BY, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder) {


        DisciplinaDTO disciplinaDTO = new DisciplinaDTO();
        disciplinaDTO.setCodigo(codigo);
        AvaliacaoDisciplinaResponse avaliacaoDisciplinaResponse = avaliacaoDisciplinaService.getDisciplinaAvaliacoes(pageNumber, pageSize, sortBy, sortOrder, disciplinaDTO);
        return new ResponseEntity<>(avaliacaoDisciplinaResponse, HttpStatus.OK);
    }

    // Método createDisciplina em DisciplinaController.java

    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    @PostMapping("/admin/disciplina")
    public ResponseEntity<DisciplinaDTO> createDisciplina(@Valid @RequestBody DisciplinaCreateDTO disciplinaCreateDTO) {
        DisciplinaDTO savedDisciplinaDTO = disciplinaService.createDisciplina(disciplinaCreateDTO);
        return new ResponseEntity<>(savedDisciplinaDTO, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    @PutMapping("/admin/disciplina/{codigo}/curso/{cursoId}/professor/{matriculaFuncional}")
    public ResponseEntity<DisciplinaDTO> updateDisciplina(@Valid @RequestBody DisciplinaDTO disciplinaDTO, @PathVariable String codigo,  @PathVariable Long cursoId, @PathVariable String matriculaFuncional) {
        DisciplinaDTO updatedDisciplinaDTO = disciplinaService.updateDisciplina(disciplinaDTO, codigo, cursoId, matriculaFuncional);
        return new ResponseEntity<>(updatedDisciplinaDTO, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    @DeleteMapping("/admin/disciplina/{codigo}")
    public ResponseEntity<DisciplinaDTO> deleteDisciplina(@PathVariable String codigo) {
        DisciplinaDTO disciplinaDeletedDTO = disciplinaService.deleteDisciplina(codigo);
        return new ResponseEntity<>(disciplinaDeletedDTO, HttpStatus.OK);
    }
}