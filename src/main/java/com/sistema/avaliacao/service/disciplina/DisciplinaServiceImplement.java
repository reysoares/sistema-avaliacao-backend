package com.sistema.avaliacao.service.disciplina;

import com.sistema.avaliacao.exceptions.APIException;
import com.sistema.avaliacao.exceptions.ResourceNotFoundException;
import com.sistema.avaliacao.model.Curso;
import com.sistema.avaliacao.model.Disciplina;
import com.sistema.avaliacao.model.Professor;
import com.sistema.avaliacao.payload.dto.DisciplinaDTO;
import com.sistema.avaliacao.payload.dto.ProfessorDTO;
import com.sistema.avaliacao.payload.response.DisciplinaResponse;
import com.sistema.avaliacao.repositories.CursoRepository;
import com.sistema.avaliacao.repositories.DisciplinaRepository;
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
public class DisciplinaServiceImplement implements DisciplinaService {

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private ModelMapper modelMapper;

    private DisciplinaResponse buildDisciplinaResponse(Page<Disciplina> disciplinaPage) {
        List<DisciplinaDTO> dtos = disciplinaPage.getContent().stream()
                .map(av -> modelMapper.map(av, DisciplinaDTO.class))
                .toList();

        DisciplinaResponse disciplinaResponse = new DisciplinaResponse();
        disciplinaResponse.setContent(dtos);
        disciplinaResponse.setPageNumber(disciplinaPage.getNumber());
        disciplinaResponse.setPageSize(disciplinaPage.getSize());
        disciplinaResponse.setTotalElements(disciplinaPage.getTotalElements());
        disciplinaResponse.setTotalPages(disciplinaPage.getTotalPages());
        disciplinaResponse.setLastPage(disciplinaPage.isLast());
        return disciplinaResponse;
    }

    @Override
    public DisciplinaResponse getAllDisciplinas(Integer pageNumber, Integer pageSize, String sortBy, String order) {
        Sort sort = order.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<Disciplina> disciplinasPage = disciplinaRepository.findAll(pageable);
        List<Disciplina> disciplinas = disciplinasPage.getContent();

        if (disciplinas.isEmpty()) {
            throw new APIException("Nenhuma disciplina encontrada.");
        }

        return buildDisciplinaResponse(disciplinasPage);
    }

    @Override
    public DisciplinaResponse getDisciplinasByCurso(Long cursoId, Integer pageNumber, Integer pageSize, String sortBy, String order) {
        Sort sort = order.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Curso curso = cursoRepository.findById(cursoId)
                .orElseThrow(() -> new ResourceNotFoundException("Curso", "cursoId", cursoId));

        Page<Disciplina> disciplinasPage = disciplinaRepository.findByCursoId(curso.getId(), pageable);
        List<Disciplina> disciplinas = disciplinasPage.getContent();

        if (disciplinas.isEmpty()) {
            throw new APIException("Nenhuma disciplina encontrada.");
        }

        return buildDisciplinaResponse(disciplinasPage);
    }

    @Override
    public DisciplinaResponse searchAlunoByKeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sort = Sort.by("nome").ascending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<Disciplina> disciplinasPage = disciplinaRepository.findByNomeStartingWithIgnoreCase(keyword, pageable);

        List<Disciplina> disciplinas = disciplinasPage.getContent();

        if (disciplinas.isEmpty()) {
            throw new APIException("Nenhuma disciplina encontrado com: " + keyword);
        }

        return buildDisciplinaResponse(disciplinasPage);
    }

    @Override
    public DisciplinaResponse getProfessorDisciplinas (Integer pageNumber, Integer pageSize, String sortBy, String order, ProfessorDTO professorDTO) {
        Sort sort = order.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<Disciplina> disciplinasPage = disciplinaRepository.findByProfessorMatriculaFuncional(professorDTO.getMatriculaFuncional(), pageable);
        List<Disciplina> disciplinas = disciplinasPage.getContent();

        if (disciplinas.isEmpty()) {
            throw new APIException("Nenhuma disciplina encontrada.");
        }

        return buildDisciplinaResponse(disciplinasPage);
    }

    @Override
    public DisciplinaDTO createDisciplina(DisciplinaDTO disciplinaDTO) {
        Disciplina disciplina = modelMapper.map(disciplinaDTO, Disciplina.class);
        Disciplina disciplinaFromDB = disciplinaRepository.findById(disciplina.getCodigo())
                .orElse(null);

        if (disciplinaFromDB != null)
            throw new APIException("Disciplina já cadastrada.");

        String professorMatriculaFuncional = disciplinaDTO.getProfessor().getMatriculaFuncional();

        Professor professor = professorRepository.findByMatriculaFuncional(professorMatriculaFuncional)
                .orElseThrow(() -> new ResourceNotFoundException("Professor", "matrícula", professorMatriculaFuncional));

        disciplina.setProfessor(professor);

        Long cursoId = disciplinaDTO.getCurso().getId();

        Curso curso = cursoRepository.findById(cursoId)
                .orElseThrow(() -> new ResourceNotFoundException("Curso", "cursoId", cursoId));

        disciplina.setCurso(curso);
        Disciplina savedDisciplina = disciplinaRepository.save(disciplina);
        return modelMapper.map(savedDisciplina, DisciplinaDTO.class);
    }

    @Override
    public DisciplinaDTO updateDisciplina(DisciplinaDTO disciplinaDTO, String codigo, Long cursoId, String matriculaFuncional) {
        Disciplina disciplina = disciplinaRepository.findById(codigo)
                .orElseThrow(() -> new ResourceNotFoundException("Disciplina", "código", codigo));

        disciplina.setNome(disciplinaDTO.getNome());
        disciplina.setSemestre(disciplinaDTO.getSemestre());
        disciplina.setDescricao(disciplinaDTO.getDescricao());
        disciplina.setCargaHoraria(disciplinaDTO.getCargaHoraria());

        Professor professor = professorRepository.findByMatriculaFuncional(matriculaFuncional)
                .orElseThrow(() -> new ResourceNotFoundException("Professor", "matrícula", matriculaFuncional));

        Curso curso = cursoRepository.findById(cursoId)
                        .orElseThrow(() -> new ResourceNotFoundException("Curso", "cursoId", cursoId));

        disciplina.setCurso(curso);
        disciplina.setProfessor(professor);
        Disciplina disciplinaAtualizada = disciplinaRepository.save(disciplina);
        return modelMapper.map(disciplinaAtualizada, DisciplinaDTO.class);
    }

    @Override
    public DisciplinaDTO deleteDisciplina(String codigo) {
        Disciplina disciplina = disciplinaRepository.findById(codigo)
                .orElseThrow(() -> new ResourceNotFoundException("Disciplina", "código", codigo));

        DisciplinaDTO disciplinaDTO = modelMapper.map(disciplina, DisciplinaDTO.class);
        disciplinaRepository.delete(disciplina);
        return disciplinaDTO;
    }
}