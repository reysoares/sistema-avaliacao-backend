package com.sistema.avaliacao.controller.administrador;

import com.sistema.avaliacao.config.AppConstants;
import com.sistema.avaliacao.payload.dto.CursoDTO;
import com.sistema.avaliacao.payload.response.CursoResponse;
import com.sistema.avaliacao.service.curso.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CursoController {

    @Autowired
    private CursoService cursoService;

    @GetMapping("/public/cursos")
    public ResponseEntity<CursoResponse> getAllCursos(
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_CURSOS_BY, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder) {

        CursoResponse cursos = cursoService.getAllCursos(pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(cursos, HttpStatus.OK);
    }

    @GetMapping("/public/cursos/keyword/{keyword}")
    public ResponseEntity <CursoResponse> getCursosByKeyword(
            @PathVariable String keyword,
            @RequestParam (name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam (name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam (name = "sortBy", defaultValue = AppConstants.SORT_CURSOS_BY, required = false) String sortBy,
            @RequestParam (name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder) {
        CursoResponse cursoResponse = cursoService.searchCursoByKeyword(keyword, pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(cursoResponse, HttpStatus.FOUND);
    }

    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    @PostMapping("/admin/curso")
    public ResponseEntity<CursoDTO> createCurso(@RequestBody CursoDTO cursoDTO) {
        CursoDTO created = cursoService.createCurso(cursoDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    @PutMapping("/admin/curso/{cursoId}/professor/{matriculaFuncional}")
    public ResponseEntity<CursoDTO> updateCurso(@PathVariable Long cursoId, @RequestBody CursoDTO cursoDTO, @PathVariable String matriculaFuncional) {
        CursoDTO updated = cursoService.updateCurso(cursoId, cursoDTO, matriculaFuncional);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    @DeleteMapping("/admin/curso/{cursoId}")
    public ResponseEntity<CursoDTO> deleteCurso(@PathVariable Long cursoId) {
        CursoDTO deleted = cursoService.deleteCurso(cursoId);
        return new ResponseEntity<>(deleted, HttpStatus.OK);
    }
}
