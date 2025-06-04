package com.sistema.avaliacao.payload.response;

import com.sistema.avaliacao.payload.dto.AvaliacaoProfessorDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvaliacaoProfessorResponse implements PaginatedResponse<AvaliacaoProfessorDTO> {
    private List<AvaliacaoProfessorDTO> content;
    private Integer pageNumber;
    private Integer pageSize;
    private Long totalElements;
    private Integer totalPages;
    private boolean lastPage;
}
