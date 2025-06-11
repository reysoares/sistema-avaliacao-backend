package com.sistema.avaliacao.service.professor;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.sistema.avaliacao.exceptions.APIException;
import com.sistema.avaliacao.exceptions.ResourceNotFoundException;
import com.sistema.avaliacao.model.Professor;
import com.sistema.avaliacao.payload.dto.ProfessorDTO;
import com.sistema.avaliacao.payload.response.ProfessorResponse;
import com.sistema.avaliacao.repositories.ProfessorRepository;
import org.springframework.stereotype.Service;

@Service
public class ProfessorServiceImpl  implements ProfessorService{

    @Autowired
    ProfessorRepository professorRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public ProfessorResponse getAllProfessor(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        Page<Professor> professorPage = professorRepository.findAll(pageDetails);
        List<Professor> professores = professorPage.getContent();
        if (professores.isEmpty())
            throw new APIException("Nenhum professor criado até agora.");

        List <ProfessorDTO> professorDTOS = professores.stream()
                .map(professor -> modelMapper.map(professor, ProfessorDTO.class))
                .toList();

        ProfessorResponse professorResponse = new ProfessorResponse();
        professorResponse.setContent(professorDTOS);
        professorResponse.setPageNumber(professorPage.getNumber());
        professorResponse.setPageSize(professorPage.getSize());
        professorResponse.setTotalElements(professorPage.getTotalElements());
        professorResponse.setTotalPages(professorPage.getTotalPages());
        professorResponse.setLastPage(professorPage.isLast());
        return professorResponse;
    }

    @Override
    public ProfessorDTO createProfessor(ProfessorDTO professorDTO) {
        Professor professor = modelMapper.map(professorDTO, Professor.class);
        Professor professorFromDB = professorRepository.findById(professor.getMatriculaFuncional())
                .orElse(null);

        if (professorFromDB != null)
            throw new APIException("Professor já cadastrado.");

        Professor savedProfessor = professorRepository.save(professor);
        return modelMapper.map(savedProfessor, ProfessorDTO.class);
    }

    @Override
    public ProfessorDTO updateProfessor(ProfessorDTO professorDTO, String matriculaFuncional) {
        Professor professor = modelMapper.map(professorDTO, Professor.class);
        Professor savedProfessor = professorRepository.findById(matriculaFuncional)
                .orElseThrow(() -> new ResourceNotFoundException("Professor", "matriculaFuncional", matriculaFuncional));

        professor.setMatriculaFuncional(matriculaFuncional);
        savedProfessor = professorRepository.save(professor);
        return modelMapper.map(savedProfessor, ProfessorDTO.class);
    }

    @Override
    public ProfessorDTO deleteProfessor(String matriculaFuncional) {
        Professor professor = professorRepository.findById(matriculaFuncional)
                .orElseThrow(() -> new ResourceNotFoundException("Professor", "matriculaFuncional", matriculaFuncional));

        ProfessorDTO professorDTO = modelMapper.map(professor, ProfessorDTO.class);
        professorRepository.delete(professor);
        return professorDTO;
    }
    
}
