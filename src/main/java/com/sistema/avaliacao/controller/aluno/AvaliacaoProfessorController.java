package com.sistema.avaliacao.controller.aluno;

import com.sistema.avaliacao.config.AppConstants;
import com.sistema.avaliacao.payload.dto.AlunoDTO;
import com.sistema.avaliacao.payload.dto.AvaliacaoProfessorDTO;
import com.sistema.avaliacao.payload.response.AvaliacaoProfessorResponse;
import com.sistema.avaliacao.service.avaliacao.AvaliacaoProfessorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AvaliacaoProfessorController {

    @Autowired
    private AvaliacaoProfessorService avaliacaoProfessorService;

    @GetMapping("/public/aluno/avaliacao/professor/{matriculaAcademica}")
    public ResponseEntity<AvaliacaoProfessorResponse> getAlunoAvaliacoes(
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_ALUNOS_BY, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder,
            @PathVariable String matriculaAcademica) {
        AlunoDTO alunoDTO = new AlunoDTO();
        alunoDTO.setMatriculaAcademica(matriculaAcademica);
        AvaliacaoProfessorResponse avaliacaoProfessorResponse = avaliacaoProfessorService.getAlunoAvaliacoes(pageNumber, pageSize, sortBy, sortOrder, alunoDTO);
        return new ResponseEntity<>(avaliacaoProfessorResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ALUNO')")
    @PostMapping("/public/aluno/avaliacao/professor")
    public ResponseEntity <AvaliacaoProfessorDTO> createAvaliacaoProfessor(@Valid @RequestBody AvaliacaoProfessorDTO avaliacaoProfessorDTO) {
        AvaliacaoProfessorDTO savedAvaliacaoProfessorDTO = avaliacaoProfessorService.createAvaliacaoProfessor(avaliacaoProfessorDTO);
        return new ResponseEntity<>(savedAvaliacaoProfessorDTO, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('ALUNO')")
    @PutMapping("/public/aluno/avaliacao/professor/{matriculaAcademica}/{id}")
    public ResponseEntity <AvaliacaoProfessorDTO> updateAvaliacaoProfessor(@Valid @RequestBody AvaliacaoProfessorDTO avaliacaoProfessorDTO, @PathVariable String matriculaAcademica, @PathVariable Long id) {
        AvaliacaoProfessorDTO updateAvaliacaoProfessorDTO = avaliacaoProfessorService.updateAvaliacaoProfessor(avaliacaoProfessorDTO,matriculaAcademica, id);
        return new ResponseEntity<>(updateAvaliacaoProfessorDTO, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ALUNO')")
    @DeleteMapping("/admin/aluno/avaliacao/professor/{matriculaAcademica}/{id}")
    public ResponseEntity <AvaliacaoProfessorDTO> deleteAvaliacaoProfessor(@PathVariable String matriculaAcademica, @PathVariable Long id) {
        AvaliacaoProfessorDTO deletedAvaliacaoProfessorDTO = avaliacaoProfessorService.deleteAvaliacaoProfessor(matriculaAcademica, id);
        return new ResponseEntity<>(deletedAvaliacaoProfessorDTO, HttpStatus.OK);
    }

}
