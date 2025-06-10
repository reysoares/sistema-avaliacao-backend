package com.sistema.avaliacao.controller.coordenador;

import com.sistema.avaliacao.config.AppConstants;
import com.sistema.avaliacao.payload.dto.DisciplinaDTO;
import com.sistema.avaliacao.payload.response.AvaliacaoDisciplinaResponse;
import com.sistema.avaliacao.payload.response.DisciplinaResponse;
import com.sistema.avaliacao.service.avaliacao.AvaliacaoDisciplinaService;
import com.sistema.avaliacao.service.disciplina.DisciplinaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CoordenadorDisciplinaController {

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

    @PostMapping("/admin/disciplina")
    public ResponseEntity<DisciplinaDTO> createDisciplina(@Valid @RequestBody DisciplinaDTO disciplinaDTO) {
        DisciplinaDTO savedDisciplinaDTO = disciplinaService.createDisciplina(disciplinaDTO);
        return new ResponseEntity<>(savedDisciplinaDTO, HttpStatus.CREATED);
    }

    @PutMapping("/admin/disciplina/{codigo}")
    public ResponseEntity<DisciplinaDTO> updateDisciplina(
            @Valid @RequestBody DisciplinaDTO disciplinaDTO,
            @PathVariable String codigo) {

        DisciplinaDTO updatedDisciplinaDTO = disciplinaService.updateDisciplina(disciplinaDTO, codigo);
        return new ResponseEntity<>(updatedDisciplinaDTO, HttpStatus.OK);
    }

    @DeleteMapping("/admin/disciplina/{codigo}")
    public ResponseEntity<DisciplinaDTO> deleteDisciplina(@PathVariable String codigo) {
        DisciplinaDTO disciplinaDeletedDTO = disciplinaService.deleteDisciplina(codigo);
        return new ResponseEntity<>(disciplinaDeletedDTO, HttpStatus.OK);
    }
}