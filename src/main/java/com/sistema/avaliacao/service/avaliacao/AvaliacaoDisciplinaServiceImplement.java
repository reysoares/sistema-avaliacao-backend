package com.sistema.avaliacao.service.avaliacao;

import com.sistema.avaliacao.exceptions.APIException;
import com.sistema.avaliacao.exceptions.ResourceNotFoundException;
import com.sistema.avaliacao.model.*;
import com.sistema.avaliacao.payload.dto.AlunoDTO;
import com.sistema.avaliacao.payload.dto.AvaliacaoDisciplinaDTO;
import com.sistema.avaliacao.payload.dto.DisciplinaDTO;
import com.sistema.avaliacao.payload.response.AvaliacaoDisciplinaResponse;
import com.sistema.avaliacao.repositories.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AvaliacaoDisciplinaServiceImplement implements AvaliacaoDisciplinaService {

    @Autowired
    private AvaliacaoDisciplinaRepository avaliacaoDisciplinaRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Autowired
    private ModelMapper modelMapper;

    private AvaliacaoDisciplinaResponse buildAvaliacaoDisciplinaResponse(Page<AvaliacaoDisciplina> page) {
        List<AvaliacaoDisciplinaDTO> dtos = page.getContent().stream()
                .map(av -> modelMapper.map(av, AvaliacaoDisciplinaDTO.class))
                .toList();

        AvaliacaoDisciplinaResponse response = new AvaliacaoDisciplinaResponse();
        response.setContent(dtos);
        response.setPageNumber(page.getNumber());
        response.setPageSize(page.getSize());
        response.setTotalElements(page.getTotalElements());
        response.setTotalPages(page.getTotalPages());
        response.setLastPage(page.isLast());
        return response;
    }

    @Override
    public AvaliacaoDisciplinaResponse getAlunoAvaliacoes(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder, AlunoDTO alunoDTO) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        Page<AvaliacaoDisciplina> avaliacaoDisciplinaPage = avaliacaoDisciplinaRepository.findByAlunoMatriculaAcademica(alunoDTO.getMatriculaAcademica(), pageDetails);
        List<AvaliacaoDisciplina> avaliacoesDisciplina = avaliacaoDisciplinaPage.getContent();
        if(avaliacoesDisciplina.isEmpty())
            throw new APIException("Nenhuma avaliação feita até agora.");

        return buildAvaliacaoDisciplinaResponse(avaliacaoDisciplinaPage);
    }

    @Override
    public AvaliacaoDisciplinaResponse getDisciplinaAvaliacoes(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder, DisciplinaDTO disciplinaDTO) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        Page<AvaliacaoDisciplina> avaliacaoDisciplinaPage = avaliacaoDisciplinaRepository.findByDisciplinaCodigo(disciplinaDTO.getCodigo(), pageDetails);
        List<AvaliacaoDisciplina> avaliacoesDisciplina = avaliacaoDisciplinaPage.getContent();
        if(avaliacoesDisciplina.isEmpty())
            throw new APIException("Nenhuma avaliação recebida até agora.");

        return buildAvaliacaoDisciplinaResponse(avaliacaoDisciplinaPage);
    }

    @Override
    public AvaliacaoDisciplinaDTO createAvaliacaoDisciplina(AvaliacaoDisciplinaDTO avaliacaoDisciplinaDTO) {
        AvaliacaoDisciplina avaliacaoDisciplina = modelMapper.map(avaliacaoDisciplinaDTO, AvaliacaoDisciplina.class);

        if (avaliacaoDisciplina.getAluno() == null || avaliacaoDisciplina.getDisciplina() == null) {
            throw new APIException("Aluno ou Disciplina não informados corretamente.");
        }

        String matriculaAluno = avaliacaoDisciplina.getAluno().getMatriculaAcademica();
        String matriculaDisciplina = avaliacaoDisciplina.getDisciplina().getCodigo();

        Aluno aluno = alunoRepository.findById(matriculaAluno)
                .orElseThrow(() -> new APIException("Aluno com matrícula " + matriculaAluno + " não encontrado."));
        Disciplina disciplina = disciplinaRepository.findById(matriculaDisciplina)
                .orElseThrow(() -> new APIException("Disciplina com código: " + matriculaDisciplina + " não encontrado."));

        if (avaliacaoDisciplinaRepository.existsByAlunoMatriculaAcademicaAndDisciplinaCodigo(matriculaAluno, matriculaDisciplina)) {
            throw new APIException("Este aluno já avaliou esta Disciplina.");
        }

        avaliacaoDisciplina.setAluno(aluno);
        avaliacaoDisciplina.setDisciplina(disciplina);
        avaliacaoDisciplina.setId(null); // para garantir nova inserção

        AvaliacaoDisciplina savedAvaliacaoDisciplina = avaliacaoDisciplinaRepository.save(avaliacaoDisciplina);
        return modelMapper.map(savedAvaliacaoDisciplina, AvaliacaoDisciplinaDTO.class);
    }

    @Override
    public AvaliacaoDisciplinaDTO updateAvaliacaoDisciplina(AvaliacaoDisciplinaDTO avaliacaoDisciplinaDTO, String matriculaAcademica, Long id) {
        // Busca a avaliação existente
        AvaliacaoDisciplina avaliacaoExistente = avaliacaoDisciplinaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Avaliação", "id", id));

        // Verifica se o aluno tem permissão para atualizar
        if (!avaliacaoExistente.getAluno().getMatriculaAcademica().equals(matriculaAcademica)) {
            throw new APIException("Você não tem permissão para atualizar esta avaliação.");
        }

        // Atualiza os campos permitidos
        avaliacaoExistente.setNotaConteudo(avaliacaoDisciplinaDTO.getNotaConteudo());
        avaliacaoExistente.setNotaCargaTrabalho(avaliacaoDisciplinaDTO.getNotaCargaTrabalho());
        avaliacaoExistente.setNotaInfraestrutura(avaliacaoDisciplinaDTO.getNotaInfraestrutura());

        // Atualiza o comentário, se existir (herdado de AvaliacaoDTO)
        if (avaliacaoDisciplinaDTO.getComentario() != null) {
            avaliacaoExistente.setComentario(avaliacaoDisciplinaDTO.getComentario());
        }

        // Salva e retorna a avaliação atualizada
        AvaliacaoDisciplina atualizada = avaliacaoDisciplinaRepository.save(avaliacaoExistente);
        return modelMapper.map(atualizada, AvaliacaoDisciplinaDTO.class);
    }


    @Override
    public AvaliacaoDisciplinaDTO deleteAvaliacaoDisciplina(String matriculaAcademica, Long id) {
        AvaliacaoDisciplina avaliacaoDisciplina = avaliacaoDisciplinaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Avaliação", "id", id));

        if (!avaliacaoDisciplina.getAluno().getMatriculaAcademica().equals(matriculaAcademica)) {
            throw new APIException("Você não tem permissão para excluir esta avaliação.");
        }

        AvaliacaoDisciplinaDTO avaliacaoDisciplinaDTO = modelMapper.map(avaliacaoDisciplina, AvaliacaoDisciplinaDTO.class);
        avaliacaoDisciplinaRepository.delete(avaliacaoDisciplina);
        return avaliacaoDisciplinaDTO;
    }
}
