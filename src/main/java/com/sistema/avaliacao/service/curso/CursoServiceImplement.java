package com.sistema.avaliacao.service.curso;

import com.sistema.avaliacao.enums.Perfil;
import com.sistema.avaliacao.exceptions.APIException;
import com.sistema.avaliacao.exceptions.ResourceNotFoundException;
import com.sistema.avaliacao.model.Curso;
import com.sistema.avaliacao.model.Professor;
import com.sistema.avaliacao.payload.dto.CursoDTO;
import com.sistema.avaliacao.payload.response.CursoResponse;
import com.sistema.avaliacao.repositories.CursoRepository;
import com.sistema.avaliacao.repositories.ProfessorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CursoServiceImplement implements CursoService {

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private ModelMapper modelMapper;

    private CursoResponse buildCursoResponse(Page<Curso> pageCursos) {
        List<CursoDTO> dtos = pageCursos.getContent().stream()
                .map(curso -> modelMapper.map(curso, CursoDTO.class))
                .toList();

        CursoResponse response = new CursoResponse();
        response.setContent(dtos);
        response.setPageNumber(pageCursos.getNumber());
        response.setPageSize(pageCursos.getSize());
        response.setTotalElements(pageCursos.getTotalElements());
        response.setTotalPages(pageCursos.getTotalPages());
        response.setLastPage(pageCursos.isLast());

        return response;
    }

    @Override
    public CursoResponse getAllCursos(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sort = sortOrder.equalsIgnoreCase("asc") ?
                Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Curso> pageCursos = cursoRepository.findAll(pageable);

        if (pageCursos.isEmpty()) {
            throw new APIException("Nenhum curso encontrado.");
        }

        return buildCursoResponse(pageCursos);
    }

    @Override
    public CursoResponse searchCursoByKeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sort = Sort.by("nome").ascending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<Curso> pageCursos = cursoRepository.findByNomeStartingWithIgnoreCase(keyword, pageable);

        List<Curso> cursos = pageCursos.getContent();

        if (cursos.isEmpty()) {
            throw new APIException("Nenhum curso encontrado com: " + keyword);
        }

        return buildCursoResponse(pageCursos);
    }

    @Override
    public CursoDTO createCurso(CursoDTO cursoDTO) {
        Curso curso = modelMapper.map(cursoDTO, Curso.class);
        Curso salvo = cursoRepository.save(curso);
        return modelMapper.map(salvo, CursoDTO.class);
    }

    @Override
    public CursoDTO updateCurso(Long cursoId, CursoDTO cursoDTO, String matriculaFuncional) {
        Curso curso = cursoRepository.findById(cursoId)
                .orElseThrow(() -> new ResourceNotFoundException("Curso", "id", cursoId));

        curso.setNome(cursoDTO.getNome());

        Professor professor = professorRepository.findByMatriculaFuncional(matriculaFuncional)
                .orElseThrow(() -> new ResourceNotFoundException("Professor", "matriculaFuncional", matriculaFuncional));

        professor.setPerfil(Perfil.COORDENADOR);
        curso.setCoordenador(professor);

        professorRepository.save(professor);
        Curso atualizado = cursoRepository.save(curso);
        return modelMapper.map(atualizado, CursoDTO.class);
    }

    @Override
    public CursoDTO deleteCurso(Long cursoId) {
        Curso curso = cursoRepository.findById(cursoId)
                .orElseThrow(() -> new ResourceNotFoundException("Curso", "id", cursoId));

        CursoDTO dto = modelMapper.map(curso, CursoDTO.class);
        cursoRepository.delete(curso);
        return dto;
    }
}