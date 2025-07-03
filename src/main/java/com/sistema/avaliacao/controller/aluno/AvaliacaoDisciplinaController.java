package com.sistema.avaliacao.controller.aluno;

import com.sistema.avaliacao.config.AppConstants;
import com.sistema.avaliacao.payload.dto.AlunoDTO;
import com.sistema.avaliacao.payload.dto.AvaliacaoDisciplinaDTO;
import com.sistema.avaliacao.payload.response.AvaliacaoDisciplinaResponse;
import com.sistema.avaliacao.service.avaliacao.AvaliacaoDisciplinaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AvaliacaoDisciplinaController {

    @Autowired
    private AvaliacaoDisciplinaService avaliacaoDisciplinaService;

    @GetMapping("/public/aluno/avaliacao/disciplina/{matriculaAcademica}")
    public ResponseEntity<AvaliacaoDisciplinaResponse> getAlunoAvaliacoes(
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_ALUNOS_BY, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder,
            @PathVariable String matriculaAcademica) {
        AlunoDTO alunoDTO = new AlunoDTO();
        alunoDTO.setMatriculaAcademica(matriculaAcademica);
        AvaliacaoDisciplinaResponse avaliacaoDisciplinaResponse = avaliacaoDisciplinaService.getAlunoAvaliacoes(pageNumber, pageSize, sortBy, sortOrder, alunoDTO);
        return new ResponseEntity<>(avaliacaoDisciplinaResponse, HttpStatus.OK);
    }

    @PostMapping("/public/aluno/avaliacao/disciplina")
    public ResponseEntity <AvaliacaoDisciplinaDTO> createAvaliacaoProfessor(@Valid @RequestBody AvaliacaoDisciplinaDTO avaliacaoDisciplinaDTO) {
        AvaliacaoDisciplinaDTO savedAvaliacaoDisciplinaDTO = avaliacaoDisciplinaService.createAvaliacaoDisciplina(avaliacaoDisciplinaDTO);
        return new ResponseEntity<>(savedAvaliacaoDisciplinaDTO, HttpStatus.CREATED);
    }

    @PutMapping("/public/aluno/avaliacao/disciplina/{matriculaAcademica}/{id}")
    public ResponseEntity <AvaliacaoDisciplinaDTO> updateAvaliacaoProfessor(@Valid @RequestBody AvaliacaoDisciplinaDTO avaliacaoDisciplinaDTO, @PathVariable String matriculaAcademica, @PathVariable Long id) {
        AvaliacaoDisciplinaDTO updateAvaliacaoDisciplinaDTO = avaliacaoDisciplinaService.updateAvaliacaoDisciplina(avaliacaoDisciplinaDTO,matriculaAcademica, id);
        return new ResponseEntity<>(updateAvaliacaoDisciplinaDTO, HttpStatus.OK);
    }

    @DeleteMapping("/admin/aluno/avaliacao/disciplina/{matriculaAcademica}/{id}")
    public ResponseEntity <AvaliacaoDisciplinaDTO> deleteAvaliacaoProfessor(@PathVariable String matriculaAcademica, @PathVariable Long id) {
        AvaliacaoDisciplinaDTO deletedAvaliacaoDisciplinaDTO = avaliacaoDisciplinaService.deleteAvaliacaoDisciplina(matriculaAcademica, id);
        return new ResponseEntity<>(deletedAvaliacaoDisciplinaDTO, HttpStatus.OK);
    }

}