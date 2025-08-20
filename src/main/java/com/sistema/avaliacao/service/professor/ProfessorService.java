package com.sistema.avaliacao.service.professor;

import com.sistema.avaliacao.payload.dto.ProfessorDTO;
import com.sistema.avaliacao.payload.dto.UsuarioCadastroDTO;
import com.sistema.avaliacao.payload.response.ProfessorResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProfessorService {

    ProfessorResponse getAllProfessor(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    ProfessorResponse searchProfessorByKeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    ProfessorDTO getProfessorDTO(Long id, boolean isPerfilProprio);

    ProfessorDTO createProfessor(UsuarioCadastroDTO usuarioDTO);

    ProfessorDTO atualizarProfessor(ProfessorDTO professorDTO, String matriculaFuncional);

    ProfessorDTO updateProfessorImagem(String matriculaFuncional, MultipartFile imagem) throws IOException;

    ProfessorDTO deleteProfessor (String matriculaFuncional);

}
