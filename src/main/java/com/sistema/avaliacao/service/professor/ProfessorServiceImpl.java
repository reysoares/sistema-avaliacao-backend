package com.sistema.avaliacao.service.professor;

import java.io.IOException;
import java.util.List;

import com.sistema.avaliacao.enums.Perfil;
import com.sistema.avaliacao.model.Funcao;
import com.sistema.avaliacao.payload.dto.UsuarioCadastroDTO;
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

import com.sistema.avaliacao.exceptions.APIException;
import com.sistema.avaliacao.exceptions.ResourceNotFoundException;
import com.sistema.avaliacao.model.Professor;
import com.sistema.avaliacao.payload.dto.ProfessorDTO;
import com.sistema.avaliacao.payload.response.ProfessorResponse;
import com.sistema.avaliacao.repositories.ProfessorRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProfessorServiceImpl  implements ProfessorService{

    @Autowired
    ProfessorRepository professorRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    private FuncaoRepository funcaoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    FileService fileService;

    @Value("${project.image}")
    private String path;

    private ProfessorResponse buildProfessorResponse(Page<Professor> professorPage) {
        List<ProfessorDTO> dtos = professorPage.getContent().stream()
                .map(av -> modelMapper.map(av, ProfessorDTO.class))
                .toList();

        ProfessorResponse professorResponse = new ProfessorResponse();
        professorResponse.setContent(dtos);
        professorResponse.setPageNumber(professorPage.getNumber());
        professorResponse.setPageSize(professorPage.getSize());
        professorResponse.setTotalElements(professorPage.getTotalElements());
        professorResponse.setTotalPages(professorPage.getTotalPages());
        professorResponse.setLastPage(professorPage.isLast());
        return professorResponse;
    }

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

        return buildProfessorResponse(professorPage);
    }

    @Override
    public ProfessorResponse searchProfessorByKeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sort = Sort.by("nome").ascending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<Professor> pageProfessores = professorRepository.findByNomeStartingWithIgnoreCase(keyword, pageable);

        List<Professor> professores = pageProfessores.getContent();

        if (professores.isEmpty()) {
            throw new APIException("Nenhum professor encontrado com: " + keyword);
        }

        return buildProfessorResponse(pageProfessores);
    }

    @Override
    public ProfessorDTO getProfessorDTO(Long id, boolean isPerfilProprio) {
        Professor professor = professorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Professor", "id", id));

        ProfessorDTO professorDTO = modelMapper.map(professor, ProfessorDTO.class);

        if (!isPerfilProprio) {
            professorDTO.setEmailInstitucional(null);
            professorDTO.setDataNascimento(null);
            professorDTO.setMatriculaFuncional(null);
        }

        return professorDTO;
    }

    @Override
    public ProfessorDTO createProfessor(UsuarioCadastroDTO usuarioDTO) {
        if (professorRepository.findByMatriculaFuncional(usuarioDTO.getMatricula()).isPresent()) {
            throw new APIException("Professor já cadastrado.");
        }

        Professor professor = new Professor();
        professor.setNome(usuarioDTO.getNome());
        professor.setEmailInstitucional(usuarioDTO.getEmailInstitucional());
        professor.setDataNascimento(usuarioDTO.getDataNascimento());
        professor.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
        professor.setMatriculaFuncional(usuarioDTO.getMatricula());

        Funcao funcaoProfessor = funcaoRepository.findByPerfil(Perfil.PROFESSOR)
                .orElseThrow(() -> new APIException("Função PROFESSOR não encontrada."));
        professor.setFuncao(funcaoProfessor);

        Professor saved = professorRepository.save(professor);
        return modelMapper.map(saved, ProfessorDTO.class);
    }

    @Override
    public ProfessorDTO atualizarProfessor(ProfessorDTO professorDTO, String matriculaFuncional) {
        Professor professor = professorRepository.findByMatriculaFuncional(matriculaFuncional)
                .orElseThrow(() -> new ResourceNotFoundException("Professor", "matriculaFuncional", matriculaFuncional));

        professor.setNome(professorDTO.getNome());
        professor.setDescricao(professorDTO.getDescricao());
        professor.setDataNascimento(professorDTO.getDataNascimento());
        professor.setEmailInstitucional(professorDTO.getEmailInstitucional());
        professor.setDepartamento(professorDTO.getDepartamento());
        professor.setUnidadeEnsino(professorDTO.getUnidadeEnsino());
        professor.setAreaAtuacao(professorDTO.getAreaAtuacao());

        Professor professorAtualizado = professorRepository.save(professor);
        return modelMapper.map(professorAtualizado, ProfessorDTO.class);
    }

    @Override
    public ProfessorDTO updateProfessorImagem(String matriculaFuncional, MultipartFile imagem) throws IOException {
        Professor professorFromDB = professorRepository.findByMatriculaFuncional(matriculaFuncional)
                .orElseThrow(() -> new ResourceNotFoundException("Professor", "matriculaFuncional", matriculaFuncional));

        String fileName = fileService.uploadImagem(path, imagem);
        professorFromDB.setImagem(fileName);
        Professor updatedProfessor = professorRepository.save(professorFromDB);
        return modelMapper.map(updatedProfessor, ProfessorDTO.class);
    }

    @Override
    public ProfessorDTO deleteProfessor(String matriculaFuncional) {
        Professor professor = professorRepository.findByMatriculaFuncional(matriculaFuncional)
                .orElseThrow(() -> new ResourceNotFoundException("Professor", "matriculaFuncional", matriculaFuncional));

        ProfessorDTO professorDTO = modelMapper.map(professor, ProfessorDTO.class);
        professorRepository.delete(professor);
        return professorDTO;
    }

}
