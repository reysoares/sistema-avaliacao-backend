package com.sistema.avaliacao.payload.response;

import com.sistema.avaliacao.payload.dto.DisciplinaDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DisciplinaResponse implements PaginatedResponse<DisciplinaDTO> {

    private List<DisciplinaDTO> content;
    private Integer pageNumber;
    private Integer pageSize;
    private long totalElements;
    private Integer totalPages;
    private boolean lastPage;

}
