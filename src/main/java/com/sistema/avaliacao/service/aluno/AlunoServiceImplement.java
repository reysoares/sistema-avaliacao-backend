package com.sistema.avaliacao.service.aluno;

import com.sistema.avaliacao.exceptions.APIException;
import com.sistema.avaliacao.exceptions.ResourceNotFoundException;
import com.sistema.avaliacao.model.Aluno;
import com.sistema.avaliacao.payload.dto.AlunoDTO;
import com.sistema.avaliacao.payload.response.AlunoResponse;
import com.sistema.avaliacao.repositories.AlunoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlunoServiceImplement implements AlunoService {

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public AlunoResponse getAllAlunos(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        Page<Aluno> alunoPage = alunoRepository.findAll(pageDetails);
        List<Aluno> alunos = alunoPage.getContent();
        if (alunos.isEmpty())
            throw new APIException("Nenhum aluno criado até agora.");

        List <AlunoDTO> alunoDTOS = alunos.stream()
                .map(aluno -> modelMapper.map(aluno, AlunoDTO.class))
                .toList();

        AlunoResponse alunoResponse = new AlunoResponse();
        alunoResponse.setContent(alunoDTOS);
        alunoResponse.setPageNumber(alunoPage.getNumber());
        alunoResponse.setPageSize(alunoPage.getSize());
        alunoResponse.setTotalElements(alunoPage.getTotalElements());
        alunoResponse.setTotalPages(alunoPage.getTotalPages());
        alunoResponse.setLastPage(alunoPage.isLast());
        return alunoResponse;
    }

    @Override
    public AlunoDTO creatAluno(AlunoDTO alunoDTO) {
        Aluno aluno = modelMapper.map(alunoDTO, Aluno.class);
        Aluno alunoFromDB = alunoRepository.findById(aluno.getMatriculaAcademica())
                .orElse(null);

        if (alunoFromDB != null)
            throw new APIException("Aluno já cadastrado.");

        Aluno savedAluno = alunoRepository.save(aluno);
        return modelMapper.map(savedAluno, AlunoDTO.class);
    }

    @Override
    public AlunoDTO updateAluno(AlunoDTO alunoDTO, String matriculaAcademica) {
        // Busca o aluno existente
        Aluno alunoExistente = alunoRepository.findById(matriculaAcademica)
                .orElseThrow(() -> new ResourceNotFoundException("Aluno", "matriculaAcademica", matriculaAcademica));

        String novoEmail = alunoDTO.getEmail();

        if (novoEmail != null && !novoEmail.isBlank() && !novoEmail.equals(alunoExistente.getEmail())) {
            // Verifica se já existe outro aluno com esse e-mail
            Optional<Aluno> alunoComMesmoEmail = alunoRepository.findByEmail(novoEmail);

            if (alunoComMesmoEmail.isPresent() &&
                    !alunoComMesmoEmail.get().getMatriculaAcademica().equals(matriculaAcademica)) {
                throw new DataIntegrityViolationException("Já existe outro aluno cadastrado com este e-mail.");
            }

            // Atualiza o e-mail
            alunoExistente.setEmail(novoEmail);
        }

        Aluno alunoAtualizado = alunoRepository.save(alunoExistente);
        return modelMapper.map(alunoAtualizado, AlunoDTO.class);
    }


    @Override
    public AlunoDTO deleteAluno(String matriculaAcademica) {
        Aluno aluno = alunoRepository.findById(matriculaAcademica)
                .orElseThrow(() -> new ResourceNotFoundException("Aluno", "matriculaAcademica", matriculaAcademica));

        AlunoDTO alunoDTO = modelMapper.map(aluno, AlunoDTO.class);
        alunoRepository.delete(aluno);
        return alunoDTO;
    }
}
