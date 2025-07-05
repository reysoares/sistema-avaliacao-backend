package com.sistema.avaliacao.payload.response;

import com.sistema.avaliacao.payload.dto.CursoDTO;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CursoResponse implements PaginatedResponse<CursoDTO> {
    private List<CursoDTO> content;
    private Integer pageNumber;
    private Integer pageSize;
    private Long totalElements;
    private Integer totalPages;
    private boolean lastPage;
}
