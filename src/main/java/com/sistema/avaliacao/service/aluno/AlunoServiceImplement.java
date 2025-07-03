package com.sistema.avaliacao.service.aluno;

import com.sistema.avaliacao.exceptions.APIException;
import com.sistema.avaliacao.exceptions.ResourceNotFoundException;
import com.sistema.avaliacao.model.Aluno;
import com.sistema.avaliacao.payload.dto.AlunoDTO;
import com.sistema.avaliacao.payload.response.AlunoResponse;
import com.sistema.avaliacao.repositories.AlunoRepository;
import com.sistema.avaliacao.service.file.FileService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class AlunoServiceImplement implements AlunoService {

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;

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
    public AlunoResponse searchAlunoByKeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sort = Sort.by("nome").ascending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<Aluno> pageAlunos = alunoRepository.findByNomeStartingWithIgnoreCase(keyword, pageable);

        List<Aluno> alunos = pageAlunos.getContent();

        if (alunos.isEmpty()) {
            throw new APIException("Nenhum aluno encontrado com: " + keyword);
        }

        List<AlunoDTO> alunoDTOS = alunos.stream()
                .map(aluno -> modelMapper.map(aluno, AlunoDTO.class))
                .toList();

        AlunoResponse alunoResponse = new AlunoResponse();
        alunoResponse.setContent(alunoDTOS);
        alunoResponse.setPageNumber(pageAlunos.getNumber());
        alunoResponse.setPageSize(pageAlunos.getSize());
        alunoResponse.setTotalElements(pageAlunos.getTotalElements());
        alunoResponse.setTotalPages(pageAlunos.getTotalPages());
        alunoResponse.setLastPage(pageAlunos.isLast());

        return alunoResponse;
    }

    @Override
    public AlunoDTO createAluno(AlunoDTO alunoDTO) {
        Aluno aluno = modelMapper.map(alunoDTO, Aluno.class);
        Aluno alunoFromDB = alunoRepository.findById(aluno.getMatriculaAcademica())
                .orElse(null);

        if (alunoFromDB != null)
            throw new APIException("Aluno já cadastrado.");

        Aluno savedAluno = alunoRepository.save(aluno);
        return modelMapper.map(savedAluno, AlunoDTO.class);
    }

    @Override
    public AlunoDTO atualizarAlunoViaSuap(AlunoDTO alunoDTO, String matriculaAcademica) {
        Aluno aluno = alunoRepository.findById(matriculaAcademica)
                .orElseThrow(() -> new ResourceNotFoundException("Aluno", "matriculaAcademica", matriculaAcademica));

        aluno.setNome(alunoDTO.getNome());
        aluno.setEmailInstitucional(alunoDTO.getEmailInstitucional());
        aluno.setCurso(alunoDTO.getCurso());
        aluno.setMatriz(alunoDTO.getMatriz());
        aluno.setPeriodoReferencia(alunoDTO.getPeriodoReferencia());
        aluno.setSituacaoAluno(alunoDTO.getSituacaoAluno());

        Aluno alunoAtualizado = alunoRepository.save(aluno);

        return modelMapper.map(alunoAtualizado, AlunoDTO.class);
    }

    @Override
    public AlunoDTO updateAlunoImagem(String matriculaAcademica, MultipartFile imagem) throws IOException {
        Aluno alunoFromDB = alunoRepository.findById(matriculaAcademica)
                .orElseThrow(() -> new ResourceNotFoundException("Aluno", "matriculaAcademica", matriculaAcademica));

        String fileName = fileService.uploadImagem(path, imagem);
        alunoFromDB.setImagem(fileName);
        Aluno updatedAluno = alunoRepository.save(alunoFromDB);
        return modelMapper.map(updatedAluno, AlunoDTO.class);
    }

    public AlunoDTO updatePerfilDescricao(String matriculaAcademica, AlunoDTO alunoDTO) {
        Aluno aluno = alunoRepository.findById(matriculaAcademica)
                .orElseThrow(() -> new ResourceNotFoundException("Aluno", "matriculaAcademica", matriculaAcademica));

        aluno.setPerfilDescricao(alunoDTO.getPerfilDescricao());
        Aluno alunoAtualizado = alunoRepository.save(aluno);
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
