package com.sistema.avaliacao.service.avaliacao;

import com.sistema.avaliacao.exceptions.APIException;
import com.sistema.avaliacao.exceptions.ResourceNotFoundException;
import com.sistema.avaliacao.model.Aluno;
import com.sistema.avaliacao.model.AvaliacaoProfessor;
import com.sistema.avaliacao.model.Professor;
import com.sistema.avaliacao.payload.dto.AlunoDTO;
import com.sistema.avaliacao.payload.dto.AvaliacaoProfessorDTO;
import com.sistema.avaliacao.payload.dto.ProfessorDTO;
import com.sistema.avaliacao.payload.response.AvaliacaoProfessorResponse;
import com.sistema.avaliacao.repositories.AlunoRepository;
import com.sistema.avaliacao.repositories.AvaliacaoProfessorRepository;
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
public class AvaliacaoProfessorServiceImplement implements AvaliacaoProfessorService {

    @Autowired
    private AvaliacaoProfessorRepository avaliacaoProfessorRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private ModelMapper modelMapper;

    private AvaliacaoProfessorResponse buildAvaliacaoProfessorResponse(Page<AvaliacaoProfessor> page) {
        List<AvaliacaoProfessorDTO> dtos = page.getContent().stream()
                .map(av -> modelMapper.map(av, AvaliacaoProfessorDTO.class))
                .toList();

        AvaliacaoProfessorResponse response = new AvaliacaoProfessorResponse();
        response.setContent(dtos);
        response.setPageNumber(page.getNumber());
        response.setPageSize(page.getSize());
        response.setTotalElements(page.getTotalElements());
        response.setTotalPages(page.getTotalPages());
        response.setLastPage(page.isLast());
        return response;
    }

    @Override
    public AvaliacaoProfessorResponse getAlunoAvaliacoes(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder, AlunoDTO alunoDTO) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        Page<AvaliacaoProfessor> avaliacaoProfessorPage = avaliacaoProfessorRepository.findByAlunoMatriculaAcademica(alunoDTO.getMatriculaAcademica(), pageDetails);
        List<AvaliacaoProfessor> avaliacoesProfessor = avaliacaoProfessorPage.getContent();
        if(avaliacoesProfessor.isEmpty())
            throw new APIException("Nenhuma avaliação feita até agora.");

        return buildAvaliacaoProfessorResponse(avaliacaoProfessorPage);
    }

    @Override
    public AvaliacaoProfessorResponse getProfessorAvaliacoes(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder, ProfessorDTO professorDTO) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        Page<AvaliacaoProfessor> avaliacaoProfessorPage = avaliacaoProfessorRepository.findByProfessorMatriculaFuncional(professorDTO.getMatriculaFuncional(), pageDetails);
        List<AvaliacaoProfessor> avaliacoesProfessor = avaliacaoProfessorPage.getContent();
        if(avaliacoesProfessor.isEmpty())
            throw new APIException("Nenhuma avaliação recebida até agora.");

        return buildAvaliacaoProfessorResponse(avaliacaoProfessorPage);
    }

    @Override
    public AvaliacaoProfessorDTO createAvaliacaoProfessor(AvaliacaoProfessorDTO avaliacaoProfessorDTO) {
        AvaliacaoProfessor avaliacaoProfessor = modelMapper.map(avaliacaoProfessorDTO, AvaliacaoProfessor.class);

        if (avaliacaoProfessor.getAluno() == null || avaliacaoProfessor.getProfessor() == null) {
            throw new APIException("Aluno ou Professor não informados corretamente.");
        }

        String matriculaAluno = avaliacaoProfessor.getAluno().getMatriculaAcademica();
        String matriculaProfessor = avaliacaoProfessor.getProfessor().getMatriculaFuncional();

        Aluno aluno = alunoRepository.findById(matriculaAluno)
                .orElseThrow(() -> new APIException("Aluno com matrícula " + matriculaAluno + " não encontrado."));
        Professor professor = professorRepository.findById(matriculaProfessor)
                .orElseThrow(() -> new APIException("Professor com matrícula funcional " + matriculaProfessor + " não encontrado."));

        if (avaliacaoProfessorRepository.existsByAlunoMatriculaAcademicaAndProfessorMatriculaFuncional(matriculaAluno, matriculaProfessor)) {
            throw new APIException("Este aluno já avaliou este professor.");
        }

        avaliacaoProfessor.setAluno(aluno);
        avaliacaoProfessor.setProfessor(professor);
        avaliacaoProfessor.setId(null); // para garantir nova inserção

        AvaliacaoProfessor savedAvaliacaoProfessor = avaliacaoProfessorRepository.save(avaliacaoProfessor);
        return modelMapper.map(savedAvaliacaoProfessor, AvaliacaoProfessorDTO.class);
    }


    @Override
    public AvaliacaoProfessorDTO updateAvaliacaoProfessor(AvaliacaoProfessorDTO avaliacaoProfessorDTO, String matriculaAcademica, Long id) {
        // Busca a avaliação existente
        AvaliacaoProfessor avaliacaoExistente = avaliacaoProfessorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Avaliação", "id", id));

        // Verifica permissão do aluno
        if (!avaliacaoExistente.getAluno().getMatriculaAcademica().equals(matriculaAcademica)) {
            throw new APIException("Você não tem permissão para atualizar esta avaliação.");
        }

        // Atualiza as notas
        avaliacaoExistente.setNotaDidatica(avaliacaoProfessorDTO.getNotaDidatica());
        avaliacaoExistente.setNotaDominioConteudo(avaliacaoProfessorDTO.getNotaDominioConteudo());
        avaliacaoExistente.setNotaInteracaoAlunos(avaliacaoProfessorDTO.getNotaInteracaoAlunos());

        // Atualiza comentário, se existir
        if (avaliacaoProfessorDTO.getComentario() != null) {
            avaliacaoExistente.setComentario(avaliacaoProfessorDTO.getComentario());
        }

        // Salva a avaliação atualizada
        AvaliacaoProfessor atualizada = avaliacaoProfessorRepository.save(avaliacaoExistente);

        // Retorna o DTO atualizado
        return modelMapper.map(atualizada, AvaliacaoProfessorDTO.class);
    }

    @Override
    public AvaliacaoProfessorDTO deleteAvaliacaoProfessor(String matriculaAcademica, Long id) {
        AvaliacaoProfessor avaliacaoProfessor = avaliacaoProfessorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Avaliação", "id", id));

        if (!avaliacaoProfessor.getAluno().getMatriculaAcademica().equals(matriculaAcademica)) {
            throw new APIException("Você não tem permissão para excluir esta avaliação.");
        }

        AvaliacaoProfessorDTO avaliacaoProfessorDTO = modelMapper.map(avaliacaoProfessor, AvaliacaoProfessorDTO.class);
        avaliacaoProfessorRepository.delete(avaliacaoProfessor);
        return avaliacaoProfessorDTO;
    }
}
