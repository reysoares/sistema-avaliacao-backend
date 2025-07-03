package com.sistema.avaliacao.service.professor;

import com.sistema.avaliacao.payload.dto.ProfessorDTO;
import com.sistema.avaliacao.payload.response.ProfessorResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProfessorService {

    ProfessorResponse getAllProfessor(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    ProfessorResponse searchAlunoByKeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    ProfessorDTO createProfessor(ProfessorDTO professorDTO);

    ProfessorDTO atualizarProfessorViaSuap(ProfessorDTO professorDTO, String matriculaFuncional);

    ProfessorDTO updateProfessorImagem(String matriculaFuncional, MultipartFile imagem) throws IOException;

    ProfessorDTO updatePerfilDescricao(String matriculaFuncional, ProfessorDTO professorDTO);

    ProfessorDTO deleteProfessor (String matriculaFuncional);
}
