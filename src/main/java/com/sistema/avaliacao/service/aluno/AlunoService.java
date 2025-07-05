package com.sistema.avaliacao.service.aluno;

import com.sistema.avaliacao.payload.dto.AlunoDTO;
import com.sistema.avaliacao.payload.response.AlunoResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AlunoService {

    AlunoResponse getAllAlunos(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    AlunoResponse getAlunosByCurso(Long cursoId, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    AlunoResponse searchAlunoByKeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    AlunoDTO createAluno(AlunoDTO alunoDTO);

    AlunoDTO atualizarAlunoViaSuap(AlunoDTO alunoDTO, String matriculaAcademica, Long cursoId);

    AlunoDTO deleteAluno (String matriculaAcademica);

    AlunoDTO updateAlunoImagem (String matriculaAcademica, MultipartFile imagem) throws IOException;

    AlunoDTO updatePerfilDescricao(String matriculaAcademica, AlunoDTO alunoDTO);

}
