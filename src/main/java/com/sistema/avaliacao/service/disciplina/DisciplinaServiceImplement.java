package com.sistema.avaliacao.service.disciplina;

import com.sistema.avaliacao.exceptions.APIException;
import com.sistema.avaliacao.exceptions.ResourceNotFoundException;
import com.sistema.avaliacao.model.Disciplina;
import com.sistema.avaliacao.model.Professor;
import com.sistema.avaliacao.payload.dto.DisciplinaDTO;
import com.sistema.avaliacao.payload.dto.ProfessorDTO;
import com.sistema.avaliacao.payload.response.DisciplinaResponse;
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
    private ModelMapper modelMapper;

    private DisciplinaResponse buildDisciplinaResponse(Page<Disciplina> page) {
        List<DisciplinaDTO> dtos = page.getContent().stream()
                .map(av -> modelMapper.map(av, DisciplinaDTO.class))
                .toList();

        DisciplinaResponse response = new DisciplinaResponse();
        response.setContent(dtos);
        response.setPageNumber(page.getNumber());
        response.setPageSize(page.getSize());
        response.setTotalElements(page.getTotalElements());
        response.setTotalPages(page.getTotalPages());
        response.setLastPage(page.isLast());
        return response;
    }

    @Override
    public DisciplinaResponse getAllDisciplinas(Integer pageNumber, Integer pageSize, String sortBy, String order) {
        // Configura a ordenação
        Sort sort = order.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        // Configura a paginação
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        // Busca as disciplinas paginadas
        Page<Disciplina> disciplinasPage = disciplinaRepository.findAll(pageable);
        List<Disciplina> disciplinas = disciplinasPage.getContent();

        // Verifica se há disciplinas
        if (disciplinas.isEmpty()) {
            throw new APIException("Nenhuma disciplina encontrada.");
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
        // Verifica se a disciplina já existe
        if (disciplinaRepository.existsById(disciplinaDTO.getCodigo())) {
            throw new APIException("Já existe uma disciplina com este código.");
        }

        // Converte DTO para entidade
        Disciplina disciplina = modelMapper.map(disciplinaDTO, Disciplina.class);

        // Busca professor no banco pela matrícula para associar a entidade gerenciada
        String matricula = disciplinaDTO.getProfessor().getMatriculaFuncional();
        Professor professor = professorRepository.findById(matricula)
                .orElseThrow(() -> new ResourceNotFoundException("Professor", "matrícula", matricula));

        // Associa o professor carregado à disciplina
        disciplina.setProfessor(professor);

        // Salva no banco
        Disciplina savedDisciplina = disciplinaRepository.save(disciplina);

        // Converte de volta para DTO e retorna
        return modelMapper.map(savedDisciplina, DisciplinaDTO.class);
    }

    @Override
    public DisciplinaDTO updateDisciplina(DisciplinaDTO disciplinaDTO, String codigo) {
        // Busca a disciplina existente
        Disciplina disciplinaExistente = disciplinaRepository.findById(codigo)
                .orElseThrow(() -> new ResourceNotFoundException("Disciplina", "código", codigo));

        // Atualiza os dados
        disciplinaExistente.setNome(disciplinaDTO.getNome());
        disciplinaExistente.setCurso(disciplinaDTO.getCurso());
        disciplinaExistente.setSemestre(disciplinaDTO.getSemestre());
        disciplinaExistente.setCargaHoraria(disciplinaDTO.getCargaHoraria());

        // Salva as alterações
        Disciplina disciplinaAtualizada = disciplinaRepository.save(disciplinaExistente);

        // Retorna como DTO
        return modelMapper.map(disciplinaAtualizada, DisciplinaDTO.class);
    }

    @Override
    public DisciplinaDTO deleteDisciplina(String codigo) {
        // Verifica se a disciplina existe
        Disciplina disciplina = disciplinaRepository.findById(codigo)
                .orElseThrow(() -> new ResourceNotFoundException("Disciplina", "código", codigo));

        // Converte para DTO antes de deletar
        DisciplinaDTO disciplinaDTO = modelMapper.map(disciplina, DisciplinaDTO.class);

        // Remove do banco
        disciplinaRepository.delete(disciplina);

        return disciplinaDTO;
    }
}