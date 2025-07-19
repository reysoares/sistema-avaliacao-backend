package com.sistema.avaliacao.service.aluno;

import com.sistema.avaliacao.enums.Perfil;
import com.sistema.avaliacao.exceptions.APIException;
import com.sistema.avaliacao.exceptions.ResourceNotFoundException;
import com.sistema.avaliacao.model.Aluno;
import com.sistema.avaliacao.model.Curso;
import com.sistema.avaliacao.model.Funcao;
import com.sistema.avaliacao.payload.dto.*;
import com.sistema.avaliacao.payload.dto.UsuarioCadastroDTO;
import com.sistema.avaliacao.payload.response.AlunoResponse;
import com.sistema.avaliacao.repositories.AlunoRepository;
import com.sistema.avaliacao.repositories.CursoRepository;
import com.sistema.avaliacao.repositories.FuncaoRepository;
import com.sistema.avaliacao.repositories.UsuarioRepository;
import com.sistema.avaliacao.service.file.FileService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class AlunoServiceImplement implements AlunoService {

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private FuncaoRepository funcaoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;

    private AlunoResponse buildAlunoResponse(Page<Aluno> alunoPage) {
        List<AlunoDTO> dtos = alunoPage.getContent().stream()
                .map(av -> modelMapper.map(av, AlunoDTO.class))
                .toList();

        AlunoResponse alunoResponse = new AlunoResponse();
        alunoResponse.setContent(dtos);
        alunoResponse.setPageNumber(alunoPage.getNumber());
        alunoResponse.setPageSize(alunoPage.getSize());
        alunoResponse.setTotalElements(alunoPage.getTotalElements());
        alunoResponse.setTotalPages(alunoPage.getTotalPages());
        alunoResponse.setLastPage(alunoPage.isLast());
        return alunoResponse;
    }

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

        return buildAlunoResponse(alunoPage);
    }

    @Override
    public AlunoResponse getAlunosByCurso(Long cursoId, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);

        Curso curso = cursoRepository.findById(cursoId)
                .orElseThrow(() -> new ResourceNotFoundException("Curso", "cursoId", cursoId));

        Page<Aluno> alunoPage = alunoRepository.findByCursoId(curso.getId(), pageDetails);
        List<Aluno> alunos = alunoPage.getContent();

        if (alunos.isEmpty())
            throw new APIException("Nenhum aluno encontrado.");

        return buildAlunoResponse(alunoPage);
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

        return buildAlunoResponse(pageAlunos);
    }

    @Override
    public AlunoDTO getAlunoDTO(Long id, boolean isPerfilProprio) {
        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aluno", "id", id));

        AlunoDTO alunoDTO = modelMapper.map(aluno, AlunoDTO.class);

        if (!isPerfilProprio) {
            alunoDTO.setEmailInstitucional(null);
            alunoDTO.setDataNascimento(null);
            alunoDTO.setMatriculaAcademica(null);
            alunoDTO.setMatriz(null);
            alunoDTO.setPeriodoReferencia(0);
            alunoDTO.setSituacaoAluno(null);
        }

        return alunoDTO;

    }

    @Override
    public AlunoDTO createAluno(UsuarioCadastroDTO usuarioDTO) {
        if (alunoRepository.findByMatriculaAcademica(usuarioDTO.getMatricula()).isPresent()) {
            throw new APIException("Aluno já cadastrado.");
        }

        Aluno aluno = new Aluno();
        aluno.setNome(usuarioDTO.getNome());
        aluno.setEmailInstitucional(usuarioDTO.getEmailInstitucional());
        aluno.setDataNascimento(usuarioDTO.getDataNascimento());
        aluno.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
        aluno.setMatriculaAcademica(usuarioDTO.getMatricula());

        Funcao funcaoAluno = funcaoRepository.findByPerfil(Perfil.ALUNO)
                .orElseThrow(() -> new APIException("Função ALUNO não encontrada."));
        aluno.setFuncao(funcaoAluno);

        Aluno saved = alunoRepository.save(aluno);
        return modelMapper.map(saved, AlunoDTO.class);
    }

    @Override
    public AlunoDTO atualizarAluno(AlunoDTO alunoDTO, String matriculaAcademica, Long cursoId) {
        Aluno aluno = alunoRepository.findByMatriculaAcademica(matriculaAcademica)
                .orElseThrow(() -> new ResourceNotFoundException("Aluno", "matriculaAcademica", matriculaAcademica));

        aluno.setNome(alunoDTO.getNome());
        aluno.setDescricao(alunoDTO.getDescricao());
        aluno.setDataNascimento(alunoDTO.getDataNascimento());
        aluno.setEmailInstitucional(alunoDTO.getEmailInstitucional());
        aluno.setMatriz(alunoDTO.getMatriz());
        aluno.setPeriodoReferencia(alunoDTO.getPeriodoReferencia());
        aluno.setSituacaoAluno(alunoDTO.getSituacaoAluno());

        Curso curso = cursoRepository.findById(cursoId)
                        .orElseThrow(() -> new ResourceNotFoundException("Curso", "cursoId", cursoId));

        aluno.setCurso(curso);
        Aluno alunoAtualizado = alunoRepository.save(aluno);
        return modelMapper.map(alunoAtualizado, AlunoDTO.class);
    }

    @Override
    public AlunoDTO updateAlunoImagem(String matriculaAcademica, MultipartFile imagem) throws IOException {
        Aluno alunoFromDB = alunoRepository.findByMatriculaAcademica(matriculaAcademica)
                .orElseThrow(() -> new ResourceNotFoundException("Aluno", "matriculaAcademica", matriculaAcademica));

        String fileName = fileService.uploadImagem(path, imagem);
        alunoFromDB.setImagem(fileName);
        Aluno updatedAluno = alunoRepository.save(alunoFromDB);
        return modelMapper.map(updatedAluno, AlunoDTO.class);
    }

    @Override
    public AlunoDTO deleteAluno(String matriculaAcademica) {
        Aluno aluno = alunoRepository.findByMatriculaAcademica(matriculaAcademica)
                .orElseThrow(() -> new ResourceNotFoundException("Aluno", "matriculaAcademica", matriculaAcademica));

        AlunoDTO alunoDTO = modelMapper.map(aluno, AlunoDTO.class);
        alunoRepository.delete(aluno);
        return alunoDTO;
    }
}
