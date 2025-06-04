package com.sistema.avaliacao.payload.response;

import com.sistema.avaliacao.payload.dto.AvaliacaoDisciplinaDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvaliacaoDisciplinaResponse implements PaginatedResponse<AvaliacaoDisciplinaDTO> {
    private List<AvaliacaoDisciplinaDTO> content;
    private Integer pageNumber;
    private Integer pageSize;
    private Long totalElements;
    private Integer totalPages;
    private boolean lastPage;
}